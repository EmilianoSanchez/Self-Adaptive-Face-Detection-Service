package com.emiliano.safdService;

import java.util.Set;
import java.util.TreeSet;

import com.emiliano.fmframework.building.FMBuilder;
import com.emiliano.fmframework.core.Configuration;
import com.emiliano.fmframework.core.FeatureModel;
import com.emiliano.fmframework.operations.ConfOperations;
import com.emiliano.safdService.planning.ModelableElement;

/**
 * Descriptor of the available features, i.e. the service interface, of a
 * FaceDetector object.
 *
 * @author Emiliano Sanchez
 */

public class ServiceFeatures implements ModelableElement {

	public enum FunctionalFeature {
		REAL_TIME_DETECTION, FACEPOSITION_DETECTION, FACEORIENTATION_DETECTION, LANDMARK_DETECTION, GENDER_CLASSIFICATION, AGE_CLASSIFICATION, FACIALHAIR_CLASSIFICATION, OPENCLOSEEYES_CLASSIFICATION, SMILE_CLASSIFICATION, MOOD_CLASSIFICATION, GLASSES_CLASSIFICATION
	}

	private boolean[] features;

	public ServiceFeatures() {
		this.features = new boolean[FunctionalFeature.values().length];
		this.features[FunctionalFeature.FACEPOSITION_DETECTION.ordinal()] = true;
	}

	public ServiceFeatures(FunctionalFeature... features) {
		this();
		for (FunctionalFeature feature : features) {
			this.features[feature.ordinal()] = true;
		}
	};

	public void addFeature(FunctionalFeature feature) {
		this.features[feature.ordinal()] = true;
	}

	public void removeFeature(FunctionalFeature feature) {
		this.features[feature.ordinal()] = false;
	}

	public boolean hasFeature(FunctionalFeature feature) {
		return this.features[feature.ordinal()];
	}

	public Set<FunctionalFeature> getSelectedFeatures() {
		Set<FunctionalFeature> features = new TreeSet<FunctionalFeature>();
		for (int i = 0; i < this.features.length; i++) {
			if (this.features[i] == true)
				features.add(FunctionalFeature.values()[i]);
		}
		return features;
	}

	public Set<FunctionalFeature> getDeselectedFeatures() {
		Set<FunctionalFeature> features = new TreeSet<FunctionalFeature>();
		for (int i = 0; i < this.features.length; i++) {
			if (this.features[i] == false)
				features.add(FunctionalFeature.values()[i]);
		}
		return features;
	}

	private static FeatureModel model;

	public FeatureModel getFeatureModel() {
		if (model == null) {
			model = new FMBuilder(FunctionalFeature.class.getSimpleName())
					.addOptionalFeature(new FMBuilder(FunctionalFeature.FACEPOSITION_DETECTION.name()))
					.addOptionalFeature(new FMBuilder(FunctionalFeature.FACEORIENTATION_DETECTION.name()))
					.addOptionalFeature(new FMBuilder(FunctionalFeature.LANDMARK_DETECTION.name()))
					.addOptionalFeature(new FMBuilder(FunctionalFeature.GENDER_CLASSIFICATION.name()))
					.addOptionalFeature(new FMBuilder(FunctionalFeature.AGE_CLASSIFICATION.name()))
					.addOptionalFeature(new FMBuilder(FunctionalFeature.FACIALHAIR_CLASSIFICATION.name()))
					.addOptionalFeature(new FMBuilder(FunctionalFeature.OPENCLOSEEYES_CLASSIFICATION.name()))
					.addOptionalFeature(new FMBuilder(FunctionalFeature.SMILE_CLASSIFICATION.name()))
					.addOptionalFeature(new FMBuilder(FunctionalFeature.MOOD_CLASSIFICATION.name())).buildModel();
		}
		return model;
	}

	public Configuration getConfiguration() {
		Configuration conf = ConfOperations.getPartialConfiguration(getFeatureModel());
		for (FunctionalFeature feature : getSelectedFeatures())
			ConfOperations.selectFeature(conf, feature.name());
		return conf;
	}
}
