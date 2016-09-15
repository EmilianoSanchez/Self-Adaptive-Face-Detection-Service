package com.emiliano.evalfd;

import com.emiliano.fd.FaceDetectionException;
import com.emiliano.fd.FaceDetectionResult;

public class FaceDetectionOutput {

	public FaceDetectionResult result;
	public FaceDetectionException exception;

	public FaceDetectionOutput(FaceDetectionResult result, FaceDetectionException exception) {
		this.result = result;
		this.exception = exception;
	}
}
