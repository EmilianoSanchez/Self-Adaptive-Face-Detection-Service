package com.emiliano.fd.services;

import java.util.HashMap;
import java.util.Map;

import com.emiliano.fd.R;
import android.content.Context;

public class FaceDetectionRegistry {

	public enum ServiceIndex {
		FaceRectAPI, SkyBiometryAPI, MicrosoftFaceAPI, GMSFaceDetector, ViolaJonesOpenCVFaceDetector
	}

	public enum ServiceConfigurationIndex {

		FaceRectAPI, FaceRectAPI_features,

		SkyBiometryAPI,

		SkyBiometryAPI_gender,

		SkyBiometryAPI_smiling,

		SkyBiometryAPI_gender_smiling,

		SkyBiometryAPI_glasses,

		SkyBiometryAPI_gender_glasses,

		SkyBiometryAPI_smiling_glasses,

		SkyBiometryAPI_gender_smiling_glasses,

		SkyBiometryAPI_aggresiveDetector,

		SkyBiometryAPI_gender_aggresiveDetector,

		SkyBiometryAPI_smiling_aggresiveDetector,

		SkyBiometryAPI_gender_smiling_aggresiveDetector,

		SkyBiometryAPI_glasses_aggresiveDetector,

		SkyBiometryAPI_gender_glasses_aggresiveDetector,

		SkyBiometryAPI_smiling_glasses_aggresiveDetector,

		SkyBiometryAPI_gender_smiling_glasses_aggresiveDetector,

		SkyBiometryAPI_mood,

		SkyBiometryAPI_gender_mood,

		SkyBiometryAPI_smiling_mood,

		SkyBiometryAPI_gender_smiling_mood,

		SkyBiometryAPI_glasses_mood,

		SkyBiometryAPI_gender_glasses_mood,

		SkyBiometryAPI_smiling_glasses_mood,

		SkyBiometryAPI_gender_smiling_glasses_mood,

		SkyBiometryAPI_aggresiveDetector_mood,

		SkyBiometryAPI_gender_aggresiveDetector_mood,

		SkyBiometryAPI_smiling_aggresiveDetector_mood,

		SkyBiometryAPI_gender_smiling_aggresiveDetector_mood,

		SkyBiometryAPI_glasses_aggresiveDetector_mood,

		SkyBiometryAPI_gender_glasses_aggresiveDetector_mood,

		SkyBiometryAPI_smiling_glasses_aggresiveDetector_mood,

		SkyBiometryAPI_gender_smiling_glasses_aggresiveDetector_mood,

		SkyBiometryAPI_age,

		SkyBiometryAPI_gender_age,

		SkyBiometryAPI_smiling_age,

		SkyBiometryAPI_gender_smiling_age,

		SkyBiometryAPI_glasses_age,

		SkyBiometryAPI_gender_glasses_age,

		SkyBiometryAPI_smiling_glasses_age,

		SkyBiometryAPI_gender_smiling_glasses_age,

		SkyBiometryAPI_aggresiveDetector_age,

		SkyBiometryAPI_gender_aggresiveDetector_age,

		SkyBiometryAPI_smiling_aggresiveDetector_age,

		SkyBiometryAPI_gender_smiling_aggresiveDetector_age,

		SkyBiometryAPI_glasses_aggresiveDetector_age,

		SkyBiometryAPI_gender_glasses_aggresiveDetector_age,

		SkyBiometryAPI_smiling_glasses_aggresiveDetector_age,

		SkyBiometryAPI_gender_smiling_glasses_aggresiveDetector_age,

		SkyBiometryAPI_mood_age,

		SkyBiometryAPI_gender_mood_age,

		SkyBiometryAPI_smiling_mood_age,

		SkyBiometryAPI_gender_smiling_mood_age,

		SkyBiometryAPI_glasses_mood_age,

		SkyBiometryAPI_gender_glasses_mood_age,

		SkyBiometryAPI_smiling_glasses_mood_age,

		SkyBiometryAPI_gender_smiling_glasses_mood_age,

		SkyBiometryAPI_aggresiveDetector_mood_age,

		SkyBiometryAPI_gender_aggresiveDetector_mood_age,

		SkyBiometryAPI_smiling_aggresiveDetector_mood_age,

		SkyBiometryAPI_gender_smiling_aggresiveDetector_mood_age,

		SkyBiometryAPI_glasses_aggresiveDetector_mood_age,

		SkyBiometryAPI_gender_glasses_aggresiveDetector_mood_age,

		SkyBiometryAPI_smiling_glasses_aggresiveDetector_mood_age,

		SkyBiometryAPI_gender_smiling_glasses_aggresiveDetector_mood_age,

		SkyBiometryAPI_openEyes,

		SkyBiometryAPI_gender_openEyes,

		SkyBiometryAPI_smiling_openEyes,

		SkyBiometryAPI_gender_smiling_openEyes,

		SkyBiometryAPI_glasses_openEyes,

		SkyBiometryAPI_gender_glasses_openEyes,

		SkyBiometryAPI_smiling_glasses_openEyes,

		SkyBiometryAPI_gender_smiling_glasses_openEyes,

		SkyBiometryAPI_aggresiveDetector_openEyes,

		SkyBiometryAPI_gender_aggresiveDetector_openEyes,

		SkyBiometryAPI_smiling_aggresiveDetector_openEyes,

		SkyBiometryAPI_gender_smiling_aggresiveDetector_openEyes,

		SkyBiometryAPI_glasses_aggresiveDetector_openEyes,

		SkyBiometryAPI_gender_glasses_aggresiveDetector_openEyes,

		SkyBiometryAPI_smiling_glasses_aggresiveDetector_openEyes,

		SkyBiometryAPI_gender_smiling_glasses_aggresiveDetector_openEyes,

		SkyBiometryAPI_mood_openEyes,

		SkyBiometryAPI_gender_mood_openEyes,

		SkyBiometryAPI_smiling_mood_openEyes,

		SkyBiometryAPI_gender_smiling_mood_openEyes,

		SkyBiometryAPI_glasses_mood_openEyes,

		SkyBiometryAPI_gender_glasses_mood_openEyes,

		SkyBiometryAPI_smiling_glasses_mood_openEyes,

		SkyBiometryAPI_gender_smiling_glasses_mood_openEyes,

		SkyBiometryAPI_aggresiveDetector_mood_openEyes,

		SkyBiometryAPI_gender_aggresiveDetector_mood_openEyes,

		SkyBiometryAPI_smiling_aggresiveDetector_mood_openEyes,

		SkyBiometryAPI_gender_smiling_aggresiveDetector_mood_openEyes,

