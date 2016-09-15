package com.emiliano.evalfd.benchmarkPlans;

import java.util.List;

import com.emiliano.androidMeter.core.BenchmarkPlanImpl;
import com.emiliano.androidMeter.core.metrics.component.ComponentName;
import com.emiliano.androidMeter.core.metrics.global.DeviceModelName;
import com.emiliano.androidMeter.core.metrics.input.InputName;
import com.emiliano.androidMeter.core.metrics.operation.ResponseTimeMetric;
import com.emiliano.evalfd.FaceDetectionComponent;
import com.emiliano.evalfd.FaceDetectionInput;
import com.emiliano.evalfd.FaceDetectionOutput;
import com.emiliano.evalfd.metrics.DetectedFacesMetric;
import com.emiliano.evalfd.metrics.ImageNumberOfFacesMetric;
import com.emiliano.fd.services.FaceDetectionServiceClient;

import android.content.Context;

public class BenchmarkPlan300W extends BenchmarkPlanImpl<FaceDetectionInput, FaceDetectionOutput> {

	public BenchmarkPlan300W(String name, Context context, FaceDetectionServiceClient faceServiceClient) {
		super(name, context);
		this.setDelayBetweenOperationsMillis(500);

		this.addComponents(new FaceDetectionComponent(faceServiceClient));

		this.addInputs();

		this.addGlobalMetrics(new DeviceModelName());
		this.addComponentMetrics(new ComponentName());

		this.addInputMetrics(new InputName());
		this.addInputMetrics(new ImageNumberOfFacesMetric());
		this.addOperationMetrics(new ResponseTimeMetric());
		this.addOperationMetrics(new DetectedFacesMetric());
	}

	private void addInputs() {

		List<FaceDetectionInput> list = DataSetUtils.getInputs300W();
		for (FaceDetectionInput input : list) {
			this.addInputs(input);
		}

	}
}
