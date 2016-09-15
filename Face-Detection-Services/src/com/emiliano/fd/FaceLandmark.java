package com.emiliano.fd;

import org.json.JSONException;
import org.json.JSONObject;

public class FaceLandmark {
	public enum LandmarkType{
		unknown,
		pupilLeft,
        pupilRight,
        noseTip,
        mouthLeft,
        mouthRight,
        mouthCenter,
        eyebrowLeftOuter,
        eyebrowLeftInner,
        eyeLeftOuter,
        eyeLeftTop,
        eyeLeftBottom,
        eyeLeftInner,
        eyebrowRightInner,
        eyebrowRightOuter,
        eyeRightInner,
        eyeRightTop,
        eyeRightBottom,
        eyeRightOuter,
        noseRootLeft,
        noseRootRight,
        noseLeftAlarTop,
        noseRightAlarTop,
        noseLeftAlarOutTip,
        noseRightAlarOutTip,
        upperLipTop,
        upperLipBottom,
        underLipTop,
        underLipBottom, 
        cheekLeft, 
        cheekRight, 
        earLeft, 
        earLeftTip, 
        earRight, 
        earRightTip,
        chin
	}
	
	public FaceLandmark(int x, int y){
		this.type=LandmarkType.unknown;
		this.x=x;
		this.y=y;
	}
	
	public FaceLandmark(LandmarkType type, int x, int y){
		this.type=type;
		this.x=x;
		this.y=y;
	}
	
	public FaceLandmark(LandmarkType type, double x, double y){
		this(type,(int)x,(int)y);
	}
	
	public LandmarkType type;
	public int x;
	public int y;
	
	@Override
	public String toString() {
		return " \""+type.name()+"\" : { \"x\" : "+x+" , \"y\" : "+y+" } ";
	}

	public static JSONObject toJson(FaceLandmark landmark) {
		JSONObject jsonObject=new JSONObject();
		if(landmark!=null){
			try {
				jsonObject.put("type", landmark.type.toString());
				jsonObject.put("x", landmark.x);
				jsonObject.put("y", landmark.y);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return jsonObject;
	}

	public static FaceLandmark fromJson(JSONObject jsonObject)  throws JSONException{
		if(jsonObject!=null){
			LandmarkType type=LandmarkType.valueOf(jsonObject.getString("type"));
			int x=jsonObject.getInt("x");
			int y=jsonObject.getInt("y");
			
			FaceLandmark landmark=new FaceLandmark(type,x,y);
			return landmark;
		}else
			return null;
	}
}
