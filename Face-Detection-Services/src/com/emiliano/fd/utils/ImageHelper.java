package com.emiliano.fd.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Set;

import com.emiliano.fd.Face;
import com.emiliano.fd.FaceLandmark;
import com.emiliano.fd.FacePosition;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Bitmap.Config;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

public class ImageHelper {
	public static Bitmap loadBitmapFromUri(Uri imageUri, ContentResolver contentResolver) throws FileNotFoundException {
		Log.i("CV", "loadBitmapFromUri " + imageUri.toString() + " " + contentResolver.toString());
		InputStream imageInputStream = contentResolver.openInputStream(imageUri);
		Bitmap bitmap = BitmapFactory.decodeStream(imageInputStream, null, null);
		return bitmap;
	}

	// Draw detected face rectangles in the original image. And return the image
	// drawn.
	// If drawLandmarks is set to be true, draw the five main landmarks of each
	// face.
	public static Bitmap drawFaceRectanglesOnBitmap(Bitmap originalBitmap, Face[] faces) {
		Bitmap bitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);
		Canvas canvas = new Canvas(bitmap);

		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(Color.GREEN);
		int stokeWidth = Math.max(originalBitmap.getWidth(), originalBitmap.getHeight()) / 100;
		if (stokeWidth == 0) {
			stokeWidth = 1;
		}
		paint.setStrokeWidth(stokeWidth);

		if (faces != null) {
			for (Face face : faces) {
				if (face != null) {
					canvas.drawRect(face.getFacePosition().getRect(), paint);

					if (face.getFaceLandmarks() != null && face.getFaceLandmarks().size() > 0) {
						int radius = face.getFacePosition().width / 30;
						if (radius == 0) {
							radius = 1;
						}
						paint.setStyle(Paint.Style.FILL);
						paint.setStrokeWidth(radius);

						for (FaceLandmark faceLandmark : face.getFaceLandmarks()) {
							canvas.drawCircle((float) faceLandmark.x, (float) faceLandmark.y, radius, paint);
						}

						paint.setStyle(Paint.Style.STROKE);
						paint.setStrokeWidth(stokeWidth);
					}
				}
			}
		}

