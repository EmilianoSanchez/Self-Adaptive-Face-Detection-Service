package com.emiliano.evalfd.metrics;

import com.emiliano.androidMeter.core.metrics.Metric;
import com.emiliano.evalfd.FaceDetectionInput;

public class ImageFormatMetric implements Metric<FaceDetectionInput, String> {

	@Override
	public String getName() {
		return "ImageFormatMetric";
	}

	@Override
	public String calculate(FaceDetectionInput element) {
		// TODO Auto-generated method stub
		int lastindex = element.file.getName().lastIndexOf('.');
		return element.file.getName().substring(lastindex + 1);
	}

}
