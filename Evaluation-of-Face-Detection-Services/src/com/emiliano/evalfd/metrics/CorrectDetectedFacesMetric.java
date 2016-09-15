package com.emiliano.evalfd.metrics;

import com.emiliano.androidMeter.core.components.Component;
import com.emiliano.androidMeter.core.metrics.OperationMetric;
import com.emiliano.evalfd.FaceDetectionInput;
import com.emiliano.evalfd.FaceDetectionOutput;
import com.emiliano.fd.Face;
import com.emiliano.fd.FacePosition;

public class CorrectDetectedFacesMetric implements OperationMetric<FaceDetectionInput, FaceDetectionOutput, Integer> {

	private FacePosition[] realPositions;
	private double threshold;

	public CorrectDetectedFacesMetric() {
		this(0.5);
	}

	public CorrectDetectedFacesMetric(double threshold) {
		this.threshold = threshold;
	}

	@Override
	public Integer calculate(FaceDetectionOutput element) {
		boolean[] detected = new boolean[realPositions.length];
		int correctDetections = 0;
		if (element.result != null) {
			Face[] detectedFaces = element.result.getDetectedFaces();
			for (Face face : detectedFaces) {
				for (int i = 0; i < detected.length; i++) {
					if (detected[i] == false) {
						if (realPositions[i].getOverlapedAreaRatio(face.getFacePosition()) >= this.threshold) {
							// Log.i("EVAL", "
							// "+realPositions[i].getOverlappingRatio(face.getFacePosition()));
							detected[i] = true;
							correctDetections++;
							break;
						}
					}
				}
			}
		}
		return correctDetections;
	}

	@Override
	public String getName() {
		return "CorrectDetectedFacesMetric";
	}

	@Override
	public void onBeforeOperation(FaceDetectionInput input,
			Component<FaceDetectionInput, FaceDetectionOutput> component) {
		this.realPositions = input.realPositions;
	}

}
