package com.emiliano.safdService.services.serviceClients;

import java.util.HashMap;
import java.util.Map;

import com.emiliano.fd.services.GMSFaceDetector;
import com.emiliano.fmframework.building.FMBuilder;
import com.emiliano.fmframework.core.Configuration;
import com.emiliano.fmframework.core.FeatureModel;
import com.emiliano.fmframework.core.constraints.Constraint;
import com.emiliano.fmframework.core.constraints.crossTreeConstraints.Exclude;
import com.emiliano.safdService.ServiceFeatures;
import com.emiliano.safdService.QualityPreference.QualityProperty;
import com.emiliano.safdService.context.ContextState;
import com.emiliano.safdService.services.ServiceClient;

import android.content.Context;

public class GMSFaceDetectorClient extends ServiceClient<GMSFaceDetector> {

	public static final String SERVICE_NAME = "GMS Face Detector";
	public static final String FUNCTION_NAME = "detect()";
	
	public GMSFaceDetectorClient(Context context) {
		super(context);
	}
	
	private static GMSFaceDetectorClient client;
	
	public static GMSFaceDetectorClient getInstance(Context context) {
		if (client == null)
			client = new GMSFaceDetectorClient(context);
		return client;
	}


	@Override
	protected Configuration getInitialConfiguration() {
		Configuration conf = new Configuration(this.model);
		conf.setFeatureState("ALL_CLASSIFICATIONS", this.fdServiceClient.isAllClassificationsParam());
		conf.setFeatureState("ALL_LANDMARKS", this.fdServiceClient.isAllLandmarksParam());
		conf.setFeatureState("ACCURATE_MODE", this.fdServiceClient.isAccurateModeParam());
		return conf;
	}

	@Override
	protected FeatureModel buildFeatureModel() {
		FeatureModel model = new FMBuilder(
				SERVICE_NAME).addAttribute(QualityProperty.RTIME.name(), 171.97)
				.addAttribute(QualityProperty.ACC_POS.name(), 0.898)
				.addAttribute(QualityProperty.ERROR_LAND.name(), 0.435)
				.addAttribute(QualityProperty.ACC_SMILE.name(), 0.748)
				
				.addMandatoryFeature(
				new FMBuilder(FUNCTION_NAME)
						.addOptionalFeature(
								new FMBuilder("ALL_LANDMARKS").addAttribute(QualityProperty.RTIME.name(),93.12))
						.addOptionalFeature(
								new FMBuilder(
										"ALL_CLASSIFICATIONS").addAttribute(QualityProperty.RTIME.name(),34.72))
						.addOptionalFeature(
								new FMBuilder("ACCURATE_MODE").addAttribute(QualityProperty.RTIME.name(), 111.51)
								.addAttribute(QualityProperty.ACC_POS.name(), 0.934)
								.addAttribute(QualityProperty.ERROR_LAND.name(), 0.351)
								.addAttribute(QualityProperty.ACC_SMILE.name(), 0.751))).buildModel();
		return model;
	}

	@Override
	protected Constraint[] buildVariantsToContext() {
		return new Constraint[] { new Exclude(ContextState.AndroidSOVersion.APIv8.name(), SERVICE_NAME) };
	}

	@Override
	protected Map buildVariantsToFunctions() {
		Map<String, ServiceFeatures.FunctionalFeature[]> variantToFunction = new HashMap<String, ServiceFeatures.FunctionalFeature[]>();
		variantToFunction.put(SERVICE_NAME, new ServiceFeatures.FunctionalFeature[] {
				ServiceFeatures.FunctionalFeature.FACEPOSITION_DETECTION,
				ServiceFeatures.FunctionalFeature.FACEORIENTATION_DETECTION });
		variantToFunction
				.put("ALL_LANDMARKS",
						new ServiceFeatures.FunctionalFeature[] { ServiceFeatures.FunctionalFeature.LANDMARK_DETECTION });
		variantToFunction
				.put("ALL_CLASSIFICATIONS",
						new ServiceFeatures.FunctionalFeature[] {
								ServiceFeatures.FunctionalFeature.GENDER_CLASSIFICATION,
								ServiceFeatures.FunctionalFeature.SMILE_CLASSIFICATION,
								ServiceFeatures.FunctionalFeature.OPENCLOSEEYES_CLASSIFICATION });
		return variantToFunction;
	}

	@Override
	protected GMSFaceDetector buildFdServiceClient() {
		return new GMSFaceDetector(this.context);
	}

	@Override
	protected boolean applyConfiguration(Configuration conf) {
		boolean ALL_LANDMARKS = conf.isFeatureSelected("ALL_LANDMARKS");
		boolean ALL_CLASSIFICATIONS = conf.isFeatureSelected("ALL_CLASSIFICATIONS");
		boolean ACCURATE_MODE = conf.isFeatureSelected("ACCURATE_MODE");
		this.fdServiceClient.setParams(ALL_LANDMARKS, ALL_CLASSIFICATIONS, ACCURATE_MODE);
		return true;
	}


}