package com.emiliano.fd.services;

import java.io.File;

import org.json.JSONException;
import org.json.JSONObject;

import com.emiliano.fd.FaceDetectionException;
import com.emiliano.fd.FaceDetectionResult;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import cz.msebera.android.httpclient.Header;

public abstract class FaceDetectionRestServiceClient extends JsonHttpResponseHandler implements FaceDetectionServiceClient{

	private FaceDetectionResult output;
	private FaceDetectionException exception;
	private SyncHttpClient httpClient;
	protected abstract void configureHttpClient(SyncHttpClient httpClient);
	protected abstract String getUrl();
	protected abstract Method getMethod();
	protected abstract void setParams(RequestParams params,File image) throws FaceDetectionException;
	
	protected static enum Method{
		GET,POST,DELETE,PUT
	};
	
	public FaceDetectionRestServiceClient() {
		httpClient= new SyncHttpClient();
		configureHttpClient(httpClient);
	}
	
	public SyncHttpClient getHttpClient() {
		return httpClient;
	}
	
	@Override
	public FaceDetectionResult execute(File image) throws FaceDetectionException{
		this.output=null;
		this.exception=null;
		RequestParams params = new RequestParams();
		setParams(params,image);
		switch(getMethod()){
		case GET:
			httpClient.get(getUrl(), params,this);
			break;
		case POST:
			httpClient.post(getUrl(), params,this);
			break;
		case DELETE:
			httpClient.delete(getUrl(), params,this);
			break;
		case PUT:
			httpClient.put(getUrl(), params,this);
			break;			
		}
		if(exception!=null)
			throw exception;
		else
			return output;
	}

	@Override
	public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
		try {
			this.output=parseResponse(statusCode, headers, response);
		} catch (JSONException e) {
			this.exception=new FaceDetectionException(e);
		}
	}
	
	public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
		this.exception=parseFailure(statusCode, headers, errorResponse,throwable);
    }
	
	protected FaceDetectionException parseFailure(int statusCode, Header[] headers, JSONObject errorResponse,
			Throwable throwable) {
		return new FaceDetectionException(errorResponse.toString());
	}
	
	public abstract FaceDetectionResult parseResponse(int statusCode, Header[] headers, JSONObject response) throws JSONException;
}
