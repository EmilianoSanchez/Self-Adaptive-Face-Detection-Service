package com.emiliano.evalfd.detection;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.emiliano.evalfd.R;
import com.emiliano.fd.Face;
import com.emiliano.fd.FaceAttribute;
import com.emiliano.fd.FaceDetectionResult;
import com.emiliano.fd.services.FaceDetectionRegistry;
import com.emiliano.fd.services.FaceDetectionServiceClient;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class DetectionActivity extends Activity {

	// Background task of face detection.
	private class DetectionTask extends AsyncTask<File, String, FaceDetectionResult> {
		private boolean mSucceed = true;
		private FaceDetectionServiceClient faceServiceClient;

		DetectionTask(FaceDetectionServiceClient faceServiceClient) {
			this.faceServiceClient = faceServiceClient;
		}

		@Override
		protected FaceDetectionResult doInBackground(File... params) {
			// Get an instance of face service client to detect faces in image.

			try {
				publishProgress("Detecting...");

				// Start detection.
				return faceServiceClient.execute(params[0]);
			} catch (Exception e) {
				mSucceed = false;
				publishProgress(e.getMessage());
				return null;
			}
		}

		@Override
		protected void onPreExecute() {
			mProgressDialog.show();
		}

		@Override
		protected void onProgressUpdate(String... progress) {
			mProgressDialog.setMessage(progress[0]);
			setInfo(progress[0]);
		}

		@Override
		protected void onPostExecute(FaceDetectionResult result) {

			// Show the result on screen when detection is done.
			setUiAfterDetection(result, mSucceed);
		}
	}

	// Flag to indicate which task is to be performed.
	private static final int REQUEST_SELECT_IMAGE = 0;

	// The URI of the image selected to detect.
	private Uri mImageUri;

	// The image selected to detect.
	private Bitmap mBitmap;

	// Progress dialog popped up when communicating with server.
	ProgressDialog mProgressDialog;

	private FaceDetectionServiceClient mService;

	private String jsonResult;

	private Spinner mSpinner;
	private FaceDetectionRegistry registry;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detection_activity);

		mProgressDialog = new ProgressDialog(this);
		mProgressDialog.setTitle(getString(R.string.progress_dialog_title));

		// Disable button "detect" as the image to detect is not selected.
		setDetectButtonEnabledStatus(false);

		mSpinner = (Spinner) this.findViewById(R.id.spinner);
		registry = FaceDetectionRegistry.getInstance(this.getApplicationContext());
		ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
				registry.getServiceConfigurationNames());
		spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mSpinner.setAdapter(spinnerArrayAdapter);
		mSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				DetectionActivity.this.mService = registry.getServiceConfigurationByIndex(position);
				if (mImageUri != null)
					setDetectButtonEnabledStatus(true);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// setExecutePlanButtonEnabled(false);
			}

		});
	}

	// Save the activity state when it's going to stop.
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		outState.putParcelable("ImageUri", mImageUri);
	}

	// Recover the saved state when the activity is recreated.
	@Override
	protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);

		mImageUri = savedInstanceState.getParcelable("ImageUri");
		if (mImageUri != null) {
			mBitmap = ImageHelper.loadSizeLimitedBitmapFromUri(mImageUri, getContentResolver());
		}
	}

	// Called when image selection is done.
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case REQUEST_SELECT_IMAGE:
			if (resultCode == RESULT_OK) {
				// If image is selected successfully, set the image URI and
				// bitmap.
				mImageUri = data.getData();
				mBitmap = ImageHelper.loadSizeLimitedBitmapFromUri(mImageUri, getContentResolver());
				if (mBitmap != null) {
					// Show the image on screen.
					ImageView imageView = (ImageView) findViewById(R.id.image);
					imageView.setImageBitmap(mBitmap);

				}

				// Clear the detection result.
				FaceListAdapter faceListAdapter = new FaceListAdapter(null);
				ListView listView = (ListView) findViewById(R.id.list_detected_faces);
				listView.setAdapter(faceListAdapter);

				// Clear the information panel.
				setInfo("");

				// Enable button "detect" as the image is selected and not
				// detected.
				if (mService != null)
					setDetectButtonEnabledStatus(true);
			}
			break;
		default:
			break;
		}
	}

	// Called when the "Select Image" button is clicked.
	public void selectImage(View view) {
		Intent intent = new Intent(this, SelectImageActivity.class);
		startActivityForResult(intent, REQUEST_SELECT_IMAGE);
	}

	// Called when the "Detect" button is clicked.
	public void detect(View view) {
		// Put the image into an input stream for detection.
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
		ByteArrayInputStream inputStream = new ByteArrayInputStream(output.toByteArray());

		// Start a background task to detect faces in the image.

		new DetectionTask(mService).execute(new File(getPath(mImageUri)));

		// Prevent button click during detecting.
		setAllButtonsEnabledStatus(false);
	}

	// View the log of service calls.
	public void viewJsonResult(View view) {
		Intent intent = new Intent(this, JsonResultActivity.class);
		intent.putExtra("JsonResult", this.jsonResult);
		startActivity(intent);
	}

	// Show the result on screen when detection is done.
	private void setUiAfterDetection(FaceDetectionResult result, boolean succeed) {
		// Detection is done, hide the progress dialog.
		mProgressDialog.dismiss();

		// Enable all the buttons.
		setAllButtonsEnabledStatus(true);

		// Disable button "detect" as the image has already been detected.
		setDetectButtonEnabledStatus(false);

		if (succeed) {
			// The information about the detection result.
			String detectionResult;
			if (result != null && result.getDetectedFaces() != null) {
				detectionResult = result.getDetectedFaces().length + " face"
						+ (result.getDetectedFaces().length != 1 ? "s" : "") + " detected";

				// Show the detected faces on original image.
				ImageView imageView = (ImageView) findViewById(R.id.image);
				imageView.setImageBitmap(ImageHelper.drawFaceRectanglesOnBitmap(mBitmap, result, true));

				// Set the adapter of the ListView which contains the details of
				// the detected faces.
				FaceListAdapter faceListAdapter = new FaceListAdapter(result.getDetectedFaces());

				// Show the detailed list of detected faces.
				ListView listView = (ListView) findViewById(R.id.list_detected_faces);
				listView.setAdapter(faceListAdapter);
			} else {
				detectionResult = "0 face detected";
			}
			jsonResult = result.getStringResult();
			setInfo(detectionResult);
		}

		// mImageUri = null;
		// mBitmap = null;

	}

	// Set whether the buttons are enabled.
	private void setDetectButtonEnabledStatus(boolean isEnabled) {
		Button detectButton = (Button) findViewById(R.id.detect);
		detectButton.setEnabled(isEnabled);
	}

	// Set whether the buttons are enabled.
	private void setAllButtonsEnabledStatus(boolean isEnabled) {
		Button selectImageButton = (Button) findViewById(R.id.select_image);
		selectImageButton.setEnabled(isEnabled);

		Button detectButton = (Button) findViewById(R.id.detect);
		detectButton.setEnabled(isEnabled);

		Button ViewLogButton = (Button) findViewById(R.id.view_log);
		ViewLogButton.setEnabled(isEnabled);
	}

	// Set the information panel on screen.
	private void setInfo(String info) {
		TextView textView = (TextView) findViewById(R.id.info);
		textView.setText(info);
	}

	// The adapter of the GridView which contains the details of the detected
	// faces.
	private class FaceListAdapter extends BaseAdapter {
		// The detected faces.
		List<Face> faces;

		// The thumbnails of detected faces.
		List<Bitmap> faceThumbnails;

		// Initialize with detection result.
		FaceListAdapter(Face[] detectionResult) {
			faces = new ArrayList<>();
			faceThumbnails = new ArrayList<>();

			if (detectionResult != null) {
				faces = Arrays.asList(detectionResult);
				for (Face face : faces) {
					try {
						// Crop face thumbnail with five main landmarks drawn
						// from original image.
						faceThumbnails
								.add(ImageHelper.generateFaceThumbnail(mBitmap, face.getFacePosition().getRect()));
					} catch (IOException e) {
						// Show the exception when generating face thumbnail
						// fails.
						setInfo(e.getMessage());
					}
				}
			}
		}

		@Override
		public boolean isEnabled(int position) {
			return false;
		}

		@Override
		public int getCount() {
			return faces.size();
		}

		@Override
		public Object getItem(int position) {
			return faces.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = layoutInflater.inflate(R.layout.item_face_with_description, parent, false);
			}
			convertView.setId(position);

			// Show the face thumbnail.
			((ImageView) convertView.findViewById(R.id.face_thumbnail)).setImageBitmap(faceThumbnails.get(position));

			// Show the face details.
			DecimalFormat formatter = new DecimalFormat("#0.000");

			String face_description = "Age: " + faces.get(position).getAge() + "\n" + "Is female: "
					+ faces.get(position).getIsFemaleConfidence() + "\n" + "Is left eye open: "
					+ faces.get(position).getIsLeftEyeOpenConfidence() + "\n" + "Is right eye open: "
					+ faces.get(position).getIsRightEyeOpenConfidence() + "\n" + "Is smiling: "
					+ faces.get(position).getIsSmilingConfidence() + "\n";

			StringBuilder builder = new StringBuilder(face_description);

			if (faces.get(position).getFaceAttributes() != null) {
				for (FaceAttribute attribute : faces.get(position).getFaceAttributes()) {
					builder.append(attribute.attributeName + ": " + formatter.format(attribute.confidence) + "\n");
				}
			}

			((TextView) convertView.findViewById(R.id.text_detected_face)).setText(builder.toString());

			return convertView;
		}
	}

	/**
	 * helper to retrieve the path of an image URI
	 */
	public String getPath(Uri uri) {

		if (uri == null) {
			return null;
		}
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		if (cursor != null) {
			int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			return cursor.getString(column_index);
		}
		return uri.getPath();
	}
}
