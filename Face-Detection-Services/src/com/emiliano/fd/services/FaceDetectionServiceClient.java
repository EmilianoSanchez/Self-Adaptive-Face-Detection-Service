package com.emiliano.fd.services;

import java.io.File;
import java.util.Map;

import com.emiliano.fd.FaceDetectionException;
import com.emiliano.fd.FaceDetectionResult;


public interface FaceDetectionServiceClient {
	
	public String getName();
	public FaceDetectionResult execute(File image) throws FaceDetectionException;
	
}
