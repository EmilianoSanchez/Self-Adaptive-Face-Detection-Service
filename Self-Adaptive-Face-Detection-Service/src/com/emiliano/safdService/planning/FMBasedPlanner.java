package com.emiliano.safdService.planning;

import java.util.Map;

import com.emiliano.fd.FaceDetectionException;
import com.emiliano.fd.services.FaceDetectionServiceClient;
import com.emiliano.fmframework.building.FMBuilder;
import com.emiliano.fmframework.core.Configuration;
import com.emiliano.fmframework.core.FeatureModel;
import com.emiliano.fmframework.core.FeatureState;
import com.emiliano.fmframework.core.constraints.AssignedValue;
import com.emiliano.fmframework.core.constraints.Clause;
import com.emiliano.fmframework.core.constraints.Constraint;
import com.emiliano.fmframework.operations.ConfOperations;
import com.emiliano.fmframework.operations.ModelOperations;
import com.emiliano.fmframework.optimization.ConfigurationSelectionInstance;
import com.emiliano.fmframework.optimization.objectiveFunctions.ObjectiveFunction;
import com.emiliano.safdService.ServiceFeatures;
import com.emiliano.safdService.ServiceFeatures.FunctionalFeature;
import com.emiliano.safdService.QualityPreference;
import com.emiliano.safdService.context.ContextState;
import com.emiliano.safdService.services.ServiceClient;

public class FMBasedPlanner implements Planner {

	@Override
	public FaceDetectionServiceClient selectBestFaceDetector(
			ServiceFeatures requiredServiceFeatures,
			QualityPreference serviceQualityPolicy,
			ContextState currentContextState, Map<String, ServiceClient> services)
			throws FaceDetectionException {

		// Build model
		FeatureModel model = buildModel(requiredServiceFeatures,
				serviceQualityPolicy, currentContextState, services);

		// Build objective function
		ObjectiveFunction objectiveFunction = serviceQualityPolicy
				.getObjectiveFunction();

		// Build partial configuration
		Configuration configuration = ConfOperations
				.getPartialConfiguration(model);

		if (configuration == null)
			throw new FaceDetectionException(
					"The system model has not valid configurations");

		// Log.i("SAFD","Partial conf: "+ configuration.toString());
		// Log.i("SAFD","ServiceFeatures: "+
		// requiredServiceFeatures.getConfiguration());

		if (!ConfOperations.assignFeatures(configuration,
				requiredServiceFeatures.getConfiguration()))
			throw new FaceDetectionException(
					"The system model has not valid configurations, because available providers do not fit user requirements");

		// Log.i("SAFD", configuration.toString());

		if (!ConfOperations.assignFeatures(configuration,
				currentContextState.getConfiguration()))
			throw new FaceDetectionException(
					"The system model has not valid configurations, because available providers do not fit context conditions");

		// Log.i("SAFD", configuration.toString());

		// Select full configuration
		Configuration bestConfiguration = ConfOperations.selectConfiguration(new ConfigurationSelectionInstance(configuration,
						objectiveFunction));

		// Log.i("SAFD", bestConfiguration.toString());

		/*
		 * Build operation:
		 */
		CompoundFaceDetection compoundFaceDetection = new CompoundFaceDetection();

		/*
		 * Alternativa 1: Usando el metodo ConfOperations.applyConfiguration()
		 */
		// ConfOperations.applyConfiguration(bestConfiguration,
		// compoundFaceDetection);

		/*
		 * Alternativa 2: Usando el metodo setConfiguration de
		 * la clase ServiceClient
		 */
		for (Map.Entry<String, ServiceClient> service : services.entrySet()) {
			if (bestConfiguration.getFeatureState(service.getKey()) == FeatureState.SELECTED) {
				ServiceClient client = service.getValue();
				client.setConfiguration(bestConfiguration);
				compoundFaceDetection.addFaceDetector(client);
			}
		}

		return compoundFaceDetection;
	}

	private FeatureModel buildModel(
			ServiceFeatures requiredServiceFeatures,
			QualityPreference serviceQualityPolicy,
			ContextState currentContextState,
			Map<String, ServiceClient> services) {

		FeatureModel model = new FMBuilder("SAFD")
				.buildModel();

		FeatureModel serviceFeaturesModel = requiredServiceFeatures
				.getFeatureModel();
		ModelOperations.insertMandatorySubModel(model, "SAFD",
				serviceFeaturesModel);

		FeatureModel contextStateModel = currentContextState.getFeatureModel();
		ModelOperations.insertMandatorySubModel(model, "SAFD",
				contextStateModel);

		for (ServiceClient service : services.values())
			ModelOperations.insertOptionalSubModel(model, "SAFD",
					service.getFeatureModel());

		setCrossTreeConstraints(model, services);

		return model;
	}

	private void setCrossTreeConstraints(FeatureModel model,
			Map<String, ServiceClient> services) {

		// root selected
		model.addCrossTreeConstraint(new AssignedValue("SAFD", true));

		// functionalities
		ServiceFeatures.FunctionalFeature[] functions = ServiceFeatures.FunctionalFeature
				.values();
		Clause[] functionsToVariants = new Clause[functions.length];
		for (int i = 0; i < functions.length; i++) {
			Clause clause = new Clause();
			clause.literals.put(functions[i].name(), false);
			functionsToVariants[i] = clause;
		}
		for (ServiceClient service : services.values()) {
			Map<String, FunctionalFeature[]> variantsToFunctions = service
					.getVariantsToFunctions();
			for (Map.Entry<String, FunctionalFeature[]> assignment : variantsToFunctions
					.entrySet()) {
				for (FunctionalFeature functionalFeature : assignment
						.getValue())
					functionsToVariants[functionalFeature.ordinal()].literals
							.put(assignment.getKey(), true);
			}
		}
		for (int i = 0; i < functions.length; i++) {
			model.addCrossTreeConstraint(functionsToVariants[i]);
		}

		// context restrictions
		for (ServiceClient service : services.values()) {
			if (service.getVariantsToContext() != null)
				for (Constraint restriction : service.getVariantsToContext())
					model.addCrossTreeConstraint(restriction);
		}
	}

}
