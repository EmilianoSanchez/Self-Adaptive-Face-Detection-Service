package com.emiliano.evalfd;

import java.io.File;
import java.io.IOException;

import com.emiliano.androidMeter.core.BenchmarkPlan;
import com.emiliano.androidMeter.core.Executor;
import com.emiliano.androidMeter.core.Executor.ExecutorListener;
import com.emiliano.androidMeter.core.Results;
import com.emiliano.androidMeter.utils.ResultsUtils;
import com.emiliano.evalfd.benchmarkPlans.BenchmarkPlan300W;
import com.emiliano.evalfd.benchmarkPlans.BenchmarkPlanFDDB;
import com.emiliano.evalfd.benchmarkPlans.BenchmarkPlanFRGC;
import com.emiliano.fd.services.FaceDetectionRegistry;
import com.emiliano.fd.services.FaceDetectionServiceClient;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class EvaluationActivity extends Activity implements ExecutorListener {

	private Results results;
	// private ProgressDialog progressDialog;
	private TextView message;
	private Spinner mSpinner;
	private FaceDetectionRegistry registry;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.evaluation_activity);
		message = (TextView) this.findViewById(R.id.message);
		mSpinner = (Spinner) this.findViewById(R.id.spinner);
		registry = FaceDetectionRegistry.getInstance(this.getApplicationContext());
		ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
				registry.getServiceConfigurationNames()); // selected item will
															// look like a
															// spinner set from
															// XML
		spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mSpinner.setAdapter(spinnerArrayAdapter);
		mSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				setExecutePlanButtonEnabled(true);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				setExecutePlanButtonEnabled(false);
			}

		});

	}

	protected void setExecutePlanButtonEnabled(boolean isEnabled) {
		Button detectButton = (Button) findViewById(R.id.executeFDDB);
		detectButton.setEnabled(isEnabled);
		Button detectButton2 = (Button) findViewById(R.id.executeFRGC);
		detectButton2.setEnabled(isEnabled);
		Button detectButton3 = (Button) findViewById(R.id.execute300W);
		detectButton3.setEnabled(isEnabled);
	}

	public void executePlan(View view) {
		int position = mSpinner.getSelectedItemPosition();
		FaceDetectionServiceClient faceServiceClient = registry.getServiceConfigurationByIndex(position);
		String deviceName = Build.MANUFACTURER + Build.MODEL;
		String testPlanName = deviceName + "-" + faceServiceClient.getName();

		Executor<FaceDetectionInput, FaceDetectionOutput> executor = new ExecutorWithFileLogger(this,
				getFileLogPath(testPlanName));
		
		BenchmarkPlan<FaceDetectionInput, FaceDetectionOutput> plan =null;
		switch(view.getId()){
		case R.id.executeFDDB:
			plan= new BenchmarkPlanFDDB(testPlanName, this,
				faceServiceClient);
			break;
		case R.id.executeFRGC:
			plan= new BenchmarkPlanFRGC(testPlanName, this,
					faceServiceClient);
			break;
		case R.id.execute300W:
			plan= new BenchmarkPlan300W(testPlanName, this,
					faceServiceClient);
			break;
		}
		executor.execute(plan);
	}

	private File getFileLogPath(String fileName) {
		String externalDir = Environment.getExternalStorageDirectory().getAbsolutePath();
		return new File(externalDir + "/" + fileName + ".txt");
	}

	public void saveResults(View view) {
		if (results != null) {
			try {
				File file = getFilePath();
				ResultsUtils.saveToCSV_DesnormalizedData(file,results);

				AlertDialog dialog = new AlertDialog.Builder(this)
						.setMessage("Results were saved in " + file.getAbsolutePath()).setTitle("Results saved")
						.setPositiveButton("OK", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
							}
						}).create();
				dialog.show();
			} catch (IOException e) {
				AlertDialog dialog = new AlertDialog.Builder(this).setMessage(e.getMessage())
						.setTitle("Error saving results")
						.setPositiveButton("OK", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
							}
						}).create();
				dialog.show();
			}
		}
	}

	protected File getFilePath() throws IOException {
		String externalDir = Environment.getExternalStorageDirectory().getAbsolutePath();
		if (externalDir == null)
			throw new IOException("No external storage available");
		return new File(externalDir + "/" + results.getBenchmarkPlan().getName() + ".csv");
	}

	@Override
	public void onPreExecute() {
		// progressDialog = new ProgressDialog(this);
		// progressDialog.setTitle("Test plan progress");
		// progressDialog.setMessage("Starting test plan");
		// progressDialog.show();
		message.setText("Starting test plan");
	}

	@Override
	public void onProgressUpdate(String... progress) {
		// progressDialog.setMessage(progress[0]);
		message.setText(progress[0]);
	}

	@Override
	public void onPostExecute(Results allResults[]) {
		this.results = allResults[0];
		// progressDialog.dismiss();
		// progressDialog=null;
		message.setText("Finish");
		Button saveResults = (Button) this.findViewById(R.id.saveResults);
		saveResults.setEnabled(true);
	}

}