package com.emiliano.safdService.services.serviceClients;

import java.util.HashMap;
import java.util.Map;

import com.emiliano.fd.services.MicrosoftFaceAPI;
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

public class MicrosoftFaceAPIClient extends ServiceClient<MicrosoftFaceAPI> {

	public static final String SERVICE_NAME = "Project Oxford Face API";

	public static final String FUNCTION_NAME = "/v1.0/detect";
	
	private static MicrosoftFaceAPIClient client;

	public static MicrosoftFaceAPIClient getInstance(Context context) {
		if (client == null)
			client = new MicrosoftFaceAPIClient(context);
		return client;
	}

	public MicrosoftFaceAPIClient(Context context) {
		super(context);
	}


	@Override
	protected Configuration getInitialConfiguration() {
		Configuration conf = new Configuration(this.model);
		conf.setFeatureState("age", this.fdServiceClient.isAgeParam());
		conf.setFeatureState("gender", this.fdServiceClient.isGenderParam());
		conf.setFeatureState("head pose", this.fdServiceClient.isHeadPoseParam());
		conf.setFeatureState("face landmarks", this.fdServiceClient.isLandmarksParam());
		conf.setFeatureState("facial hair", this.fdServiceClient.isFacialHairParam());
		conf.setFeatureState("smile", this.fdServiceClient.isSmileParam());
		return conf;
	}

	@Override
	protected FeatureModel buildFeatureModel() {
		FeatureModel model = new FMBuilder(SERVICE_NAME).addAttribute(QualityProperty.RTIME.name(), 704.51)
				.addAttribute(QualityProperty.ACC_POS.name(), 0.923)
				.addAttribute(QualityProperty.ERROR_LAND.name(), 0.227)
				.addAttribute(QualityProperty.ACC_SMILE.name(), 0.518)
				.addAttribute(QualityProperty.ACC_GENDER.name(), 0.799)
				.addAttribute(QualityProperty.ERROR_AGE.name(), 0.419)
				
				.addMandatoryFeature(new FMBuilder(FUNCTION_NAME)
				.addOptionalFeature(new FMBuilder("age").addAttribute(QualityProperty.RTIME.name(),31.17 ) )
				.addOptionalFeature(new FMBuilder("gender"))
				.addOptionalFeature(new FMBuilder("head pose"))
				.addOptionalFeature(new FMBuilder("face landmarks"))
				.addOptionalFeature(new FMBuilder("facial hair").addAttribute(QualityProperty.RTIME.name(),26.46))
				.addOptionalFeature(new FMBuilder("smile").addAttribute(QualityProperty.RTIME.name(), 55.88))).buildModel();
		return model;
	}

	@Override
	protected Constraint[] buildVariantsToContext() {
		return new Constraint[] { new Exclude(ContextState.ConnectionState.NO_CONNECTION.name(), SERVICE_NAME) };
	}

	@Override
	protected Map<String, FunctionalFeature[]> buildVariantsToFunctions() {
		Map<String, ServiceFeatures.FunctionalFeature[]> variantToFunction = new HashMap<String, ServiceFeatures.FunctionalFeature[]>();
		variantToFunction.put(SERVICE_NAME,
				new ServiceFeatures.FunctionalFeature[] { ServiceFeatures.FunctionalFeature.FACEPOSITION_DETECTION,
						 });
		variantToFunction.put("gender",
				new ServiceFeatures.FunctionalFeature[] { ServiceFeatures.FunctionalFeature.GENDER_CLASSIFICATION });
		variantToFunction.put("facial hair",
				new ServiceFeatures.FunctionalFeature[] { ServiceFeatures.FunctionalFeature.FACIALHAIR_CLASSIFICATION });
		variantToFunction.put("smile",
				new ServiceFeatures.FunctionalFeature[] { ServiceFeatures.FunctionalFeature.SMILE_CLASSIFICATION });
		variantToFunction.put("age",
				new ServiceFeatures.FunctionalFeature[] { ServiceFeatures.FunctionalFeature.AGE_CLASSIFICATION });
		variantToFunction.put("face landmarks",
				new ServiceFeatures.FunctionalFeature[] { ServiceFeatures.FunctionalFeature.LANDMARK_DETECTION });
		variantToFunction.put("head pose",
				new ServiceFeatures.FunctionalFeature[] { ServiceFeatures.FunctionalFeature.FACEORIENTATION_DETECTION });
		return variantToFunction;
	}

	@Override
	protected MicrosoftFaceAPI buildFdServiceClient() {
		return new MicrosoftFaceAPI(this.context.getResources().getString(com.emiliano.safdService.R.string.microsoft_faceapi_key));
	}

	@Override
	protected boolean applyConfiguration(Configuration conf) {
		boolean age = conf.isFeatureSelected("age");
		this.fdServiceClient.setAgeParam(age);
		boolean gender = conf.isFeatureSelected("gender");
		this.fdServiceClient.setGenderParam(gender);
		boolean headpose = conf.isFeatureSelected("head pose");
		this.fdServiceClient.setHeadPoseParam(headpose);
		boolean facelandmarks = conf.isFeatureSelected("face landmarks");
		this.fdServiceClient.setLandmarksParam(facelandmarks);
		boolean facialhair = conf.isFeatureSelected("facial hair");
		this.fdServiceClient.setFacialHairParam(facialhair);
		boolean smile = conf.isFeatureSelected("smile");
		this.fdServiceClient.setSmileParam(smile);
		
		return true;
	}

}