		SkyBiometryAPI_glasses_aggresiveDetector_mood_openEyes,

		SkyBiometryAPI_gender_glasses_aggresiveDetector_mood_openEyes,

		SkyBiometryAPI_smiling_glasses_aggresiveDetector_mood_openEyes,

		SkyBiometryAPI_gender_smiling_glasses_aggresiveDetector_mood_openEyes,

		SkyBiometryAPI_age_openEyes,

		SkyBiometryAPI_gender_age_openEyes,

		SkyBiometryAPI_smiling_age_openEyes,

		SkyBiometryAPI_gender_smiling_age_openEyes,

		SkyBiometryAPI_glasses_age_openEyes,

		SkyBiometryAPI_gender_glasses_age_openEyes,

		SkyBiometryAPI_smiling_glasses_age_openEyes,

		SkyBiometryAPI_gender_smiling_glasses_age_openEyes,

		SkyBiometryAPI_aggresiveDetector_age_openEyes,

		SkyBiometryAPI_gender_aggresiveDetector_age_openEyes,

		SkyBiometryAPI_smiling_aggresiveDetector_age_openEyes,

		SkyBiometryAPI_gender_smiling_aggresiveDetector_age_openEyes,

		SkyBiometryAPI_glasses_aggresiveDetector_age_openEyes,

		SkyBiometryAPI_gender_glasses_aggresiveDetector_age_openEyes,

		SkyBiometryAPI_smiling_glasses_aggresiveDetector_age_openEyes,

		SkyBiometryAPI_gender_smiling_glasses_aggresiveDetector_age_openEyes,

		SkyBiometryAPI_mood_age_openEyes,

		SkyBiometryAPI_gender_mood_age_openEyes,

		SkyBiometryAPI_smiling_mood_age_openEyes,

		SkyBiometryAPI_gender_smiling_mood_age_openEyes,

		SkyBiometryAPI_glasses_mood_age_openEyes,

		SkyBiometryAPI_gender_glasses_mood_age_openEyes,

		SkyBiometryAPI_smiling_glasses_mood_age_openEyes,

		SkyBiometryAPI_gender_smiling_glasses_mood_age_openEyes,

		SkyBiometryAPI_aggresiveDetector_mood_age_openEyes,

		SkyBiometryAPI_gender_aggresiveDetector_mood_age_openEyes,

		SkyBiometryAPI_smiling_aggresiveDetector_mood_age_openEyes,

		SkyBiometryAPI_gender_smiling_aggresiveDetector_mood_age_openEyes,

		SkyBiometryAPI_glasses_aggresiveDetector_mood_age_openEyes,

		SkyBiometryAPI_gender_glasses_aggresiveDetector_mood_age_openEyes,

		SkyBiometryAPI_smiling_glasses_aggresiveDetector_mood_age_openEyes,

		SkyBiometryAPI_gender_smiling_glasses_aggresiveDetector_mood_age_openEyes,

		MicrosoftFaceAPI, MicrosoftFaceAPI_landmarks, MicrosoftFaceAPI_age, MicrosoftFaceAPI_landmarks_age, MicrosoftFaceAPI_gender, MicrosoftFaceAPI_landmarks_gender, MicrosoftFaceAPI_age_gender, MicrosoftFaceAPI_landmarks_age_gender, MicrosoftFaceAPI_facialHair, MicrosoftFaceAPI_landmarks_facialHair, MicrosoftFaceAPI_age_facialHair, MicrosoftFaceAPI_landmarks_age_facialHair, MicrosoftFaceAPI_gender_facialHair, MicrosoftFaceAPI_landmarks_gender_facialHair, MicrosoftFaceAPI_age_gender_facialHair, MicrosoftFaceAPI_landmarks_age_gender_facialHair, MicrosoftFaceAPI_smile, MicrosoftFaceAPI_landmarks_smile, MicrosoftFaceAPI_age_smile, MicrosoftFaceAPI_landmarks_age_smile, MicrosoftFaceAPI_gender_smile, MicrosoftFaceAPI_landmarks_gender_smile, MicrosoftFaceAPI_age_gender_smile, MicrosoftFaceAPI_landmarks_age_gender_smile, MicrosoftFaceAPI_facialHair_smile, MicrosoftFaceAPI_landmarks_facialHair_smile, MicrosoftFaceAPI_age_facialHair_smile, MicrosoftFaceAPI_landmarks_age_facialHair_smile, MicrosoftFaceAPI_gender_facialHair_smile, MicrosoftFaceAPI_landmarks_gender_facialHair_smile, MicrosoftFaceAPI_age_gender_facialHair_smile, MicrosoftFaceAPI_landmarks_age_gender_facialHair_smile, MicrosoftFaceAPI_headPose, MicrosoftFaceAPI_landmarks_headPose, MicrosoftFaceAPI_age_headPose, MicrosoftFaceAPI_landmarks_age_headPose, MicrosoftFaceAPI_gender_headPose, MicrosoftFaceAPI_landmarks_gender_headPose, MicrosoftFaceAPI_age_gender_headPose, MicrosoftFaceAPI_landmarks_age_gender_headPose, MicrosoftFaceAPI_facialHair_headPose, MicrosoftFaceAPI_landmarks_facialHair_headPose, MicrosoftFaceAPI_age_facialHair_headPose, MicrosoftFaceAPI_landmarks_age_facialHair_headPose, MicrosoftFaceAPI_gender_facialHair_headPose, MicrosoftFaceAPI_landmarks_gender_facialHair_headPose, MicrosoftFaceAPI_age_gender_facialHair_headPose, MicrosoftFaceAPI_landmarks_age_gender_facialHair_headPose, MicrosoftFaceAPI_smile_headPose, MicrosoftFaceAPI_landmarks_smile_headPose, MicrosoftFaceAPI_age_smile_headPose, MicrosoftFaceAPI_landmarks_age_smile_headPose, MicrosoftFaceAPI_gender_smile_headPose, MicrosoftFaceAPI_landmarks_gender_smile_headPose, MicrosoftFaceAPI_age_gender_smile_headPose, MicrosoftFaceAPI_landmarks_age_gender_smile_headPose, MicrosoftFaceAPI_facialHair_smile_headPose, MicrosoftFaceAPI_landmarks_facialHair_smile_headPose, MicrosoftFaceAPI_age_facialHair_smile_headPose, MicrosoftFaceAPI_landmarks_age_facialHair_smile_headPose, MicrosoftFaceAPI_gender_facialHair_smile_headPose, MicrosoftFaceAPI_landmarks_gender_facialHair_smile_headPose, MicrosoftFaceAPI_age_gender_facialHair_smile_headPose, MicrosoftFaceAPI_landmarks_age_gender_facialHair_smile_headPose, GMSFaceDetector, GMSFaceDetector_allLandmarks, GMSFaceDetector_allClassifications, GMSFaceDetector_allLandmarks_allClassifications, GMSFaceDetector_accurateMode, GMSFaceDetector_allLandmarks_accurateMode, GMSFaceDetector_allClassifications_accurateMode, GMSFaceDetector_allLandmarks_allClassifications_accurateMode, ViolaJonesOpenCVFaceDetector
	}

