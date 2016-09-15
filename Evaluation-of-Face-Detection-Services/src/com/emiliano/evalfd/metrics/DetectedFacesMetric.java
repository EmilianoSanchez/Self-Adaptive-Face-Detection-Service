package com.emiliano.evalfd.metrics;

import com.emiliano.androidMeter.core.components.Component;
import com.emiliano.androidMeter.core.metrics.OperationMetric;
import com.emiliano.evalfd.FaceDetectionInput;
import com.emiliano.evalfd.FaceDetectionOutput;

public class DetectedFacesMetric implements OperationMetric<FaceDetectionInput, FaceDetectionOutput, Integer> {

	@Override
	public Integer calculate(FaceDetectionOutput element) {
		if (element != null && element.result != null && element.result.getDetectedFaces() != null)
			return element.result.getDetectedFaces().length;
		else
			return -1;
	}

	@Override
	public String getName() {
		return "Detected Faces";
	}

	@Override
	public void onBeforeOperation(FaceDetectionInput input,
			Component<FaceDetectionInput, FaceDetectionOutput> component) {
	}

}
