package com.emiliano.fd;

import org.json.JSONException;
import org.json.JSONObject;

import com.emiliano.fd.FaceLandmark.LandmarkType;

public class FaceAttribute {
	
	public FaceAttribute(String attributeName,double confidence){
		this.attributeName=attributeName;
		this.confidence=confidence;
	}
	
	public String attributeName;
	public double confidence;
	
	@Override
	public String toString() {
		return " \""+attributeName+"\" : "+confidence;
	}

	public static JSONObject toJson(FaceAttribute attribute) {
		JSONObject jsonObject=new JSONObject();
		if(attribute!=null){
			try {
				jsonObject.put("attributeName", attribute.attributeName);
				jsonObject.put("confidence", attribute.confidence);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return jsonObject;
	}

	public static FaceAttribute fromJson(JSONObject jsonObject)  throws JSONException{
		if(jsonObject!=null){
			String attributeName=jsonObject.getString("attributeName");
			double confidence=jsonObject.getDouble("confidence");
			
			FaceAttribute attribute=new FaceAttribute(attributeName,confidence);
			return attribute;
		}else
			return null;
	}
}
