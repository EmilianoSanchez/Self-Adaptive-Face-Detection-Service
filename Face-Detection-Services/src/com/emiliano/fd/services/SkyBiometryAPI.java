package com.emiliano.fd.services;

import java.io.File;
import java.io.FileNotFoundException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.emiliano.fd.Face;
import com.emiliano.fd.FaceAttribute;
import com.emiliano.fd.FaceDetectionException;
import com.emiliano.fd.FaceDetectionResult;
import com.emiliano.fd.FaceDetectionResultImpl;
import com.emiliano.fd.FaceImpl;
import com.emiliano.fd.FaceLandmark;
import com.emiliano.fd.FaceLandmark.LandmarkType;
import com.emiliano.fd.FaceOrientation;
import com.emiliano.fd.FacePosition;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import cz.msebera.android.httpclient.Header;

public class SkyBiometryAPI extends FaceDetectionRestServiceClient {

	private boolean gender;
	private boolean glasses;
	private boolean smiling;
	private boolean aggresiveDetector;
	private boolean mood;
	private boolean age;
	private boolean openEyes;
	private String x_mashape_key;
	private String sky_biometry_api_key;
	private String sky_biometry_api_secret;

	public SkyBiometryAPI(String x_mashape_key, String sky_biometry_api_key, String sky_biometry_api_secret) {
		this(x_mashape_key, sky_biometry_api_key, sky_biometry_api_secret, false, false, false, false, false, false,
				false);
	}

	public SkyBiometryAPI(String x_mashape_key, String sky_biometry_api_key, String sky_biometry_api_secret,
			boolean gender, boolean smiling, boolean glasses, boolean aggresiveDetector, boolean mood, boolean age,
			boolean openEyes) {
		this.x_mashape_key = x_mashape_key;
		this.sky_biometry_api_key = sky_biometry_api_key;
		this.sky_biometry_api_secret = sky_biometry_api_secret;
		setParams(gender, glasses, smiling, aggresiveDetector, mood, age, openEyes);
	}

	public void setParams(boolean gender, boolean smiling, boolean glasses, boolean aggresiveDetector, boolean mood,
			boolean age, boolean openEyes) {
		setGenderParam(gender);
		setGlassesParam(glasses);
		setSmilingParam(smiling);
		setAggresiveDetectorParam(aggresiveDetector);
		setMoodParam(mood);
		setAgeParam(age);
		setOpenEyesParam(openEyes);
	}

	public boolean isGenderParam() {
		return gender;
	}

	public void setGenderParam(boolean gender) {
		this.gender = gender;
	}

	public boolean isGlassesParam() {
		return glasses;
	}

	public void setGlassesParam(boolean glasses) {
		this.glasses = glasses;
	}

	public boolean isSmilingParam() {
		return smiling;
	}

	public void setSmilingParam(boolean smiling) {
		this.smiling = smiling;
	}

	public boolean isAggresiveDetectorParam() {
		return aggresiveDetector;
	}

	public void setAggresiveDetectorParam(boolean aggresiveDetector) {
		this.aggresiveDetector = aggresiveDetector;
	}

	public boolean isMoodParam() {
		return gender;
	}

	public void setMoodParam(boolean mood) {
		this.mood = mood;
	}

	public boolean isAgeParam() {
		return age;
	}

	public void setAgeParam(boolean age) {
		this.age = age;
	}

	public boolean isOpenEyesParam() {
		return openEyes;
	}

	public void setOpenEyesParam(boolean openEyes) {
		this.openEyes = openEyes;
	}

	@Override
	protected String getUrl() {
		return "https://face.p.mashape.com/faces/detect?api_key="+sky_biometry_api_key +"&api_secret="+sky_biometry_api_secret;
	}

	@Override
	protected Method getMethod() {
		return Method.POST;
	}

	@Override
	protected void configureHttpClient(SyncHttpClient client) {
		client.addHeader("X-Mashape-Key", x_mashape_key);
		client.addHeader("Content-Type", "application/x-www-form-urlencoded");
		client.addHeader("Accept", "application/json");
	}

	@Override
	public String getName() {
		String name = new String("SkyBiometryAPI");
		if (this.gender)
			name += "_gender";
		if (this.smiling)
			name += "_smiling";
		if (this.glasses)
			name += "_glasses";
		if (this.aggresiveDetector)
			name += "_aggresiveDetector";
		if (this.mood)
			name += "_mood";
		if (this.age)
			name += "_age";
		if (this.openEyes)
			name += "_openEyes";
		return name;
	}

