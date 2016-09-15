package com.emiliano.safdService.services.serviceClients;

import java.util.HashMap;
import java.util.Map;

import com.emiliano.fd.services.SkyBiometryAPI;
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
import com.emiliano.safdService.QualityPreference;

import android.content.Context;

public class SkyBiometryAPIClient extends ServiceClient<SkyBiometryAPI> {

	public static final String SERVICE_NAME = "Sky Biometry Face API";
	public static final String FUNCTION_NAME = "/faces/detect";

	private static SkyBiometryAPIClient client;

	public static SkyBiometryAPIClient getInstance(Context context) {
		if (client == null)
			client = new SkyBiometryAPIClient(context);
		return client;
	}

	public SkyBiometryAPIClient(Context context) {
		super(context);
	}

	@Override
	protected Configuration getInitialConfiguration() {
		Configuration conf = new Configuration(this.model);
		conf.setFeatureState("AggresiveDetector", this.fdServiceClient.isAggresiveDetectorParam());
		conf.setFeatureState("Gender", this.fdServiceClient.isGenderParam());
		conf.setFeatureState("Glasses", this.fdServiceClient.isGlassesParam());
		conf.setFeatureState("Smiling", this.fdServiceClient.isSmilingParam());
		conf.setFeatureState("Age", this.fdServiceClient.isAgeParam());
		conf.setFeatureState("Mood", this.fdServiceClient.isMoodParam());
		conf.setFeatureState("OpenEyes", this.fdServiceClient.isOpenEyesParam());
		return conf;
	}

	@Override
	protected FeatureModel buildFeatureModel() {
		FeatureModel model = new FMBuilder(SERVICE_NAME).addAttribute(QualityProperty.RTIME.name(), 997.66)
				.addAttribute(QualityProperty.ACC_POS.name(), 0.919)
				.addAttribute(QualityProperty.ERROR_LAND.name(), 0.203)
				.addAttribute(QualityProperty.ACC_SMILE.name(), 0.651)
				.addAttribute(QualityProperty.ACC_GENDER.name(), 0.809)
				.addAttribute(QualityProperty.ERROR_AGE.name(), 0.361)
				.addMandatoryFeature(new FMBuilder(FUNCTION_NAME).addOptionalFeature(new FMBuilder("OpenEyes"))
						.addOptionalFeature(new FMBuilder("Mood"))
						.addOptionalFeature(new FMBuilder("Age").addAttribute(QualityProperty.RTIME.name(), 53.62))
						.addOptionalFeature(new FMBuilder("Gender").addAttribute(QualityProperty.RTIME.name(), 52.67))
						.addOptionalFeature(new FMBuilder("Glasses"))
						.addOptionalFeature(new FMBuilder("Smiling").addAttribute(QualityProperty.RTIME.name(), 69.86))
						.addOptionalFeature(
								new FMBuilder("AggresiveDetector").addAttribute(QualityProperty.ACC_POS.name(), 0.923)
										.addAttribute(QualityProperty.ERROR_LAND.name(), 0.204)
										.addAttribute(QualityProperty.ACC_SMILE.name(), 0.723)
										.addAttribute(QualityProperty.ACC_GENDER.name(), 0.824)
										.addAttribute(QualityProperty.ERROR_AGE.name(), 0.358)))

				.buildModel();
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
						ServiceFeatures.FunctionalFeature.FACEORIENTATION_DETECTION,
						ServiceFeatures.FunctionalFeature.LANDMARK_DETECTION });
		variantToFunction.put("Gender",
				new ServiceFeatures.FunctionalFeature[] { ServiceFeatures.FunctionalFeature.GENDER_CLASSIFICATION });
		variantToFunction.put("Glasses",
				new ServiceFeatures.FunctionalFeature[] { ServiceFeatures.FunctionalFeature.GLASSES_CLASSIFICATION });
		variantToFunction.put("Smiling",
				new ServiceFeatures.FunctionalFeature[] { ServiceFeatures.FunctionalFeature.SMILE_CLASSIFICATION });
		variantToFunction.put("Age",
				new ServiceFeatures.FunctionalFeature[] { ServiceFeatures.FunctionalFeature.AGE_CLASSIFICATION });
		variantToFunction.put("Mood",
				new ServiceFeatures.FunctionalFeature[] { ServiceFeatures.FunctionalFeature.MOOD_CLASSIFICATION });
		variantToFunction.put("OpenEyes", new ServiceFeatures.FunctionalFeature[] {
				ServiceFeatures.FunctionalFeature.OPENCLOSEEYES_CLASSIFICATION });
		return variantToFunction;
	}

	@Override
	protected SkyBiometryAPI buildFdServiceClient() {
		return new SkyBiometryAPI(
				this.context.getResources().getString(com.emiliano.safdService.R.string.x_mashape_key),
				this.context.getResources().getString(com.emiliano.safdService.R.string.sky_biometry_api_key),
				this.context.getResources().getString(com.emiliano.safdService.R.string.sky_biometry_api_secret));
	}

	@Override
	protected boolean applyConfiguration(Configuration conf) {
		boolean Age = conf.isFeatureSelected("Age");
		this.fdServiceClient.setAgeParam(Age);
		boolean Mood = conf.isFeatureSelected("Mood");
		this.fdServiceClient.setMoodParam(Mood);
		boolean OpenEyes = conf.isFeatureSelected("OpenEyes");
		this.fdServiceClient.setOpenEyesParam(OpenEyes);
		boolean Gender = conf.isFeatureSelected("Gender");
		this.fdServiceClient.setGenderParam(Gender);
		boolean Glasses = conf.isFeatureSelected("Glasses");
		this.fdServiceClient.setGlassesParam(Glasses);
		boolean Smiling = conf.isFeatureSelected("Smiling");
		this.fdServiceClient.setSmilingParam(Smiling);
		boolean AggresiveDetector = conf.isFeatureSelected("AggresiveDetector");
		this.fdServiceClient.setAggresiveDetectorParam(AggresiveDetector);

		return true;
	}

}
