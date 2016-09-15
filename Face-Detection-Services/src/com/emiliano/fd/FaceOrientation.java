package com.emiliano.fd;

import org.json.JSONException;
import org.json.JSONObject;

public class FaceOrientation {
	
	public static enum HighLevelOrientation{ FRONTAL, PROFILE_LEFT,PROFILE_RIGHT, UNKNOWN };
	
	public FaceOrientation(HighLevelOrientation orientation){
		this.orientation=orientation;
		double [] angleVector=FaceOrientation.getOrientationAngleVector(orientation);
		this.yaw=angleVector[0];
		this.roll=angleVector[1];
		this.pitch=angleVector[2];
	}
	
	public FaceOrientation(double yaw,double roll,double pitch){
		this.yaw=yaw;
		this.roll=roll;
		this.pitch=pitch;
		this.orientation=FaceOrientation.getHighLevelOrientation(yaw, roll, pitch);
	}
	
	public HighLevelOrientation orientation;
	public double yaw;
	public double roll;
	public double pitch;
	
	public static double[] getOrientationAngleVector(HighLevelOrientation orientation){
		switch(orientation){
		case FRONTAL:
			return new double[]{0.0f,0.0f,0.0f};
		case PROFILE_LEFT:
			return new double[]{45.0f,0.0f,0.0f};
		case PROFILE_RIGHT:
			return new double[]{-45.0f,0.0f,0.0f};
		}
		return new double[]{0.0f,0.0f,0.0f};		
	}
	
	//yaw == euler Y, roll == euler Z, pitch == euler X
	public static HighLevelOrientation getHighLevelOrientation(double yaw,double roll,double pitch){
		if(yaw>30.0f)
			return HighLevelOrientation.PROFILE_LEFT;
		else
			if(yaw<-30.0f)
				return HighLevelOrientation.PROFILE_RIGHT;
			else
				return HighLevelOrientation.FRONTAL;
	}
	
	@Override
	public String toString() {
		return " \"faceOrientation\" : { \"high-level orientation\" : "+orientation.name()+" , \"yaw\" : "+yaw+" , \"roll\" : "+roll+" , \"pitch\" : "+pitch+" } ";
	}

	public static JSONObject toJson(FaceOrientation faceOrientation) {
		JSONObject jsonObject=new JSONObject();
		if(faceOrientation!=null){
			try {
				jsonObject.put("yaw", faceOrientation.yaw);
				jsonObject.put("roll", faceOrientation.roll);
				jsonObject.put("pitch", faceOrientation.pitch);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return jsonObject;
	}

	public static FaceOrientation fromJson(JSONObject jsonObject)  throws JSONException{
		if(jsonObject!=null){
			double yaw=jsonObject.getDouble("yaw");
			double roll=jsonObject.getDouble("roll");
			double pitch=jsonObject.getDouble("pitch");
			
			FaceOrientation orientation=new FaceOrientation(yaw,roll,pitch);
			return orientation;
		}else
			return null;
	}
}
