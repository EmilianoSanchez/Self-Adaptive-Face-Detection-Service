package com.emiliano.safdService.utils;

import com.emiliano.fd.FaceDetectionException;
import com.emiliano.fd.FaceDetectionResult;

public interface OnFaceDetectionDone {
	public void onSucced(FaceDetectionResult result);

	public void onFailure(FaceDetectionException exception);
}
