package com.emiliano.safdService.test;

import java.io.File;

import com.emiliano.fd.FaceDetectionException;
import com.emiliano.fd.FaceDetectionResult;
import com.emiliano.safdService.SelfAdaptiveFaceDetector;
import com.emiliano.safdService.ServiceFeatures;
import com.emiliano.safdService.QualityPreference;
import com.emiliano.safdService.utils.FaceDetectionAsyncTask;
import com.emiliano.safdService.utils.OnFaceDetectionDone;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class MinimalSAFDActivity extends Activity implements OnFaceDetectionDone{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        android.content.Context appContext = this.getApplicationContext();
        SelfAdaptiveFaceDetector faceDetector = new SelfAdaptiveFaceDetector(appContext);
    
        ServiceFeatures requiredServiceFeatures = new ServiceFeatures();
        faceDetector.setRequiredServiceFeatures(requiredServiceFeatures);
        
        QualityPreference serviceQualityPolicy = new QualityPreference(0.5,0.5);
        faceDetector.setServiceQualityPolicy(serviceQualityPolicy);
        
        FaceDetectionAsyncTask detectionTask=new FaceDetectionAsyncTask(faceDetector, this);
        File input=new File("/storage/sdcard0/DCIM/Camera/picture1.jpg");
        detectionTask.execute(input);
    }
	
	@Override
	public void onSucced(FaceDetectionResult result) {
		Log.i("MinimalSAFDActivity", "Face detection succeeded with the following result: "+result.getStringResult());
	}

	@Override
	public void onFailure(FaceDetectionException exception) {
		Log.i("MinimalSAFDActivity", "Face detection failed with the following exception: "+exception.getMessage());
	}

}