package com.emiliano.safdService.services.serviceClients;

import java.util.HashMap;
import java.util.Map;

import com.emiliano.fd.services.ViolaJonesOpenCVFaceDetector;
import com.emiliano.fmframework.building.FMBuilder;
import com.emiliano.fmframework.core.Configuration;
import com.emiliano.fmframework.core.FeatureModel;
import com.emiliano.fmframework.core.constraints.Constraint;
import com.emiliano.fmframework.core.constraints.crossTreeConstraints.Imply;
import com.emiliano.safdService.ServiceFeatures;
import com.emiliano.safdService.QualityPreference.QualityProperty;
import com.emiliano.safdService.context.ContextState;
import com.emiliano.safdService.services.ServiceClient;

import android.content.Context;

public class ViolaJonesOpenCVFaceDetectorClient extends ServiceClient<ViolaJonesOpenCVFaceDetector> {

	public static final String SERVICE_NAME = "OpenCV Face Detector";
	public static final String FUNCTION_NAME = "detect()";
	
	public ViolaJonesOpenCVFaceDetectorClient(Context context) {
		super(context);
	}
	
	private static ViolaJonesOpenCVFaceDetectorClient client;
	
	public static ViolaJonesOpenCVFaceDetectorClient getInstance(Context context) {
		if (client == null)
			client = new ViolaJonesOpenCVFaceDetectorClient(context);
		return client;
	}

	@Override
	protected Configuration getInitialConfiguration() {
		Configuration conf = new Configuration(this.model);
		return conf;
	}

	@Override
	protected FeatureModel buildFeatureModel() {
		FeatureModel model = new FMBuilder(
				SERVICE_NAME).addAttribute(QualityProperty.RTIME.name(), 89.4)
				.addAttribute(QualityProperty.ACC_POS.name(), 0.760).addMandatoryFeature(
				new FMBuilder(FUNCTION_NAME)).buildModel();
		
		 
		return model;
	}

	@Override
	protected Constraint[] buildVariantsToContext() {
		return new Constraint[] { 
				new Imply(SERVICE_NAME,ContextState.CPUArchitecture.armeabi.name())};
				
//				new Exclude(ContextState.CPUArchitecture.armeabi.name(), SERVICE_NAME),
//				new Exclude(ContextState.CPUArchitecture.arm64_v8a.name(), SERVICE_NAME),
//				new Exclude(ContextState.CPUArchitecture.mips.name(), SERVICE_NAME),
//				new Exclude(ContextState.CPUArchitecture.mips64.name(), SERVICE_NAME),
//				new Exclude(ContextState.CPUArchitecture.x86.name(), SERVICE_NAME),
//				new Exclude(ContextState.CPUArchitecture.x86_64.name(), SERVICE_NAME)
	}

	@Override
	protected Map buildVariantsToFunctions() {
		Map<String, ServiceFeatures.FunctionalFeature[]> variantToFunction = new HashMap<String, ServiceFeatures.FunctionalFeature[]>();
		variantToFunction.put(SERVICE_NAME, new ServiceFeatures.FunctionalFeature[] {
				ServiceFeatures.FunctionalFeature.FACEPOSITION_DETECTION });
		return variantToFunction;
	}

	@Override
	protected ViolaJonesOpenCVFaceDetector buildFdServiceClient() {
		return new ViolaJonesOpenCVFaceDetector(this.context);
	}

	@Override
	protected boolean applyConfiguration(Configuration conf) {
		return true;
	}

}
