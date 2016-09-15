package com.emiliano.safdService.services;

import java.io.File;
import java.util.Map;

import com.emiliano.fd.FaceDetectionException;
import com.emiliano.fd.FaceDetectionResult;
import com.emiliano.fd.services.FaceDetectionServiceClient;
import com.emiliano.fmframework.core.Configuration;
import com.emiliano.fmframework.core.FeatureModel;
import com.emiliano.fmframework.core.FeatureState;
import com.emiliano.fmframework.core.constraints.Constraint;
import com.emiliano.safdService.ServiceFeatures;
import com.emiliano.safdService.ServiceFeatures.FunctionalFeature;
import com.emiliano.safdService.planning.ModelableElement;

import android.content.Context;

public abstract class ServiceClient<FDServiceClient extends FaceDetectionServiceClient> implements FaceDetectionServiceClient, ModelableElement{

	protected FDServiceClient fdServiceClient;
	protected Map<String,ServiceFeatures.FunctionalFeature[]> variantsToFunctions;
	protected Constraint[] variantsToContext;
	protected FeatureModel model;
	protected Configuration conf;
	protected Context context;
	
	public ServiceClient(Context context){
		this.context=context;
		fdServiceClient=buildFdServiceClient();
		variantsToFunctions=buildVariantsToFunctions();
		variantsToContext=buildVariantsToContext();
		model=buildFeatureModel();
		conf=getInitialConfiguration();
	}
	
	protected abstract  Configuration getInitialConfiguration();

	protected abstract  FeatureModel buildFeatureModel();

	protected abstract  Constraint[] buildVariantsToContext();

	protected abstract  Map<String, FunctionalFeature[]> buildVariantsToFunctions();

	protected abstract FDServiceClient buildFdServiceClient();
	
	protected abstract boolean applyConfiguration(Configuration conf);

	public boolean setConfiguration(Configuration conf){
		if(applyConfiguration(conf)){
			this.conf=conf;
			return true;
		}else
			return false;
	};

	@Override
	public Configuration getConfiguration() {
		return conf;
	}
	
	@Override
	public String getName(){
		return fdServiceClient.getName();
	}

	public Map<String,ServiceFeatures.FunctionalFeature[]> getVariantsToFunctions() {
		return variantsToFunctions;
	}
	
	@Override
	public FeatureModel getFeatureModel(){
		return model;
	}

	public Constraint[] getVariantsToContext() {
		return variantsToContext;
	}

	@Override
	public FaceDetectionResult execute(File image) throws FaceDetectionException {
		return fdServiceClient.execute(image);
	}
}
