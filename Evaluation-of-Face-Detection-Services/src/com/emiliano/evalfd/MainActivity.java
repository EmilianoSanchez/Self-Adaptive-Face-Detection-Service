package com.emiliano.evalfd;


import com.emiliano.evalfd.detection.DetectionActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
    }

    public void detection(View view) {
        Intent intent = new Intent(this, DetectionActivity.class);
        startActivity(intent);
    }

    public void evaluation(View view) {
        Intent intent = new Intent(this, EvaluationActivity.class);
        startActivity(intent);
    }

}
