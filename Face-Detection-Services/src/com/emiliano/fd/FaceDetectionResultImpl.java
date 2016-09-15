package com.emiliano.fd;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FaceDetectionResultImpl implements FaceDetectionResult{

	private Face[] detectedFaces;
	private String stringResult;
	
	@Override
	public Face[] getDetectedFaces() {
		return detectedFaces;
	}

	@Override
	public String getStringResult() {
		return stringResult;
	}

	public void setDetectedFaces(Face[] detectedFaces) {
		this.detectedFaces = detectedFaces;
	}

	public void setStringResult(String stringResult) {
		this.stringResult = stringResult;
	}

	public String toString(){
		JSONObject jsonObject=FaceDetectionResultImpl.toJson(this);
		return jsonObject.toString();
//		StringBuilder builder=new StringBuilder("[");
//		for(Face face:this.getDetectedFaces()){
//			builder.append("{");
//			builder.append(face.toString());
//			builder.append("},");
//		}
//		builder.append("]");
//		return builder.toString();
	}
	
	public static JSONObject toJson(FaceDetectionResult result) {
		JSONObject jsonObject=new JSONObject();
		try{
			jsonObject.put("stringResult", result.getStringResult());
			JSONArray jsonArray=new JSONArray();
			for(Face face:result.getDetectedFaces())
				jsonArray.put(FaceImpl.toJson(face));
			jsonObject.put("detectedFaces", jsonArray);
		}catch(JSONException e){
			e.printStackTrace();
		}
		return jsonObject;
	}
	
	public static FaceDetectionResultImpl fromJson(JSONObject json) throws JSONException{
		FaceDetectionResultImpl result=new FaceDetectionResultImpl();
		result.setStringResult(json.getString("stringResult"));
		JSONArray jsonArray=json.getJSONArray("detectedFaces");
		Face[] faces=new Face[jsonArray.length()];
		for(int i=0;i<jsonArray.length();i++){
			JSONObject jsonObject=jsonArray.getJSONObject(i);
			faces[i]=FaceImpl.fromJson(jsonObject);
		}
		result.setDetectedFaces(faces);
		return result;
	}
}