	private Context context;

	protected FaceDetectionRegistry(Context context) {
		this.context = context;
		services = new HashMap<ServiceIndex, FaceDetectionServiceClient>();
		numOfServices = ServiceIndex.values().length;
		numOfServiceConfigurations = ServiceConfigurationIndex.values().length;
	}

	private static FaceDetectionRegistry instance;

	public static FaceDetectionRegistry getInstance(Context context) {
		if (instance == null) {
			instance = new FaceDetectionRegistry(context);
		}
		return instance;
	}

	private final int numOfServices;
	private final int numOfServiceConfigurations;

	public int getNumOfServices() {
		return numOfServices;
	}

	public int getNumOfServiceConfigurations() {
		return numOfServiceConfigurations;
	}

	private String[] serviceNames;

	public String[] getServiceNames() {
		if (this.serviceNames == null) {
			ServiceIndex[] states = ServiceIndex.values();
			serviceNames = new String[states.length];
			for (int i = 0; i < states.length; i++) {
				serviceNames[i] = states[i].name();
			}
		}
		return serviceNames;
	};

	private String[] serviceConfigurationNames;

	public String[] getServiceConfigurationNames() {
		if (this.serviceConfigurationNames == null) {
			ServiceConfigurationIndex[] states = ServiceConfigurationIndex.values();
			serviceConfigurationNames = new String[states.length];
			for (int i = 0; i < states.length; i++) {
				serviceConfigurationNames[i] = states[i].name();
			}
		}
		return serviceConfigurationNames;
	};

	private Map<ServiceIndex, FaceDetectionServiceClient> services;

	public FaceDetectionServiceClient getServiceByIndex(int index) {
		if (index < 0 || index >= numOfServices)
			throw new IllegalArgumentException("Index out of bounds");
		return getServiceByIndex(ServiceIndex.values()[index]);
	}

	public FaceDetectionServiceClient getServiceByIndex(ServiceIndex index) {
		if (services.get(index) == null) {
			switch (index) {
			case FaceRectAPI:
				services.put(index, new FaceRectAPI(context.getResources().getString(R.string.x_mashape_key)));
				break;
			case SkyBiometryAPI:
				services.put(index, new SkyBiometryAPI(context.getResources().getString(R.string.x_mashape_key),
						context.getResources().getString(R.string.sky_biometry_api_key), context.getResources().getString(R.string.sky_biometry_api_secret)));
				break;
			case MicrosoftFaceAPI:
				services.put(index, new MicrosoftFaceAPI(context.getResources().getString(R.string.microsoft_faceapi_key)));
				break;
			case GMSFaceDetector:
				services.put(index, new GMSFaceDetector(context));
				break;
			case ViolaJonesOpenCVFaceDetector:
				services.put(index, new ViolaJonesOpenCVFaceDetector(context));
				break;
			}
		}
		return services.get(index);
	}

	public FaceDetectionServiceClient getServiceConfigurationByIndex(int index) {
		if (index < 0 || index >= numOfServiceConfigurations)
			throw new IllegalArgumentException("Index out of bounds");
		return getServiceConfigurationByIndex(ServiceConfigurationIndex.values()[index]);
	}