	@Override
	protected void setParams(RequestParams params, File image) throws FaceDetectionException {
		try {
			params.put("files", image);
			String attributes = new String("");
			if (this.gender)
				attributes += "gender,";
			if (this.glasses)
				attributes += "glasses,";
			if (this.smiling)
				attributes += "smiling,";
			if (this.mood)
				attributes += "mood,";
			if (this.age)
				attributes += "age,";
			if (this.openEyes)
				attributes += "eyes,";
			if (!attributes.equals(""))
				params.put("attributes", attributes);
			if (this.aggresiveDetector)
				params.put("detector", "Aggressive");
		} catch (FileNotFoundException e) {
			throw new FaceDetectionException(e);
		}
	}

	@Override
	public FaceDetectionResult parseResponse(int statusCode, Header[] headers, JSONObject response)
			throws JSONException {

		JSONObject jPhoto = response.getJSONArray("photos").getJSONObject(0);
		double imageWidth = jPhoto.getDouble("width");
		double imageHeight = jPhoto.getDouble("height");
		JSONArray jfaces = jPhoto.getJSONArray("tags");
		Face[] faces = new Face[jfaces.length()];
		for (int i = 0; i < jfaces.length(); i++) {
			org.json.JSONObject jface = jfaces.getJSONObject(i);
			faces[i] = faceToFace(jface, imageWidth, imageHeight);
		}
		FaceDetectionResultImpl result = new FaceDetectionResultImpl();
		result.setDetectedFaces(faces);
		result.setStringResult(response.toString());
		return result;
	}

