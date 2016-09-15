package com.emiliano.safdService.planning;

import com.emiliano.fmframework.core.Configuration;
import com.emiliano.fmframework.core.FeatureModel;

public interface ModelableElement {
	public FeatureModel getFeatureModel();
	public Configuration getConfiguration();
}
