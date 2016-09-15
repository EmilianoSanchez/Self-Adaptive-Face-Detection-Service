package com.emiliano.evalfd.detection;

import com.emiliano.evalfd.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class JsonResultActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.json_result_activity);

		String jsonResult = (String) getIntent().getSerializableExtra("JsonResult");
		if (jsonResult != null) {
			TextView textView = (TextView) findViewById(R.id.jsonResult);
			textView.setText(jsonResult);
		}
	}

}
