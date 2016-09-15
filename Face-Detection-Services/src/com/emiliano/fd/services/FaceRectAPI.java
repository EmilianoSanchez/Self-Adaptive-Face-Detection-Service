package com.emiliano.fd.services;

import java.io.File;
import java.io.FileNotFoundException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R;
import android.util.Log;

import com.emiliano.fd.Face;
import com.emiliano.fd.FaceDetectionResult;
import com.emiliano.fd.FaceDetectionResultImpl;
import com.emiliano.fd.FaceImpl;
import com.emiliano.fd.FaceLandmark;
import com.emiliano.fd.FaceOrientation;
import com.emiliano.fd.FacePosition;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import cz.msebera.android.httpclient.Header;

public class FaceRectAPI extends FaceDetectionRestServiceClient {

	private boolean features;
	private String x_mashape_key;

	public FaceRectAPI(String x_mashape_key) {
		this(x_mashape_key, false);
	}

	public FaceRectAPI(String x_mashape_key, boolean features) {
		this.x_mashape_key = x_mashape_key;
		this.setFeaturesParam(features);
	}

	public boolean isFeaturesParam() {
		return features;
	}

	public void setFeaturesParam(boolean features) {
		this.features = features;
	}

	public void setParams(boolean features) {
		setFeaturesParam(features);
	}

	@Override
	protected String getUrl() {
		return "https://apicloud-facerect.p.mashape.com/process-file.json";
	}

	@Override
	protected Method getMethod() {
		return Method.POST;
	}

	@Override
	protected void configureHttpClient(SyncHttpClient client) {
		client.addHeader("X-Mashape-Key", x_mashape_key);
	}

	@Override
	public String getName() {
		String name = new String("FaceRectAPI");
		if (this.features)
			name += "_features";
		return name;
	}

	@Override
	protected void setParams(RequestParams params, File image) {
		try {
			params.put("image", image);
			if (isFeaturesParam())
				params.put("features", "true");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public FaceDetectionResult parseResponse(int statusCode, Header[] headers, JSONObject response)
			throws JSONException {

		JSONArray jfaces = response.getJSONArray("faces");
		Face[] faces = new Face[jfaces.length()];
		for (int i = 0; i < jfaces.length(); i++) {
			org.json.JSONObject jface = jfaces.getJSONObject(i);
			faces[i] = faceToFace(jface);
		}
		FaceDetectionResultImpl result = new FaceDetectionResultImpl();
		result.setDetectedFaces(faces);
		result.setStringResult(response.toString());

		return result;
	}

	private Face faceToFace(JSONObject jface) throws JSONException {
		FaceImpl face = new FaceImpl();
		if (jface != null) {
			face.setFacePosition(new FacePosition(jface.getInt("x"), jface.getInt("y"), jface.getInt("width"),
					jface.getInt("height")));
			if (jface.has("orientation")) {
				String orientation = jface.getString("orientation");
				if (orientation.equals("frontal"))
					face.setFaceOrientation(new FaceOrientation(FaceOrientation.HighLevelOrientation.FRONTAL));
				else if (orientation.equals("profile-right"))
					face.setFaceOrientation(new FaceOrientation(FaceOrientation.HighLevelOrientation.PROFILE_RIGHT));
				else if (orientation.equals("profile-left"))
					face.setFaceOrientation(new FaceOrientation(FaceOrientation.HighLevelOrientation.PROFILE_LEFT));
				else
					face.setFaceOrientation(new FaceOrientation(FaceOrientation.HighLevelOrientation.UNKNOWN));
			}
			if (this.features && jface.has("features")) {
				try {
					JSONObject features = jface.getJSONObject("features");
					FaceLandmark noseLandmark = null;
					if (features.has("nose")) {
						JSONObject nose = features.getJSONObject("nose");
						int x = nose.getInt("x") + nose.getInt("width") / 2;
						int y = nose.getInt("y") + nose.getInt("height") / 2;
						noseLandmark = new FaceLandmark(FaceLandmark.LandmarkType.noseTip, x, y);
						face.addFaceLandmarks(noseLandmark);
					}
					if (features.has("mouth")) {
						JSONObject mouth = features.getJSONObject("mouth");
						int xLeft = mouth.getInt("x");
						int xRight = mouth.getInt("x") + mouth.getInt("width");
						int y = mouth.getInt("y") + mouth.getInt("height") / 2;
						face.addFaceLandmarks(new FaceLandmark(FaceLandmark.LandmarkType.mouthLeft, xLeft, y));
						face.addFaceLandmarks(new FaceLandmark(FaceLandmark.LandmarkType.mouthRight, xRight, y));
					}
					if (features.has("eyes")) {
						JSONArray eyes = features.getJSONArray("eyes");
						if (eyes.length() > 0) {
							JSONObject leftEye = eyes.getJSONObject(1);
							int x1 = leftEye.getInt("x") + leftEye.getInt("width") / 2;
							int y1 = leftEye.getInt("y") + leftEye.getInt("height") / 2;

							if (eyes.length() > 1) {
								JSONObject rightEye = eyes.getJSONObject(0);
								int x2 = rightEye.getInt("x") + rightEye.getInt("width") / 2;
								int y2 = rightEye.getInt("y") + rightEye.getInt("height") / 2;
								if (x1 > x2) {
									face.addFaceLandmarks(
											new FaceLandmark(FaceLandmark.LandmarkType.pupilRight, x2, y2));
									face.addFaceLandmarks(
											new FaceLandmark(FaceLandmark.LandmarkType.pupilLeft, x1, y1));
								} else {
									face.addFaceLandmarks(
											new FaceLandmark(FaceLandmark.LandmarkType.pupilRight, x1, y1));
									face.addFaceLandmarks(
											new FaceLandmark(FaceLandmark.LandmarkType.pupilLeft, x2, y2));
								}
							} else {
								if (noseLandmark != null) {
									if (noseLandmark.x > x1)
										face.addFaceLandmarks(
												new FaceLandmark(FaceLandmark.LandmarkType.pupilRight, x1, y1));
									else
										face.addFaceLandmarks(
												new FaceLandmark(FaceLandmark.LandmarkType.pupilLeft, x1, y1));
								} else
									face.addFaceLandmarks(
											new FaceLandmark(FaceLandmark.LandmarkType.pupilLeft, x1, y1));
							}
						}
					}
				} catch (JSONException e) {
				}
			}
		}
		return face;
	}
}
