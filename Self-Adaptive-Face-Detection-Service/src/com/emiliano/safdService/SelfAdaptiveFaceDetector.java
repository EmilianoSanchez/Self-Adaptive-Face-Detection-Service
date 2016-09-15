package com.emiliano.safdService;

import java.io.File;
import java.util.Map;

import com.emiliano.fd.FaceDetectionException;
import com.emiliano.fd.FaceDetectionResult;
import com.emiliano.fd.services.FaceDetectionServiceClient;
import com.emiliano.safdService.context.ContextManager;
import com.emiliano.safdService.context.ContextState;
import com.emiliano.safdService.context.OnContextChangeListener;
import com.emiliano.safdService.planning.FMBasedPlanner;
import com.emiliano.safdService.planning.Planner;
import com.emiliano.safdService.services.OnServiceChangeListener;
import com.emiliano.safdService.services.ServiceClient;
import com.emiliano.safdService.services.ServicePortfolio;

import android.content.Context;

// TODO: Auto-generated Javadoc
/**
 * The Class ContextAwareFaceDetector.
 */
public class SelfAdaptiveFaceDetector
		implements FaceDetectionServiceClient, OnContextChangeListener, OnServiceChangeListener {

	/** The context manager. */
	private ContextManager contextManager;

	/** The current context state. */
	private ContextState currentContextState;

	/** The provider manager. */
	private ServicePortfolio servicePortfolio;

	/** The face detector providers. */
	private Map<String, ServiceClient> services;

	/** The required service features. */
	private ServiceFeatures requiredServiceFeatures;

	/** The service quality policy. */
	private QualityPreference serviceQualityPolicy;

	/** The planner. */
	private Planner planner;

	/** The current face detector. */
	// private FaceDetection currentFaceDetector;

	/**
	 * Instantiates a new context aware face detector.
	 *
	 * @param androidContext
	 *            the android context
	 * @param requiredServiceFeatures
	 *            the required service features
	 * @param serviceQualityPolicy
	 *            the service quality policy
	 */
	public SelfAdaptiveFaceDetector(Context androidContext, ServiceFeatures requiredServiceFeatures,
			QualityPreference serviceQualityPolicy) {

		this.contextManager = new ContextManager(androidContext);
		this.contextManager.addOnContextChangeListener(this);
		this.currentContextState = this.contextManager.getContextState();

		this.servicePortfolio = new ServicePortfolio(androidContext);
		this.services = this.servicePortfolio.getServices();
		if (this.services == null || this.services.size() == 0)
			throw new RuntimeException("No Face Detector providers are supported");

		this.requiredServiceFeatures = requiredServiceFeatures;
		this.serviceQualityPolicy = serviceQualityPolicy;

		// this.planner=new FMBasedPlanner();
		this.planner = new FMBasedPlanner();

		// this.currentFaceDetector=selectBestFaceDetector();
	}

	/**
	 * Instantiates a new context aware face detector.
	 *
	 * @param androidContext
	 *            the android context
	 */
	public SelfAdaptiveFaceDetector(Context androidContext) {
		this(androidContext, new ServiceFeatures(), new QualityPreference());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.emiliano.cafdLibrary.FaceDetector#detect(android.net.Uri)
	 */
	@Override
	public FaceDetectionResult execute(File image) throws FaceDetectionException {
		FaceDetectionServiceClient operation = selectBestFaceDetector();
		return operation.execute(image);
	};

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.emiliano.cafdLibrary.context.OnContextChangeListener#
	 * onConnectivityStateChange(com.emiliano.cafdLibrary.context.ContextState.
	 * ConnectivityState,
	 * com.emiliano.cafdLibrary.context.ContextState.ConnectivityState)
	 */
	@Override
	public void onContextStateChange(ContextState newContextState) {
		this.currentContextState = newContextState;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.emiliano.cafdLibrary.providers.OnProviderChangeListener#
	 * onProviderDescriptorChange(int,
	 * com.emiliano.cafdLibrary.providers.FaceDetectorDescriptor)
	 */
	@Override
	public void onServiceChange(ServiceClient service) {
		this.services.put(service.getName(), service);
	}

	/**
	 * Gets the required service features.
	 *
	 * @return the required service features
	 */
	public ServiceFeatures getRequiredServiceFeatures() {
		return requiredServiceFeatures;
	}

	/**
	 * Sets the required service features.
	 *
	 * @param requiredServiceFeatures
	 *            the new required service features
	 */
	public void setRequiredServiceFeatures(ServiceFeatures requiredServiceFeatures) {
		this.requiredServiceFeatures = requiredServiceFeatures;
		// this.currentFaceDetector=selectBestFaceDetector();
	}

	/**
	 * Gets the service quality policy.
	 *
	 * @return the service quality policy
	 */
	public QualityPreference getServiceQualityPolicy() {
		return serviceQualityPolicy;
	}

	/**
	 * Sets the service quality policy.
	 *
	 * @param serviceQualityPolicy
	 *            the new service quality policy
	 */
	public void setServiceQualityPolicy(QualityPreference serviceQualityPolicy) {
		this.serviceQualityPolicy = serviceQualityPolicy;
		// this.currentFaceDetector=this.selectBestFaceDetector();
	}

	private FaceDetectionServiceClient selectBestFaceDetector() throws FaceDetectionException {
		return this.planner.selectBestFaceDetector(requiredServiceFeatures, serviceQualityPolicy, currentContextState,
				services);
	}

	@Override
	public String getName() {
		return "Self-Adaptive Face Detection Service";
	}
}
