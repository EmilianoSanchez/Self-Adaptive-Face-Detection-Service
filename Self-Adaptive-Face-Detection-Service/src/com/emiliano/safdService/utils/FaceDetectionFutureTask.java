package com.emiliano.safdService.utils;

import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import com.emiliano.fd.FaceDetectionException;
import com.emiliano.fd.FaceDetectionResult;
import com.emiliano.fd.services.FaceDetectionServiceClient;

public class FaceDetectionFutureTask extends FutureTask<FaceDetectionResult> {

	private static class FaceDetectionCallable implements Callable<FaceDetectionResult> {

		private FaceDetectionServiceClient faceDetector;
		private File image;

		public FaceDetectionCallable(FaceDetectionServiceClient faceDetector, File image) {
			this.faceDetector = faceDetector;
			this.image = image;
		}

		@Override
		public FaceDetectionResult call() throws FaceDetectionException {
			return faceDetector.execute(image);
		}

	}

	public FaceDetectionFutureTask(FaceDetectionServiceClient faceDetector, File image) {
		super(new FaceDetectionCallable(faceDetector, image));
	}

}