	private Face faceToFace(JSONObject jface, double imageWidth, double imageHeight) throws JSONException {

		FaceImpl face = new FaceImpl();
		if (jface != null) {
			org.json.JSONObject jcenter = jface.getJSONObject("center");
			int width = (int) (imageWidth * jface.getDouble("width") / 100.0);
			int height = (int) (imageHeight * jface.getDouble("height") / 100.0);
			int centerX = (int) (imageWidth * jcenter.getDouble("x") / 100.0);
			int centerY = (int) (imageHeight * jcenter.getDouble("y") / 100.0);
			face.setFacePosition(new FacePosition(centerX - width / 2, centerY - height / 2, width, height));
			if (jface.has("yaw") && jface.has("roll") && jface.has("pitch"))
				face.setFaceOrientation(
						new FaceOrientation(jface.getInt("yaw"), jface.getInt("roll"), jface.getInt("pitch")));
			if (jface.has("eye_left")) {
				int x = (int) (imageWidth * jface.getJSONObject("eye_left").getDouble("x") / 100.0);
				int y = (int) (imageHeight * jface.getJSONObject("eye_left").getDouble("y") / 100.0);
				face.addFaceLandmarks(new FaceLandmark(LandmarkType.pupilLeft, x, y));
			}
			if (jface.has("eye_right")) {
				int x = (int) (imageWidth * jface.getJSONObject("eye_right").getDouble("x") / 100.0);
				int y = (int) (imageHeight * jface.getJSONObject("eye_right").getDouble("y") / 100.0);
				face.addFaceLandmarks(new FaceLandmark(LandmarkType.pupilRight, x, y));
			}
			if (jface.has("mouth_center")) {
				int x = (int) (imageWidth * jface.getJSONObject("mouth_center").getDouble("x") / 100.0);
				int y = (int) (imageHeight * jface.getJSONObject("mouth_center").getDouble("y") / 100.0);
				face.addFaceLandmarks(new FaceLandmark(LandmarkType.mouthCenter, x, y));
			}
			if (jface.has("nose")) {
				int x = (int) (imageWidth * jface.getJSONObject("nose").getDouble("x") / 100.0);
				int y = (int) (imageHeight * jface.getJSONObject("nose").getDouble("y") / 100.0);
				face.addFaceLandmarks(new FaceLandmark(LandmarkType.noseTip, x, y));
			}
			if (jface.has("mouth_left")) {
				int x = (int) (imageWidth * jface.getJSONObject("mouth_left").getDouble("x") / 100.0);
				int y = (int) (imageHeight * jface.getJSONObject("mouth_left").getDouble("y") / 100.0);
				face.addFaceLandmarks(new FaceLandmark(LandmarkType.mouthLeft, x, y));
			}
			if (jface.has("mouth_right")) {
				int x = (int) (imageWidth * jface.getJSONObject("mouth_right").getDouble("x") / 100.0);
				int y = (int) (imageHeight * jface.getJSONObject("mouth_right").getDouble("y") / 100.0);
				face.addFaceLandmarks(new FaceLandmark(LandmarkType.mouthRight, x, y));
			}
			if (jface.has("ear_left")) {
				int x = (int) (imageWidth * jface.getJSONObject("ear_left").getDouble("x") / 100.0);
				int y = (int) (imageHeight * jface.getJSONObject("ear_left").getDouble("y") / 100.0);
				face.addFaceLandmarks(new FaceLandmark(LandmarkType.earLeft, x, y));
			}
			if (jface.has("ear_right")) {
				int x = (int) (imageWidth * jface.getJSONObject("ear_right").getDouble("x") / 100.0);
				int y = (int) (imageHeight * jface.getJSONObject("ear_right").getDouble("y") / 100.0);
				face.addFaceLandmarks(new FaceLandmark(LandmarkType.earRight, x, y));
			}
			if (jface.has("chin")) {
				int x = (int) (imageWidth * jface.getJSONObject("chin").getDouble("x") / 100.0);
				int y = (int) (imageHeight * jface.getJSONObject("chin").getDouble("y") / 100.0);
				face.addFaceLandmarks(new FaceLandmark(LandmarkType.chin, x, y));
			}

			if (jface.has("attributes")) {
				JSONObject attributes = jface.getJSONObject("attributes");
				if (this.gender && attributes.has("gender")) {
					JSONObject gender = attributes.getJSONObject("gender");
					String value = gender.getString("value");
					int confidence = gender.getInt("confidence");
					double isFemaleConfidence = 0.5;
					if (value.equals("female"))
						isFemaleConfidence += ((double) confidence) / 200.0;
					else
						isFemaleConfidence -= ((double) confidence) / 200.0;
					face.setIsFemaleConfidence(isFemaleConfidence);
				}
				if (this.smiling) {
					addAttributeIfExists(attributes, "smiling", face, "smiling");
				}
				if (this.glasses) {
					addAttributeIfExists(attributes, "glasses", face, "glasses");
					addAttributeIfExists(attributes, "dark_glasses", face, "dark_glasses");
				}
				if (this.mood) {
					addAttributeIfExists(attributes, "neutral_mood", face, "neutral_mood");
					addAttributeIfExists(attributes, "anger", face, "anger");
					addAttributeIfExists(attributes, "disgust", face, "disgust");
					addAttributeIfExists(attributes, "fear", face, "fear");
					addAttributeIfExists(attributes, "happiness", face, "happiness");
					addAttributeIfExists(attributes, "sadness", face, "sadness");
					addAttributeIfExists(attributes, "surprise", face, "surprise");
				}
				if (this.age && attributes.has("age_est")) {
					JSONObject age_est = attributes.getJSONObject("age_est");
					String value = age_est.getString("value");
					try {
						int age = Integer.valueOf(value);
						face.setAge(age);
					} catch (NumberFormatException e) {
					}
				}
				if (this.openEyes && attributes.has("eyes")) {
					JSONObject openEyes = attributes.getJSONObject("eyes");
					String value = openEyes.getString("value");
					int confidence = openEyes.getInt("confidence");
					double openEyesConfidence = 0.5;
					if (value.equals("open"))
						openEyesConfidence += ((double) confidence) / 200.0;
					else
						openEyesConfidence -= ((double) confidence) / 200.0;
					face.setIsLeftEyeOpenConfidence(openEyesConfidence);
					face.setIsRightEyeOpenConfidence(openEyesConfidence);
				}
			}
		}
		return face;
	}

	private void addAttributeIfExists(JSONObject attributes, String attributeName, FaceImpl face, String faceAttribute)
			throws JSONException {
		if (attributes.has(attributeName)) {
			JSONObject attribute = attributes.getJSONObject(attributeName);
			String value = attribute.getString("value");
			int confidence = attribute.getInt("confidence");
			double doubleConfidence = 0.5;
			if (value.equals("true"))
				doubleConfidence += ((double) confidence) / 200.0;
			else
				doubleConfidence -= ((double) confidence) / 200.0;
			face.addFaceAttributes(new FaceAttribute(faceAttribute, doubleConfidence));
		}
	}

}