		return bitmap;
	}

	// The maximum side length of the image to detect, to keep the size of image
	// less than 4MB.
	// Resize the image if its side length is larger than the maximum.
	private static final int IMAGE_MAX_SIDE_LENGTH = 1280;

	// Ratio to scale a detected face rectangle, the face rectangle scaled up
	// looks more natural.
	private static final double FACE_RECT_SCALE_RATIO = 1.3;

	public static Bitmap loadBitmapRGB565FromUri(Uri imageUri, ContentResolver contentResolver) {
		InputStream imageInputStream;
		try {
			imageInputStream = contentResolver.openInputStream(imageUri);
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inPreferredConfig = Config.RGB_565;
			Bitmap bitmap = BitmapFactory.decodeStream(imageInputStream, null, options);
			return bitmap;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	// Decode image from imageUri, and resize according to the
	// expectedMaxImageSideLength
	// If expectedMaxImageSideLength is
	// (1) less than or equal to 0,
	// (2) more than the actual max size length of the bitmap
	// then return the original bitmap
	// Else, return the scaled bitmap
	public static Bitmap loadSizeLimitedBitmapFromUri(Uri imageUri, ContentResolver contentResolver) {
		Log.i("CV", "loadSizeLimitedBitmapFromUri " + imageUri.toString() + " " + contentResolver.toString());
		try {
			// Load the image into InputStream.
			InputStream imageInputStream = contentResolver.openInputStream(imageUri);

			// For saving memory, only decode the image meta and get the side
			// length.
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			Rect outPadding = new Rect();
			BitmapFactory.decodeStream(imageInputStream, outPadding, options);

			// Calculate shrink rate when loading the image into memory.
			int maxSideLength = options.outWidth > options.outHeight ? options.outWidth : options.outHeight;
			options.inSampleSize = 1;
			options.inSampleSize = calculateSampleSize(maxSideLength, IMAGE_MAX_SIDE_LENGTH);
			options.inJustDecodeBounds = false;
			imageInputStream.close();

			// Load the bitmap and resize it to the expected size length
			imageInputStream = contentResolver.openInputStream(imageUri);
			Bitmap bitmap = BitmapFactory.decodeStream(imageInputStream, outPadding, options);
			maxSideLength = bitmap.getWidth() > bitmap.getHeight() ? bitmap.getWidth() : bitmap.getHeight();
			double ratio = IMAGE_MAX_SIDE_LENGTH / (double) maxSideLength;
			if (ratio < 1) {
				bitmap = Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() * ratio),
						(int) (bitmap.getHeight() * ratio), false);
			}

			return rotateBitmap(bitmap, getImageRotationAngle(imageUri, contentResolver));
		} catch (Exception e) {
			return null;
		}
	}

	// Draw detected face rectangles in the original image. And return the image
	// drawn.
	// If drawLandmarks is set to be true, draw the five main landmarks of each
	// face.
	public static Bitmap drawFaceRectanglesOnBitmap(Bitmap originalBitmap, FacePosition[] faces) {
		Bitmap bitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);
		Canvas canvas = new Canvas(bitmap);

		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(Color.GREEN);
		int stokeWidth = Math.max(originalBitmap.getWidth(), originalBitmap.getHeight()) / 100;
		if (stokeWidth == 0) {
			stokeWidth = 1;
		}
		paint.setStrokeWidth(stokeWidth);

		if (faces != null) {
			for (FacePosition face : faces) {
				FacePosition faceRectangle = calculateFaceRectangle(bitmap, face, FACE_RECT_SCALE_RATIO);

				canvas.drawRect(faceRectangle.left, faceRectangle.top, faceRectangle.left + faceRectangle.width,
						faceRectangle.top + faceRectangle.height, paint);

			}
		}

		return bitmap;
	}

	public static Bitmap drawFaceLandmarksOnBitmap(Bitmap originalBitmap, Set<FaceLandmark>[] facesLandmarks) {
		Bitmap bitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);
		Canvas canvas = new Canvas(bitmap);

		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(Color.GREEN);
		paint.setStyle(Paint.Style.FILL);

		int radius = 1;
		// int radius = face.faceRectangle.width / 30;
		// if (radius == 0) {
		// radius = 1;
		// }
		paint.setStrokeWidth(radius);

		if (facesLandmarks != null) {
			for (Set<FaceLandmark> faceLandmarks : facesLandmarks) {
				if (faceLandmarks != null) {
					for (FaceLandmark landmark : faceLandmarks) {
						canvas.drawCircle((float) landmark.x, (float) landmark.y, radius, paint);
					}
				}
			}
		}
		return bitmap;
	}

	// Highlight the selected face thumbnail in face list.
	public static Bitmap highlightSelectedFaceThumbnail(Bitmap originalBitmap) {
		Bitmap bitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);
		Canvas canvas = new Canvas(bitmap);
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(Color.parseColor("#3399FF"));
		int stokeWidth = Math.max(originalBitmap.getWidth(), originalBitmap.getHeight()) / 10;
		if (stokeWidth == 0) {
			stokeWidth = 1;
		}
		bitmap.getWidth();
		paint.setStrokeWidth(stokeWidth);
		canvas.drawRect(0, 0, bitmap.getWidth(), bitmap.getHeight(), paint);

		return bitmap;
	}

	// Crop the face thumbnail out from the original image.
	// For better view for human, face rectangles are resized to the rate
	// faceRectEnlargeRatio.
	public static Bitmap generateFaceThumbnail(Bitmap originalBitmap, FacePosition faceRectangle) throws IOException {
		FacePosition faceRect = calculateFaceRectangle(originalBitmap, faceRectangle, FACE_RECT_SCALE_RATIO);

		return Bitmap.createBitmap(originalBitmap, faceRect.left, faceRect.top, faceRect.width, faceRect.height);
	}

	// Return the number of times for the image to shrink when loading it into
	// memory.
	// The SampleSize can only be a final value based on powers of 2.
	private static int calculateSampleSize(int maxSideLength, int expectedMaxImageSideLength) {
		int inSampleSize = 1;

		while (maxSideLength > 2 * expectedMaxImageSideLength) {
			maxSideLength /= 2;
			inSampleSize *= 2;
		}

		return inSampleSize;
	}

	// Get the rotation angle of the image taken.
	private static int getImageRotationAngle(Uri imageUri, ContentResolver contentResolver) throws IOException {
		int angle = 0;
		Cursor cursor = contentResolver.query(imageUri, new String[] { MediaStore.Images.ImageColumns.ORIENTATION },
				null, null, null);
		if (cursor != null) {
			if (cursor.getCount() == 1) {
				cursor.moveToFirst();
				angle = cursor.getInt(0);
			}
			cursor.close();
		} else {
			ExifInterface exif = new ExifInterface(imageUri.getPath());
			int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_270:
				angle = 270;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				angle = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_90:
				angle = 90;
				break;
			default:
				break;
			}
		}
		return angle;
	}

	// Rotate the original bitmap according to the given orientation angle
	private static Bitmap rotateBitmap(Bitmap bitmap, int angle) {
		// If the rotate angle is 0, then return the original image, else return
		// the rotated image
		if (angle != 0) {
			Matrix matrix = new Matrix();
			matrix.postRotate(angle);
			return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		} else {
			return bitmap;
		}
	}

	// Resize face rectangle, for better view for human
	// To make the rectangle larger, faceRectEnlargeRatio should be larger than
	// 1, recommend 1.3
	private static FacePosition calculateFaceRectangle(Bitmap bitmap, FacePosition faceRectangle,
			double faceRectEnlargeRatio) {
		// Get the resized side length of the face rectangle
		double sideLength = faceRectangle.width * faceRectEnlargeRatio;
		sideLength = Math.min(sideLength, bitmap.getWidth());
		sideLength = Math.min(sideLength, bitmap.getHeight());

		// Make the left edge to left more.
		double left = faceRectangle.left - faceRectangle.width * (faceRectEnlargeRatio - 1.0) * 0.5;
		left = Math.max(left, 0.0);
		left = Math.min(left, bitmap.getWidth() - sideLength);

		// Make the top edge to top more.
		double top = faceRectangle.top - faceRectangle.height * (faceRectEnlargeRatio - 1.0) * 0.5;
		top = Math.max(top, 0.0);
		top = Math.min(top, bitmap.getHeight() - sideLength);

		// Shift the top edge to top more, for better view for human
		double shiftTop = faceRectEnlargeRatio - 1.0;
		shiftTop = Math.max(shiftTop, 0.0);
		shiftTop = Math.min(shiftTop, 1.0);
		top -= 0.15 * shiftTop * faceRectangle.height;
		top = Math.max(top, 0.0);

		// Set the result.
		FacePosition result = new FacePosition((int) left, (int) top, (int) sideLength, (int) sideLength);
		return result;
	}
}
