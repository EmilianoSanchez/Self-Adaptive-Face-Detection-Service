package com.emiliano.evalfd.metrics;

import com.emiliano.androidMeter.core.metrics.Metric;
import com.emiliano.evalfd.FaceDetectionInput;

public class ImageSizeMetric implements Metric<FaceDetectionInput, Long> {

	@Override
	public String getName() {
		return "ImageSizeMetric";
	}

	@Override
	public Long calculate(FaceDetectionInput element) {
		return element.file.length();
	}

}
