package com.emiliano.evalfd.detection;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.IOException;
import java.io.InputStream;

import com.emiliano.fd.Face;
import com.emiliano.fd.FaceDetectionResult;
import com.emiliano.fd.FaceLandmark;

public class ImageHelper {

	private static final int IMAGE_MAX_SIDE_LENGTH = 1280;

	public static Bitmap loadSizeLimitedBitmapFromUri(Uri imageUri, ContentResolver contentResolver) {
		try {
			InputStream imageInputStream = contentResolver.openInputStream(imageUri);

			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			Rect outPadding = new Rect();
			BitmapFactory.decodeStream(imageInputStream, outPadding, options);

			int maxSideLength = options.outWidth > options.outHeight ? options.outWidth : options.outHeight;
			options.inSampleSize = 1;
			options.inSampleSize = calculateSampleSize(maxSideLength, IMAGE_MAX_SIDE_LENGTH);
			options.inJustDecodeBounds = false;
			if (imageInputStream != null) {
				imageInputStream.close();
			}

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

	public static Bitmap drawFaceRectanglesOnBitmap(Bitmap originalBitmap, FaceDetectionResult result,
			boolean drawLandmarks) {
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

		if (result != null && result.getDetectedFaces() != null) {
			for (Face face : result.getDetectedFaces()) {
				Rect faceRectangle = face.getFacePosition().getRect();
				canvas.drawRect(faceRectangle, paint);

				if (drawLandmarks && face.getFaceLandmarks() != null) {
					int radius = faceRectangle.width() / 30;
					if (radius == 0) {
						radius = 1;
					}
					paint.setStyle(Paint.Style.FILL);
					paint.setStrokeWidth(radius);

					for (FaceLandmark landmark : face.getFaceLandmarks()) {
						canvas.drawCircle((float) landmark.x, (float) landmark.y, radius, paint);
					}

					paint.setStyle(Paint.Style.STROKE);
					paint.setStrokeWidth(stokeWidth);
				}
			}
		}

		return bitmap;
	}

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

	public static Bitmap generateFaceThumbnail(Bitmap originalBitmap, Rect faceRectangle) throws IOException {

		return Bitmap.createBitmap(originalBitmap, faceRectangle.left, faceRectangle.top, faceRectangle.width(),
				faceRectangle.height());
	}

	private static int calculateSampleSize(int maxSideLength, int expectedMaxImageSideLength) {
		int inSampleSize = 1;

		while (maxSideLength > 2 * expectedMaxImageSideLength) {
			maxSideLength /= 2;
			inSampleSize *= 2;
		}

		return inSampleSize;
	}

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

	private static Bitmap rotateBitmap(Bitmap bitmap, int angle) {
		if (angle != 0) {
			Matrix matrix = new Matrix();
			matrix.postRotate(angle);
			return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		} else {
			return bitmap;
		}
	}

}
