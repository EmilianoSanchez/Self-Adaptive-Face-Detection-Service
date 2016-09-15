package com.emiliano.evalfd;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.emiliano.androidMeter.context.DeviceModel;
import com.emiliano.androidMeter.core.BenchmarkPlan;
import com.emiliano.androidMeter.core.Executor;
import com.emiliano.androidMeter.core.Loader;
import com.emiliano.androidMeter.core.Results;
import com.emiliano.androidMeter.core.ResultsImpl;
import com.emiliano.androidMeter.core.components.Component;
import com.emiliano.androidMeter.core.metrics.Metric;
import com.emiliano.androidMeter.core.metrics.OperationMetric;
import com.emiliano.fd.FaceDetectionResultImpl;

public class ExecutorWithFileLogger extends Executor<FaceDetectionInput, FaceDetectionOutput> {

	private File fileLogPath;

	public ExecutorWithFileLogger(ExecutorListener listener, File fileLogPath) {
		super(listener);
		this.fileLogPath = fileLogPath;
	}

	protected Results<FaceDetectionInput, FaceDetectionOutput> executeTestPlan(
			BenchmarkPlan<FaceDetectionInput, FaceDetectionOutput> testPlan) {
		this.publishProgress("executing " + testPlan.getName());
		ResultsImpl<FaceDetectionInput, FaceDetectionOutput> results = new ResultsImpl<FaceDetectionInput, FaceDetectionOutput>(
				testPlan);

		this.publishProgress("executing " + testPlan.getName() + ": measuring global properties");
		for (Metric<BenchmarkPlan<FaceDetectionInput, FaceDetectionOutput>, ?> globalMetric : testPlan
				.getGlobalMetrics()) {
			results.addGlobalMeasure(globalMetric.getName(), globalMetric.calculate(testPlan));
		}

		this.publishProgress("executing " + testPlan.getName() + ": measuring input properties");
		List<Loader<FaceDetectionInput>> inputs = testPlan.getInputs();
		for (int i = 0; i < inputs.size(); i++) {
			for (Metric<FaceDetectionInput, ?> inputMetric : testPlan.getInputMetrics()) {
				results.addInputMeasure(i, inputMetric.getName(), inputMetric.calculate(inputs.get(i).getElement()));
			}
		}

		this.publishProgress("executing " + testPlan.getName() + ": measuring component properties");
		List<Component<FaceDetectionInput, FaceDetectionOutput>> components = testPlan.getComponents();
		for (int c = 0; c < components.size(); c++) {
			for (Metric<Component<FaceDetectionInput, FaceDetectionOutput>, ?> componentMetric : testPlan
					.getComponentMetrics()) {
				results.addComponentMeasure(c, componentMetric.getName(), componentMetric.calculate(components.get(c)));
			}
		}

		int currentOperation = 0;
		int totalOperations = inputs.size() * components.size();

		DeviceModel deviceModel = new DeviceModel();
		OutputStreamWriter fileLog = null;
		try {
			fileLog = new OutputStreamWriter(new FileOutputStream(fileLogPath));// new
																				// OutputStreamWriter(context.openFileOutput("config.txt",
																				// Context.MODE_PRIVATE));
			fileLog.write("[");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < inputs.size(); i++) {
			FaceDetectionInput input = inputs.get(i).getElement();
			for (int c = 0; c < components.size(); c++) {
				Component<FaceDetectionInput, FaceDetectionOutput> component = components.get(c);

				currentOperation++;
				this.publishProgress("executing " + testPlan.getName() + ": operation " + currentOperation + " of "
						+ totalOperations);

				for (OperationMetric<FaceDetectionInput, FaceDetectionOutput, ?> operationMetric : testPlan
						.getOperationMetrics()) {
					operationMetric.onBeforeOperation(input, component);
				}
				FaceDetectionOutput output = component.execute(input);
				for (OperationMetric<FaceDetectionInput, FaceDetectionOutput, ?> operationMetric : testPlan
						.getOperationMetrics()) {
					results.addOperationMeasure(i, c, operationMetric.getName(), operationMetric.calculate(output));
				}

				try {
					if (currentOperation > 1)
						fileLog.write(",");
					JSONObject entry = new JSONObject();
					entry.put("currentOperation", currentOperation).put("device", deviceModel.toString())
							.put("component", component.getName()).put("input", input.toString());
					if (output.exception != null) {
						entry.put("error", output.exception.getMessage());
					} else {
						entry.put("succed", FaceDetectionResultImpl.toJson(output.result));
					}
					fileLog.write(entry.toString());

				} catch (IOException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}

				if (testPlan.getDelayBetweenOperationsMillis() > 0) {
					synchronized (this) {
						try {
							wait(testPlan.getDelayBetweenOperationsMillis());
						} catch (InterruptedException exeption) {
							exeption.printStackTrace();
						}
					}
				}

			}
		}

		try {
			fileLog.write("]");
			fileLog.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return results;
	}
}