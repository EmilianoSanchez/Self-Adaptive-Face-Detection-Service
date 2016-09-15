package com.emiliano.evalfd;

import com.emiliano.androidMeter.core.components.Component;
import com.emiliano.fd.FaceDetectionException;
import com.emiliano.fd.FaceDetectionResult;
import com.emiliano.fd.services.FaceDetectionServiceClient;

public class FaceDetectionComponent implements Component<FaceDetectionInput, FaceDetectionOutput> {

	private FaceDetectionServiceClient client;

	public FaceDetectionComponent(FaceDetectionServiceClient client) {
		this.client = client;
	}

	@Override
	public String getName() {
		return client.getName();
	}

	@Override
	public FaceDetectionOutput execute(FaceDetectionInput input) {
		FaceDetectionResult result = null;
		FaceDetectionException exception = null;
		try {
			result = client.execute(input.file);
		} catch (FaceDetectionException e) {
			exception = e;
		}

		return new FaceDetectionOutput(result, exception);
	}
};
