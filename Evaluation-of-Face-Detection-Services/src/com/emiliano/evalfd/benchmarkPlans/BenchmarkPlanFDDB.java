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

public class BenchmarkPlanFDDB extends BenchmarkPlanImpl<FaceDetectionInput, FaceDetectionOutput> {

	public BenchmarkPlanFDDB(String name, Context context, FaceDetectionServiceClient faceServiceClient) {
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

		List<FaceDetectionInput> list = DataSetUtils.getInputsFDDBFromEllipseList();
		for (FaceDetectionInput input : list) {
			this.addInputs(input);
		}

		// this.addInputs(new FaceDetectionInput(new
		// File("/storage/emulated/0/FaceDetection/detection1.jpg"),1));
		// this.addInputs(new FaceDetectionInput(new
		// File("/storage/emulated/0/FaceDetection/detection2.jpg"),1));
		// this.addInputs(new FaceDetectionInput(new
		// File("/storage/emulated/0/FaceDetection/detection3.jpg"),2));
		// this.addInputs(new FaceDetectionInput(new
		// File("/storage/emulated/0/FaceDetection/detection4.jpg"),3));
		// this.addInputs(new FaceDetectionInput(new
		// File("/storage/emulated/0/FaceDetection/detection5.jpg"),2));
		// this.addInputs(new FaceDetectionInput(new
		// File("/storage/emulated/0/FaceDetection/detection6.jpg"),6));

		// this.addInputs(new FaceDetectionInput(new
		// File("/storage/emulated/0/FaceDetection/detection7.jpg"),2));
		// this.addInputs(new FaceDetectionInput(new
		// File("/storage/emulated/0/FaceDetection/detection8.jpg"),4));
		// this.addInputs(new FaceDetectionInput(new
		// File("/storage/emulated/0/FaceDetection/detection9.jpg"),2));
		// this.addInputs(new FaceDetectionInput(new
		// File("/storage/emulated/0/FaceDetection/detection10.jpg"),0));
		// this.addInputs(new FaceDetectionInput(new
		// File("/storage/emulated/0/FaceDetection/detection11.jpg"),0));
		// this.addInputs(new FaceDetectionInput(new
		// File("/storage/emulated/0/FaceDetection/profile-left.jpg"),1));
		// this.addInputs(new FaceDetectionInput(new
		// File("/storage/emulated/0/FaceDetection/profile-right.jpg"),1));

	}
}
