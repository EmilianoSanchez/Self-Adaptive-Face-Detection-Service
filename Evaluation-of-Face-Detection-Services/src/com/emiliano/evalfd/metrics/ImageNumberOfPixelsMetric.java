package com.emiliano.evalfd.metrics;

import java.io.File;

import com.emiliano.androidMeter.core.metrics.Metric;
import com.emiliano.evalfd.FaceDetectionInput;

import android.graphics.BitmapFactory;

public class ImageNumberOfPixelsMetric implements Metric<FaceDetectionInput, Integer> {

	@Override
	public String getName() {
		return "ImageNumberOfPixelsMetric";
	}

	@Override
	public Integer calculate(FaceDetectionInput element) {
		File image = element.file;
		BitmapFactory.Options bmOptions = new BitmapFactory.Options();
		bmOptions.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(image.getAbsolutePath(), bmOptions);
		return bmOptions.outHeight * bmOptions.outWidth;
	}

}
