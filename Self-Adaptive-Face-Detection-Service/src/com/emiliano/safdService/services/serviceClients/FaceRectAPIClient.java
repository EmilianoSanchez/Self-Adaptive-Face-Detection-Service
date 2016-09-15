package com.emiliano.safdService.services.serviceClients;

import java.util.HashMap;
import java.util.Map;

import com.emiliano.fd.services.FaceRectAPI;
import com.emiliano.fmframework.building.FMBuilder;
import com.emiliano.fmframework.core.Configuration;
import com.emiliano.fmframework.core.FeatureModel;
import com.emiliano.fmframework.core.constraints.Constraint;
import com.emiliano.fmframework.core.constraints.crossTreeConstraints.Exclude;
import com.emiliano.safdService.ServiceFeatures;
import com.emiliano.safdService.QualityPreference.QualityProperty;
import com.emiliano.safdService.ServiceFeatures.FunctionalFeature;
import com.emiliano.safdService.context.ContextState;
import com.emiliano.safdService.services.ServiceClient;

import android.content.Context;

public class FaceRectAPIClient extends ServiceClient<FaceRectAPI> {

	public static final String SERVICE_NAME = "Face Rect API";
	public static final String FUNCTION_NAME = "/process-file";
	public static final String FEATURES_PARAMETER_NAME = "features";

	public FaceRectAPIClient(Context context) {
		super(context);
	}

	private static FaceRectAPIClient client;

	public static FaceRectAPIClient getInstance(Context context) {
		if (client == null)
			client = new FaceRectAPIClient(context);
		return client;
	}

	@Override
	protected FeatureModel buildFeatureModel() {
		return new FMBuilder(SERVICE_NAME).addAttribute(QualityProperty.RTIME.name(), 907.1)
				.addAttribute(QualityProperty.ACC_POS.name(), 0.844)
				.addAttribute(QualityProperty.ERROR_LAND.name(), 0.623)
				.addMandatoryFeature(new FMBuilder(FUNCTION_NAME)
				.addOptionalFeature(new FMBuilder(FEATURES_PARAMETER_NAME).addAttribute(QualityProperty.RTIME.name(),492.23)))
				.buildModel();
		
		
		
	}


	@Override
	protected Constraint[] buildVariantsToContext() {
		return new Constraint[] { new Exclude(ContextState.ConnectionState.NO_CONNECTION.name(), SERVICE_NAME) };
	}

	@Override
	protected Map<String, FunctionalFeature[]> buildVariantsToFunctions() {
		Map<String, ServiceFeatures.FunctionalFeature[]> variantToFunction = new HashMap<String, ServiceFeatures.FunctionalFeature[]>();
		variantToFunction
				.put(SERVICE_NAME,
						new ServiceFeatures.FunctionalFeature[] {
								ServiceFeatures.FunctionalFeature.FACEPOSITION_DETECTION,
								ServiceFeatures.FunctionalFeature.FACEORIENTATION_DETECTION });
		variantToFunction.put(FEATURES_PARAMETER_NAME,
				new ServiceFeatures.FunctionalFeature[] { ServiceFeatures.FunctionalFeature.LANDMARK_DETECTION });
		return variantToFunction;
	}

	@Override
	protected FaceRectAPI buildFdServiceClient() {
		return new FaceRectAPI(this.context.getResources().getString(com.emiliano.safdService.R.string.x_mashape_key));
	}

	@Override
	protected Configuration getInitialConfiguration() {
		Configuration conf = new Configuration(this.model);
		conf.setFeatureState(FEATURES_PARAMETER_NAME, this.fdServiceClient.isFeaturesParam());
		return conf;
	}

	@Override
	protected boolean applyConfiguration(Configuration conf) {
		this.fdServiceClient.setFeaturesParam(conf.isFeatureSelected(FEATURES_PARAMETER_NAME));
		return true;
	}

}
