package com.emiliano.evalfd.metrics;

import com.emiliano.androidMeter.core.metrics.Metric;
import com.emiliano.evalfd.FaceDetectionInput;

public class ImageNumberOfFacesMetric implements Metric<FaceDetectionInput, Integer> {

	@Override
	public Integer calculate(FaceDetectionInput element) {
		return element.numberOfFaces;
	}

	@Override
	public String getName() {
		return "ImageNumberOfFacesMetric";
	}

}