	public FaceDetectionServiceClient getServiceConfigurationByIndex(ServiceConfigurationIndex index) {
		FaceRectAPI service1 = null;
		SkyBiometryAPI service2 = null;
		MicrosoftFaceAPI service3 = null;
		GMSFaceDetector service4 = null;
		ViolaJonesOpenCVFaceDetector service5 = null;

		switch (index) {

		case GMSFaceDetector:
			service4 = (GMSFaceDetector) getServiceByIndex(ServiceIndex.GMSFaceDetector);
			service4.setParams(false, false, false);
			return service4;
		case GMSFaceDetector_allLandmarks:
			service4 = (GMSFaceDetector) getServiceByIndex(ServiceIndex.GMSFaceDetector);
			service4.setParams(true, false, false);
			return service4;
		case GMSFaceDetector_allClassifications:
			service4 = (GMSFaceDetector) getServiceByIndex(ServiceIndex.GMSFaceDetector);
			service4.setParams(false, true, false);
			return service4;
		case GMSFaceDetector_allLandmarks_allClassifications:
			service4 = (GMSFaceDetector) getServiceByIndex(ServiceIndex.GMSFaceDetector);
			service4.setParams(true, true, false);
			return service4;
		case GMSFaceDetector_accurateMode:
			service4 = (GMSFaceDetector) getServiceByIndex(ServiceIndex.GMSFaceDetector);
			service4.setParams(false, false, true);
			return service4;
		case GMSFaceDetector_allLandmarks_accurateMode:
			service4 = (GMSFaceDetector) getServiceByIndex(ServiceIndex.GMSFaceDetector);
			service4.setParams(true, false, true);
			return service4;
		case GMSFaceDetector_allClassifications_accurateMode:
			service4 = (GMSFaceDetector) getServiceByIndex(ServiceIndex.GMSFaceDetector);
			service4.setParams(false, true, true);
			return service4;
		case GMSFaceDetector_allLandmarks_allClassifications_accurateMode:
			service4 = (GMSFaceDetector) getServiceByIndex(ServiceIndex.GMSFaceDetector);
			service4.setParams(true, true, true);
			return service4;

		case ViolaJonesOpenCVFaceDetector:
			service5 = (ViolaJonesOpenCVFaceDetector) getServiceByIndex(ServiceIndex.ViolaJonesOpenCVFaceDetector);
			service5.setParams(0.2f);
			return service5;

		case FaceRectAPI:
			service1 = (FaceRectAPI) getServiceByIndex(ServiceIndex.FaceRectAPI);
			service1.setParams(false);
			return service1;
		case FaceRectAPI_features:
			service1 = (FaceRectAPI) getServiceByIndex(ServiceIndex.FaceRectAPI);
			service1.setParams(true);
			return service1;

		case SkyBiometryAPI:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(false, false, false, false, false, false, false);
			return service2;
		case SkyBiometryAPI_gender:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(true, false, false, false, false, false, false);
			return service2;
		case SkyBiometryAPI_smiling:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(false, true, false, false, false, false, false);
			return service2;
		case SkyBiometryAPI_gender_smiling:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(true, true, false, false, false, false, false);
			return service2;
		case SkyBiometryAPI_glasses:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(false, false, true, false, false, false, false);
			return service2;
		case SkyBiometryAPI_gender_glasses:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(true, false, true, false, false, false, false);
			return service2;
		case SkyBiometryAPI_smiling_glasses:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(false, true, true, false, false, false, false);
			return service2;
		case SkyBiometryAPI_gender_smiling_glasses:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(true, true, true, false, false, false, false);
			return service2;
		case SkyBiometryAPI_aggresiveDetector:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(false, false, false, true, false, false, false);
			return service2;
		case SkyBiometryAPI_gender_aggresiveDetector:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(true, false, false, true, false, false, false);
			return service2;
		case SkyBiometryAPI_smiling_aggresiveDetector:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(false, true, false, true, false, false, false);
			return service2;
		case SkyBiometryAPI_gender_smiling_aggresiveDetector:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(true, true, false, true, false, false, false);
			return service2;
		case SkyBiometryAPI_glasses_aggresiveDetector:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(false, false, true, true, false, false, false);
			return service2;
		case SkyBiometryAPI_gender_glasses_aggresiveDetector:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(true, false, true, true, false, false, false);
			return service2;
		case SkyBiometryAPI_smiling_glasses_aggresiveDetector:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(false, true, true, true, false, false, false);
			return service2;
		case SkyBiometryAPI_gender_smiling_glasses_aggresiveDetector:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(true, true, true, true, false, false, false);
			return service2;

		case SkyBiometryAPI_mood:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(false, false, false, false, true, false, false);
			return service2;
		case SkyBiometryAPI_gender_mood:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(true, false, false, false, true, false, false);
			return service2;
		case SkyBiometryAPI_smiling_mood:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(false, true, false, false, true, false, false);
			return service2;
		case SkyBiometryAPI_gender_smiling_mood:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(true, true, false, false, true, false, false);
			return service2;
		case SkyBiometryAPI_glasses_mood:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(false, false, true, false, true, false, false);
			return service2;
		case SkyBiometryAPI_gender_glasses_mood:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(true, false, true, false, true, false, false);
			return service2;
		case SkyBiometryAPI_smiling_glasses_mood:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(false, true, true, false, true, false, false);
			return service2;
		case SkyBiometryAPI_gender_smiling_glasses_mood:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(true, true, true, false, true, false, false);
			return service2;
		case SkyBiometryAPI_aggresiveDetector_mood:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(false, false, false, true, true, false, false);
			return service2;
		case SkyBiometryAPI_gender_aggresiveDetector_mood:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(true, false, false, true, true, false, false);
			return service2;
		case SkyBiometryAPI_smiling_aggresiveDetector_mood:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(false, true, false, true, true, false, false);
			return service2;
		case SkyBiometryAPI_gender_smiling_aggresiveDetector_mood:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(true, true, false, true, true, false, false);
			return service2;
		case SkyBiometryAPI_glasses_aggresiveDetector_mood:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(false, false, true, true, true, false, false);
			return service2;
		case SkyBiometryAPI_gender_glasses_aggresiveDetector_mood:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(true, false, true, true, true, false, false);
			return service2;
		case SkyBiometryAPI_smiling_glasses_aggresiveDetector_mood:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(false, true, true, true, true, false, false);
			return service2;
		case SkyBiometryAPI_gender_smiling_glasses_aggresiveDetector_mood:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(true, true, true, true, true, false, false);
			return service2;

		case SkyBiometryAPI_age:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(false, false, false, false, false, true, false);
			return service2;
		case SkyBiometryAPI_gender_age:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(true, false, false, false, false, true, false);
			return service2;
		case SkyBiometryAPI_smiling_age:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(false, true, false, false, false, true, false);
			return service2;
		case SkyBiometryAPI_gender_smiling_age:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(true, true, false, false, false, true, false);
			return service2;
		case SkyBiometryAPI_glasses_age:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(false, false, true, false, false, true, false);
			return service2;
		case SkyBiometryAPI_gender_glasses_age:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(true, false, true, false, false, true, false);
			return service2;
		case SkyBiometryAPI_smiling_glasses_age:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(false, true, true, false, false, true, false);
			return service2;
		case SkyBiometryAPI_gender_smiling_glasses_age:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(true, true, true, false, false, true, false);
			return service2;
		case SkyBiometryAPI_aggresiveDetector_age:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(false, false, false, true, false, true, false);
			return service2;
		case SkyBiometryAPI_gender_aggresiveDetector_age:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(true, false, false, true, false, true, false);
			return service2;
		case SkyBiometryAPI_smiling_aggresiveDetector_age:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(false, true, false, true, false, true, false);
			return service2;
		case SkyBiometryAPI_gender_smiling_aggresiveDetector_age:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(true, true, false, true, false, true, false);
			return service2;
		case SkyBiometryAPI_glasses_aggresiveDetector_age:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(false, false, true, true, false, true, false);
			return service2;
		case SkyBiometryAPI_gender_glasses_aggresiveDetector_age:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(true, false, true, true, false, true, false);
			return service2;
		case SkyBiometryAPI_smiling_glasses_aggresiveDetector_age:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(false, true, true, true, false, true, false);
			return service2;
		case SkyBiometryAPI_gender_smiling_glasses_aggresiveDetector_age:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(true, true, true, true, false, true, false);
			return service2;

		case SkyBiometryAPI_mood_age:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(false, false, false, false, true, true, false);
			return service2;
		case SkyBiometryAPI_gender_mood_age:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(true, false, false, false, true, true, false);
			return service2;
		case SkyBiometryAPI_smiling_mood_age:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(false, true, false, false, true, true, false);
			return service2;
		case SkyBiometryAPI_gender_smiling_mood_age:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(true, true, false, false, true, true, false);
			return service2;
		case SkyBiometryAPI_glasses_mood_age:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(false, false, true, false, true, true, false);
			return service2;
		case SkyBiometryAPI_gender_glasses_mood_age:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(true, false, true, false, true, true, false);
			return service2;
		case SkyBiometryAPI_smiling_glasses_mood_age:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(false, true, true, false, true, true, false);
			return service2;
		case SkyBiometryAPI_gender_smiling_glasses_mood_age:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(true, true, true, false, true, true, false);
			return service2;
		case SkyBiometryAPI_aggresiveDetector_mood_age:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(false, false, false, true, true, true, false);
			return service2;
		case SkyBiometryAPI_gender_aggresiveDetector_mood_age:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(true, false, false, true, true, true, false);
			return service2;
		case SkyBiometryAPI_smiling_aggresiveDetector_mood_age:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(false, true, false, true, true, true, false);
			return service2;
		case SkyBiometryAPI_gender_smiling_aggresiveDetector_mood_age:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(true, true, false, true, true, true, false);
			return service2;
		case SkyBiometryAPI_glasses_aggresiveDetector_mood_age:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(false, false, true, true, true, true, false);
			return service2;
		case SkyBiometryAPI_gender_glasses_aggresiveDetector_mood_age:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(true, false, true, true, true, true, false);
			return service2;
		case SkyBiometryAPI_smiling_glasses_aggresiveDetector_mood_age:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(false, true, true, true, true, true, false);
			return service2;
		case SkyBiometryAPI_gender_smiling_glasses_aggresiveDetector_mood_age:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(true, true, true, true, true, true, false);
			return service2;

		case SkyBiometryAPI_openEyes:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(false, false, false, false, false, false, true);
			return service2;
		case SkyBiometryAPI_gender_openEyes:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(true, false, false, false, false, false, true);
			return service2;
		case SkyBiometryAPI_smiling_openEyes:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(false, true, false, false, false, false, true);
			return service2;
		case SkyBiometryAPI_gender_smiling_openEyes:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(true, true, false, false, false, false, true);
			return service2;
		case SkyBiometryAPI_glasses_openEyes:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(false, false, true, false, false, false, true);
			return service2;
		case SkyBiometryAPI_gender_glasses_openEyes:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(true, false, true, false, false, false, true);
			return service2;
		case SkyBiometryAPI_smiling_glasses_openEyes:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(false, true, true, false, false, false, true);
			return service2;
		case SkyBiometryAPI_gender_smiling_glasses_openEyes:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(true, true, true, false, false, false, true);
			return service2;
		case SkyBiometryAPI_aggresiveDetector_openEyes:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(false, false, false, true, false, false, true);
			return service2;
		case SkyBiometryAPI_gender_aggresiveDetector_openEyes:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(true, false, false, true, false, false, true);
			return service2;
		case SkyBiometryAPI_smiling_aggresiveDetector_openEyes:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(false, true, false, true, false, false, true);
			return service2;
		case SkyBiometryAPI_gender_smiling_aggresiveDetector_openEyes:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(true, true, false, true, false, false, true);
			return service2;
		case SkyBiometryAPI_glasses_aggresiveDetector_openEyes:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(false, false, true, true, false, false, true);
			return service2;
		case SkyBiometryAPI_gender_glasses_aggresiveDetector_openEyes:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(true, false, true, true, false, false, true);
			return service2;
		case SkyBiometryAPI_smiling_glasses_aggresiveDetector_openEyes:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(false, true, true, true, false, false, true);
			return service2;
		case SkyBiometryAPI_gender_smiling_glasses_aggresiveDetector_openEyes:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(true, true, true, true, false, false, true);
			return service2;

		case SkyBiometryAPI_mood_openEyes:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(false, false, false, false, true, false, true);
			return service2;
		case SkyBiometryAPI_gender_mood_openEyes:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(true, false, false, false, true, false, true);
			return service2;
		case SkyBiometryAPI_smiling_mood_openEyes:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(false, true, false, false, true, false, true);
			return service2;
		case SkyBiometryAPI_gender_smiling_mood_openEyes:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(true, true, false, false, true, false, true);
			return service2;
		case SkyBiometryAPI_glasses_mood_openEyes:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(false, false, true, false, true, false, true);
			return service2;
		case SkyBiometryAPI_gender_glasses_mood_openEyes:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(true, false, true, false, true, false, true);
			return service2;
		case SkyBiometryAPI_smiling_glasses_mood_openEyes:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(false, true, true, false, true, false, true);
			return service2;
		case SkyBiometryAPI_gender_smiling_glasses_mood_openEyes:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(true, true, true, false, true, false, true);
			return service2;
		case SkyBiometryAPI_aggresiveDetector_mood_openEyes:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(false, false, false, true, true, false, true);
			return service2;
		case SkyBiometryAPI_gender_aggresiveDetector_mood_openEyes:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(true, false, false, true, true, false, true);
			return service2;
		case SkyBiometryAPI_smiling_aggresiveDetector_mood_openEyes:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(false, true, false, true, true, false, true);
			return service2;
		case SkyBiometryAPI_gender_smiling_aggresiveDetector_mood_openEyes:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(true, true, false, true, true, false, true);
			return service2;
		case SkyBiometryAPI_glasses_aggresiveDetector_mood_openEyes:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(false, false, true, true, true, false, true);
			return service2;
		case SkyBiometryAPI_gender_glasses_aggresiveDetector_mood_openEyes:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(true, false, true, true, true, false, true);
			return service2;
		case SkyBiometryAPI_smiling_glasses_aggresiveDetector_mood_openEyes:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(false, true, true, true, true, false, true);
			return service2;
		case SkyBiometryAPI_gender_smiling_glasses_aggresiveDetector_mood_openEyes:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(true, true, true, true, true, false, true);
			return service2;
		case SkyBiometryAPI_age_openEyes:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(false, false, false, false, false, true, true);
			return service2;
		case SkyBiometryAPI_gender_age_openEyes:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(true, false, false, false, false, true, true);
			return service2;
		case SkyBiometryAPI_smiling_age_openEyes:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(false, true, false, false, false, true, true);
			return service2;
		case SkyBiometryAPI_gender_smiling_age_openEyes:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(true, true, false, false, false, true, true);
			return service2;
		case SkyBiometryAPI_glasses_age_openEyes:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(false, false, true, false, false, true, true);
			return service2;
		case SkyBiometryAPI_gender_glasses_age_openEyes:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(true, false, true, false, false, true, true);
			return service2;
		case SkyBiometryAPI_smiling_glasses_age_openEyes:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(false, true, true, false, false, true, true);
			return service2;
		case SkyBiometryAPI_gender_smiling_glasses_age_openEyes:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(true, true, true, false, false, true, true);
			return service2;
		case SkyBiometryAPI_aggresiveDetector_age_openEyes:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(false, false, false, true, false, true, true);
			return service2;
		case SkyBiometryAPI_gender_aggresiveDetector_age_openEyes:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(true, false, false, true, false, true, true);
			return service2;
		case SkyBiometryAPI_smiling_aggresiveDetector_age_openEyes:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(false, true, false, true, false, true, true);
			return service2;
		case SkyBiometryAPI_gender_smiling_aggresiveDetector_age_openEyes:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(true, true, false, true, false, true, true);
			return service2;
		case SkyBiometryAPI_glasses_aggresiveDetector_age_openEyes:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(false, false, true, true, false, true, true);
			return service2;
		case SkyBiometryAPI_gender_glasses_aggresiveDetector_age_openEyes:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(true, false, true, true, false, true, true);
			return service2;
		case SkyBiometryAPI_smiling_glasses_aggresiveDetector_age_openEyes:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(false, true, true, true, false, true, true);
			return service2;
		case SkyBiometryAPI_gender_smiling_glasses_aggresiveDetector_age_openEyes:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(true, true, true, true, false, true, true);
			return service2;

		case SkyBiometryAPI_mood_age_openEyes:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(false, false, false, false, true, true, true);
			return service2;
		case SkyBiometryAPI_gender_mood_age_openEyes:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(true, false, false, false, true, true, true);
			return service2;
		case SkyBiometryAPI_smiling_mood_age_openEyes:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(false, true, false, false, true, true, true);
			return service2;
		case SkyBiometryAPI_gender_smiling_mood_age_openEyes:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(true, true, false, false, true, true, true);
			return service2;
		case SkyBiometryAPI_glasses_mood_age_openEyes:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(false, false, true, false, true, true, true);
			return service2;
		case SkyBiometryAPI_gender_glasses_mood_age_openEyes:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(true, false, true, false, true, true, true);
			return service2;
		case SkyBiometryAPI_smiling_glasses_mood_age_openEyes:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(false, true, true, false, true, true, true);
			return service2;
		case SkyBiometryAPI_gender_smiling_glasses_mood_age_openEyes:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(true, true, true, false, true, true, true);
			return service2;
		case SkyBiometryAPI_aggresiveDetector_mood_age_openEyes:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(false, false, false, true, true, true, true);
			return service2;
		case SkyBiometryAPI_gender_aggresiveDetector_mood_age_openEyes:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(true, false, false, true, true, true, true);
			return service2;
		case SkyBiometryAPI_smiling_aggresiveDetector_mood_age_openEyes:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(false, true, false, true, true, true, true);
			return service2;
		case SkyBiometryAPI_gender_smiling_aggresiveDetector_mood_age_openEyes:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(true, true, false, true, true, true, true);
			return service2;
		case SkyBiometryAPI_glasses_aggresiveDetector_mood_age_openEyes:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(false, false, true, true, true, true, true);
			return service2;
		case SkyBiometryAPI_gender_glasses_aggresiveDetector_mood_age_openEyes:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(true, false, true, true, true, true, true);
			return service2;
		case SkyBiometryAPI_smiling_glasses_aggresiveDetector_mood_age_openEyes:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(false, true, true, true, true, true, true);
			return service2;
		case SkyBiometryAPI_gender_smiling_glasses_aggresiveDetector_mood_age_openEyes:
			service2 = (SkyBiometryAPI) getServiceByIndex(ServiceIndex.SkyBiometryAPI);
			service2.setParams(true, true, true, true, true, true, true);
			return service2;

		case MicrosoftFaceAPI:
			service3 = (MicrosoftFaceAPI) getServiceByIndex(ServiceIndex.MicrosoftFaceAPI);
			service3.setParams(false, false, false, false, false, false);
			return service3;
		case MicrosoftFaceAPI_landmarks:
			service3 = (MicrosoftFaceAPI) getServiceByIndex(ServiceIndex.MicrosoftFaceAPI);
			service3.setParams(true, false, false, false, false, false);
			return service3;
		case MicrosoftFaceAPI_age:
			service3 = (MicrosoftFaceAPI) getServiceByIndex(ServiceIndex.MicrosoftFaceAPI);
			service3.setParams(false, true, false, false, false, false);
			return service3;
		case MicrosoftFaceAPI_landmarks_age:
			service3 = (MicrosoftFaceAPI) getServiceByIndex(ServiceIndex.MicrosoftFaceAPI);
			service3.setParams(true, true, false, false, false, false);
			return service3;
		case MicrosoftFaceAPI_gender:
			service3 = (MicrosoftFaceAPI) getServiceByIndex(ServiceIndex.MicrosoftFaceAPI);
			service3.setParams(false, false, true, false, false, false);
			return service3;
		case MicrosoftFaceAPI_landmarks_gender:
			service3 = (MicrosoftFaceAPI) getServiceByIndex(ServiceIndex.MicrosoftFaceAPI);
			service3.setParams(true, false, true, false, false, false);
			return service3;
		case MicrosoftFaceAPI_age_gender:
			service3 = (MicrosoftFaceAPI) getServiceByIndex(ServiceIndex.MicrosoftFaceAPI);
			service3.setParams(false, true, true, false, false, false);
			return service3;
		case MicrosoftFaceAPI_landmarks_age_gender:
			service3 = (MicrosoftFaceAPI) getServiceByIndex(ServiceIndex.MicrosoftFaceAPI);
			service3.setParams(true, true, true, false, false, false);
			return service3;
		case MicrosoftFaceAPI_facialHair:
			service3 = (MicrosoftFaceAPI) getServiceByIndex(ServiceIndex.MicrosoftFaceAPI);
			service3.setParams(false, false, false, true, false, false);
			return service3;
		case MicrosoftFaceAPI_landmarks_facialHair:
			service3 = (MicrosoftFaceAPI) getServiceByIndex(ServiceIndex.MicrosoftFaceAPI);
			service3.setParams(true, false, false, true, false, false);
			return service3;
		case MicrosoftFaceAPI_age_facialHair:
			service3 = (MicrosoftFaceAPI) getServiceByIndex(ServiceIndex.MicrosoftFaceAPI);
			service3.setParams(false, true, false, true, false, false);
			return service3;
		case MicrosoftFaceAPI_landmarks_age_facialHair:
			service3 = (MicrosoftFaceAPI) getServiceByIndex(ServiceIndex.MicrosoftFaceAPI);
			service3.setParams(true, true, false, true, false, false);
			return service3;
		case MicrosoftFaceAPI_gender_facialHair:
			service3 = (MicrosoftFaceAPI) getServiceByIndex(ServiceIndex.MicrosoftFaceAPI);
			service3.setParams(false, false, true, true, false, false);
			return service3;
		case MicrosoftFaceAPI_landmarks_gender_facialHair:
			service3 = (MicrosoftFaceAPI) getServiceByIndex(ServiceIndex.MicrosoftFaceAPI);
			service3.setParams(true, false, true, true, false, false);
			return service3;
		case MicrosoftFaceAPI_age_gender_facialHair:
			service3 = (MicrosoftFaceAPI) getServiceByIndex(ServiceIndex.MicrosoftFaceAPI);
			service3.setParams(false, true, true, true, false, false);
			return service3;
		case MicrosoftFaceAPI_landmarks_age_gender_facialHair:
			service3 = (MicrosoftFaceAPI) getServiceByIndex(ServiceIndex.MicrosoftFaceAPI);
			service3.setParams(true, true, true, true, false, false);
			return service3;
		case MicrosoftFaceAPI_smile:
			service3 = (MicrosoftFaceAPI) getServiceByIndex(ServiceIndex.MicrosoftFaceAPI);
			service3.setParams(false, false, false, false, true, false);
			return service3;
		case MicrosoftFaceAPI_landmarks_smile:
			service3 = (MicrosoftFaceAPI) getServiceByIndex(ServiceIndex.MicrosoftFaceAPI);
			service3.setParams(true, false, false, false, true, false);
			return service3;
		case MicrosoftFaceAPI_age_smile:
			service3 = (MicrosoftFaceAPI) getServiceByIndex(ServiceIndex.MicrosoftFaceAPI);
			service3.setParams(false, true, false, false, true, false);
			return service3;
		case MicrosoftFaceAPI_landmarks_age_smile:
			service3 = (MicrosoftFaceAPI) getServiceByIndex(ServiceIndex.MicrosoftFaceAPI);
			service3.setParams(true, true, false, false, true, false);
			return service3;
		case MicrosoftFaceAPI_gender_smile:
			service3 = (MicrosoftFaceAPI) getServiceByIndex(ServiceIndex.MicrosoftFaceAPI);
			service3.setParams(false, false, true, false, true, false);
			return service3;
		case MicrosoftFaceAPI_landmarks_gender_smile:
			service3 = (MicrosoftFaceAPI) getServiceByIndex(ServiceIndex.MicrosoftFaceAPI);
			service3.setParams(true, false, true, false, true, false);
			return service3;
		case MicrosoftFaceAPI_age_gender_smile:
			service3 = (MicrosoftFaceAPI) getServiceByIndex(ServiceIndex.MicrosoftFaceAPI);
			service3.setParams(false, true, true, false, true, false);
			return service3;
		case MicrosoftFaceAPI_landmarks_age_gender_smile:
			service3 = (MicrosoftFaceAPI) getServiceByIndex(ServiceIndex.MicrosoftFaceAPI);
			service3.setParams(true, true, true, false, true, false);
			return service3;
		case MicrosoftFaceAPI_facialHair_smile:
			service3 = (MicrosoftFaceAPI) getServiceByIndex(ServiceIndex.MicrosoftFaceAPI);
			service3.setParams(false, false, false, true, true, false);
			return service3;
		case MicrosoftFaceAPI_landmarks_facialHair_smile:
			service3 = (MicrosoftFaceAPI) getServiceByIndex(ServiceIndex.MicrosoftFaceAPI);
			service3.setParams(true, false, false, true, true, false);
			return service3;
		case MicrosoftFaceAPI_age_facialHair_smile:
			service3 = (MicrosoftFaceAPI) getServiceByIndex(ServiceIndex.MicrosoftFaceAPI);
			service3.setParams(false, true, false, true, true, false);
			return service3;
		case MicrosoftFaceAPI_landmarks_age_facialHair_smile:
			service3 = (MicrosoftFaceAPI) getServiceByIndex(ServiceIndex.MicrosoftFaceAPI);
			service3.setParams(true, true, false, true, true, false);
			return service3;
		case MicrosoftFaceAPI_gender_facialHair_smile:
			service3 = (MicrosoftFaceAPI) getServiceByIndex(ServiceIndex.MicrosoftFaceAPI);
			service3.setParams(false, false, true, true, true, false);
			return service3;
		case MicrosoftFaceAPI_landmarks_gender_facialHair_smile:
			service3 = (MicrosoftFaceAPI) getServiceByIndex(ServiceIndex.MicrosoftFaceAPI);
			service3.setParams(true, false, true, true, true, false);
			return service3;
		case MicrosoftFaceAPI_age_gender_facialHair_smile:
			service3 = (MicrosoftFaceAPI) getServiceByIndex(ServiceIndex.MicrosoftFaceAPI);
			service3.setParams(false, true, true, true, true, false);
			return service3;
		case MicrosoftFaceAPI_landmarks_age_gender_facialHair_smile:
			service3 = (MicrosoftFaceAPI) getServiceByIndex(ServiceIndex.MicrosoftFaceAPI);
			service3.setParams(true, true, true, true, true, false);
			return service3;
		case MicrosoftFaceAPI_headPose:
			service3 = (MicrosoftFaceAPI) getServiceByIndex(ServiceIndex.MicrosoftFaceAPI);
			service3.setParams(false, false, false, false, false, true);
			return service3;
		case MicrosoftFaceAPI_landmarks_headPose:
			service3 = (MicrosoftFaceAPI) getServiceByIndex(ServiceIndex.MicrosoftFaceAPI);
			service3.setParams(true, false, false, false, false, true);
			return service3;
		case MicrosoftFaceAPI_age_headPose:
			service3 = (MicrosoftFaceAPI) getServiceByIndex(ServiceIndex.MicrosoftFaceAPI);
			service3.setParams(false, true, false, false, false, true);
			return service3;
		case MicrosoftFaceAPI_landmarks_age_headPose:
			service3 = (MicrosoftFaceAPI) getServiceByIndex(ServiceIndex.MicrosoftFaceAPI);
			service3.setParams(true, true, false, false, false, true);
			return service3;
		case MicrosoftFaceAPI_gender_headPose:
			service3 = (MicrosoftFaceAPI) getServiceByIndex(ServiceIndex.MicrosoftFaceAPI);
			service3.setParams(false, false, true, false, false, true);
			return service3;
		case MicrosoftFaceAPI_landmarks_gender_headPose:
			service3 = (MicrosoftFaceAPI) getServiceByIndex(ServiceIndex.MicrosoftFaceAPI);
			service3.setParams(true, false, true, false, false, true);
			return service3;
		case MicrosoftFaceAPI_age_gender_headPose:
			service3 = (MicrosoftFaceAPI) getServiceByIndex(ServiceIndex.MicrosoftFaceAPI);
			service3.setParams(false, true, true, false, false, true);
			return service3;
		case MicrosoftFaceAPI_landmarks_age_gender_headPose:
			service3 = (MicrosoftFaceAPI) getServiceByIndex(ServiceIndex.MicrosoftFaceAPI);
			service3.setParams(true, true, true, false, false, true);
			return service3;
		case MicrosoftFaceAPI_facialHair_headPose:
			service3 = (MicrosoftFaceAPI) getServiceByIndex(ServiceIndex.MicrosoftFaceAPI);
			service3.setParams(false, false, false, true, false, true);
			return service3;
		case MicrosoftFaceAPI_landmarks_facialHair_headPose:
			service3 = (MicrosoftFaceAPI) getServiceByIndex(ServiceIndex.MicrosoftFaceAPI);
			service3.setParams(true, false, false, true, false, true);
			return service3;
		case MicrosoftFaceAPI_age_facialHair_headPose:
			service3 = (MicrosoftFaceAPI) getServiceByIndex(ServiceIndex.MicrosoftFaceAPI);
			service3.setParams(false, true, false, true, false, true);
			return service3;
		case MicrosoftFaceAPI_landmarks_age_facialHair_headPose:
			service3 = (MicrosoftFaceAPI) getServiceByIndex(ServiceIndex.MicrosoftFaceAPI);
			service3.setParams(true, true, false, true, false, true);
			return service3;
		case MicrosoftFaceAPI_gender_facialHair_headPose:
			service3 = (MicrosoftFaceAPI) getServiceByIndex(ServiceIndex.MicrosoftFaceAPI);
			service3.setParams(false, false, true, true, false, true);
			return service3;
		case MicrosoftFaceAPI_landmarks_gender_facialHair_headPose:
			service3 = (MicrosoftFaceAPI) getServiceByIndex(ServiceIndex.MicrosoftFaceAPI);
			service3.setParams(true, false, true, true, false, true);
			return service3;
		case MicrosoftFaceAPI_age_gender_facialHair_headPose:
			service3 = (MicrosoftFaceAPI) getServiceByIndex(ServiceIndex.MicrosoftFaceAPI);
			service3.setParams(false, true, true, true, false, true);
			return service3;
		case MicrosoftFaceAPI_landmarks_age_gender_facialHair_headPose:
			service3 = (MicrosoftFaceAPI) getServiceByIndex(ServiceIndex.MicrosoftFaceAPI);
			service3.setParams(true, true, true, true, false, true);
			return service3;
		case MicrosoftFaceAPI_smile_headPose:
			service3 = (MicrosoftFaceAPI) getServiceByIndex(ServiceIndex.MicrosoftFaceAPI);
			service3.setParams(false, false, false, false, true, true);
			return service3;
		case MicrosoftFaceAPI_landmarks_smile_headPose:
			service3 = (MicrosoftFaceAPI) getServiceByIndex(ServiceIndex.MicrosoftFaceAPI);
			service3.setParams(true, false, false, false, true, true);
			return service3;
		case MicrosoftFaceAPI_age_smile_headPose:
			service3 = (MicrosoftFaceAPI) getServiceByIndex(ServiceIndex.MicrosoftFaceAPI);
			service3.setParams(false, true, false, false, true, true);
			return service3;
		case MicrosoftFaceAPI_landmarks_age_smile_headPose:
			service3 = (MicrosoftFaceAPI) getServiceByIndex(ServiceIndex.MicrosoftFaceAPI);
			service3.setParams(true, true, false, false, true, true);
			return service3;
		case MicrosoftFaceAPI_gender_smile_headPose:
			service3 = (MicrosoftFaceAPI) getServiceByIndex(ServiceIndex.MicrosoftFaceAPI);
			service3.setParams(false, false, true, false, true, true);
			return service3;
		case MicrosoftFaceAPI_landmarks_gender_smile_headPose:
			service3 = (MicrosoftFaceAPI) getServiceByIndex(ServiceIndex.MicrosoftFaceAPI);
			service3.setParams(true, false, true, false, true, true);
			return service3;
		case MicrosoftFaceAPI_age_gender_smile_headPose:
			service3 = (MicrosoftFaceAPI) getServiceByIndex(ServiceIndex.MicrosoftFaceAPI);
			service3.setParams(false, true, true, false, true, true);
			return service3;
		case MicrosoftFaceAPI_landmarks_age_gender_smile_headPose:
			service3 = (MicrosoftFaceAPI) getServiceByIndex(ServiceIndex.MicrosoftFaceAPI);
			service3.setParams(true, true, true, false, true, true);
			return service3;
		case MicrosoftFaceAPI_facialHair_smile_headPose:
			service3 = (MicrosoftFaceAPI) getServiceByIndex(ServiceIndex.MicrosoftFaceAPI);
			service3.setParams(false, false, false, true, true, true);
			return service3;
		case MicrosoftFaceAPI_landmarks_facialHair_smile_headPose:
			service3 = (MicrosoftFaceAPI) getServiceByIndex(ServiceIndex.MicrosoftFaceAPI);
			service3.setParams(true, false, false, true, true, true);
			return service3;
		case MicrosoftFaceAPI_age_facialHair_smile_headPose:
			service3 = (MicrosoftFaceAPI) getServiceByIndex(ServiceIndex.MicrosoftFaceAPI);
			service3.setParams(false, true, false, true, true, true);
			return service3;
		case MicrosoftFaceAPI_landmarks_age_facialHair_smile_headPose:
			service3 = (MicrosoftFaceAPI) getServiceByIndex(ServiceIndex.MicrosoftFaceAPI);
			service3.setParams(true, true, false, true, true, true);
			return service3;
		case MicrosoftFaceAPI_gender_facialHair_smile_headPose:
			service3 = (MicrosoftFaceAPI) getServiceByIndex(ServiceIndex.MicrosoftFaceAPI);
			service3.setParams(false, false, true, true, true, true);
			return service3;
		case MicrosoftFaceAPI_landmarks_gender_facialHair_smile_headPose:
			service3 = (MicrosoftFaceAPI) getServiceByIndex(ServiceIndex.MicrosoftFaceAPI);
			service3.setParams(true, false, true, true, true, true);
			return service3;
		case MicrosoftFaceAPI_age_gender_facialHair_smile_headPose:
			service3 = (MicrosoftFaceAPI) getServiceByIndex(ServiceIndex.MicrosoftFaceAPI);
			service3.setParams(false, true, true, true, true, true);
			return service3;
		case MicrosoftFaceAPI_landmarks_age_gender_facialHair_smile_headPose:
			service3 = (MicrosoftFaceAPI) getServiceByIndex(ServiceIndex.MicrosoftFaceAPI);
			service3.setParams(true, true, true, true, true, true);
			return service3;
		}
		return null;
	}
}