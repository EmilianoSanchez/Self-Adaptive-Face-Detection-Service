package com.emiliano.safdService.utils;

import java.io.File;

import com.emiliano.fd.FaceDetectionException;
import com.emiliano.fd.FaceDetectionResult;
import com.emiliano.fd.services.FaceDetectionServiceClient;

import android.os.AsyncTask;

public class FaceDetectionAsyncTask extends AsyncTask<File, Integer, FaceDetectionResult> {

	private FaceDetectionServiceClient faceDetector;
	private OnFaceDetectionDone callback;
	private FaceDetectionException exception;

	public FaceDetectionAsyncTask(FaceDetectionServiceClient faceDetector, OnFaceDetectionDone callback) {
		this.faceDetector = faceDetector;
		this.callback = callback;
	}

	@Override
	protected FaceDetectionResult doInBackground(File... params) {
		FaceDetectionResult result = null;
		try {
			result = faceDetector.execute(params[0]);
		} catch (FaceDetectionException e) {
			this.exception = e;
		}
		return result;
	}

	protected void onPostExecute(FaceDetectionResult result) {
		if (exception == null)
			this.callback.onSucced(result);
		else
			this.callback.onFailure(exception);
	}
}
