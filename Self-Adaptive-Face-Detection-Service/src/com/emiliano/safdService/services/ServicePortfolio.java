package com.emiliano.safdService.services;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.emiliano.safdService.services.serviceClients.FaceRectAPIClient;
import com.emiliano.safdService.services.serviceClients.GMSFaceDetectorClient;
import com.emiliano.safdService.services.serviceClients.SkyBiometryAPIClient;

import android.content.Context;

public class ServicePortfolio {

	private Context androidContext;
	private List<OnServiceChangeListener> listeners;
	private Map<String, ServiceClient> services;

	public ServicePortfolio(Context androidContext) {
		this.androidContext = androidContext;
		this.listeners = new LinkedList<OnServiceChangeListener>();
		this.services = new HashMap<String, ServiceClient>();

		ServiceClient client = FaceRectAPIClient.getInstance(androidContext);
		this.services.put(FaceRectAPIClient.SERVICE_NAME, client);

		client = SkyBiometryAPIClient.getInstance(androidContext);
		this.services.put(SkyBiometryAPIClient.SERVICE_NAME, client);

		client = GMSFaceDetectorClient.getInstance(androidContext);
		this.services.put(GMSFaceDetectorClient.SERVICE_NAME, client);

	}

	public void setOnContextChangeListener(OnServiceChangeListener listener) {
		listeners.add(listener);
	}

	public ServiceClient getService(String name) {
		return this.services.get(name);
	}

	public Map<String, ServiceClient> getServices() {
		return services;
	}

	public int getNumberOfServices() {
		return services.size();
	}

}
