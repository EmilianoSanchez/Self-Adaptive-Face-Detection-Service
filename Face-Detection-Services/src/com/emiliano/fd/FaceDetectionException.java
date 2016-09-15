package com.emiliano.fd;

public class FaceDetectionException extends Exception {

	public FaceDetectionException(Exception exception) {
		super(exception);
	}
	
	public FaceDetectionException(String message) {
		super(new Exception(message));
	}

	private static final long serialVersionUID = 1L;

}
