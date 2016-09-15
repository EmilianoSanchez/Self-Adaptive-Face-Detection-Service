package com.emiliano.safdService.planning;

import java.util.Map;

import com.emiliano.fd.FaceDetectionException;
import com.emiliano.fd.services.FaceDetectionServiceClient;
import com.emiliano.safdService.ServiceFeatures;
import com.emiliano.safdService.QualityPreference;
import com.emiliano.safdService.context.ContextState;
import com.emiliano.safdService.services.ServiceClient;

public interface Planner {

	public FaceDetectionServiceClient selectBestFaceDetector(
			ServiceFeatures requiredServiceFeatures,
			QualityPreference serviceQualityPolicy,
			ContextState currentContextState, Map<String,ServiceClient> services) throws FaceDetectionException;

}