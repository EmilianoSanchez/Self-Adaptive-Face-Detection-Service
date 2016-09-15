package com.emiliano.fd;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.vision.face.Landmark;
import com.google.gson.JsonObject;

public class FaceImpl implements Face{

	private FacePosition facePosition;
	private FaceOrientation faceOrientation;
	private Map<FaceLandmark.LandmarkType,FaceLandmark> faceLandmarks;
	private Map<String,FaceAttribute> faceAttributes;
	private Integer age;
	
	public FaceImpl(){
		this.faceLandmarks=new HashMap<FaceLandmark.LandmarkType,FaceLandmark>();
		this.faceAttributes=new HashMap<String,FaceAttribute>();
	}
	
	public void setFacePosition(FacePosition facePosition) {
		this.facePosition = facePosition;
	}

	public void setFaceOrientation(FaceOrientation faceOrientation) {
		this.faceOrientation = faceOrientation;
	}
	
	public void addFaceLandmarks(FaceLandmark faceLandmark) {
		this.faceLandmarks.put(faceLandmark.type, faceLandmark);
	}

	public void setIsLeftEyeOpenConfidence(Double isLeftEyeOpenConfidence) {
		this.faceAttributes.put("IsLeftEyeOpen",new FaceAttribute("IsLeftEyeOpen",isLeftEyeOpenConfidence));
	}

	public void setIsRightEyeOpenConfidence(Double isRightEyeOpenConfidence) {
		this.faceAttributes.put("IsRightEyeOpen",new FaceAttribute("IsRightEyeOpen",isRightEyeOpenConfidence));
	}

	public void setIsSmilingConfidence(Double isSmilingConfidence) {
		this.faceAttributes.put("IsSmiling",new FaceAttribute("IsSmiling",isSmilingConfidence));
	}
	
	public void setIsFemaleConfidence(Double isFemaleConfidence) {
		this.faceAttributes.put("IsFemale",new FaceAttribute("IsFemale",isFemaleConfidence));
	}
	
	public void addFaceAttributes(FaceAttribute faceAttribute) {
		this.faceAttributes.put(faceAttribute.attributeName, faceAttribute);
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Override
	public FacePosition getFacePosition() {
		return facePosition;
	}

	@Override
	public FaceOrientation getFaceOrientation() {
		return faceOrientation;
	}

	@Override
	public Collection<FaceLandmark> getFaceLandmarks() {
		return faceLandmarks.values();
	}

	@Override
	public Double getIsLeftEyeOpenConfidence() {
		FaceAttribute faceAttribute=this.faceAttributes.get("IsLeftEyeOpen");
		if(faceAttribute!=null)
			return faceAttribute.confidence;
		return null;
	}

	@Override
	public Double getIsRightEyeOpenConfidence() {
		FaceAttribute faceAttribute=this.faceAttributes.get("IsRightEyeOpen");
		if(faceAttribute!=null)
			return faceAttribute.confidence;
		return null;
	}

	@Override
	public Double getIsSmilingConfidence() {
		FaceAttribute faceAttribute=this.faceAttributes.get("IsSmiling");
		if(faceAttribute!=null)
			return faceAttribute.confidence;
		faceAttribute=this.faceAttributes.get("smiling");
		if(faceAttribute!=null)
			return faceAttribute.confidence;
		return null;
	}

	@Override
	public Double getIsFemaleConfidence() {
		FaceAttribute faceAttribute=this.faceAttributes.get("IsFemale");
		if(faceAttribute!=null)
			return faceAttribute.confidence;
		return null;
	}
	
	@Override
	public Collection<FaceAttribute> getFaceAttributes() {
		return faceAttributes.values();
	}

	@Override
	public Integer getAge() {
		return age;
	}

	
	@Override
	public String toString() {
		JSONObject jsonObject=FaceImpl.toJson(this);
		return jsonObject.toString();
//		StringBuilder builder=new StringBuilder(" \"face\" : {");
//		if(facePosition!=null){
//			builder.append(facePosition.toString());
//			builder.append(",");
//		}
//		if(faceOrientation!=null){
//			builder.append(faceOrientation.toString());
//			builder.append(",");
//		}
//		if(faceLandmarks!=null && faceLandmarks.size()>0){
//			builder.append("\"faceLandmarks\": {");
//			for(FaceLandmark landmark:getFaceLandmarks()){
//				builder.append(landmark.toString());
//				builder.append(" ,");
//			}
//			builder.append("},");
//		}
//		
//		if(faceAttributes!=null && faceAttributes.size()>0){
//			builder.append("\"faceAttributes\": {");
//			for(FaceAttribute faceAttribute:getFaceAttributes()){
//				builder.append(faceAttribute.toString());
//				builder.append(" ,");
//			}
//			builder.append("},");
//		}
//		if(age!=null){
//			builder.append("\"age\" : "+age);
//			builder.append(",");
//		}
//		if(race!=null){
//			builder.append("\"race\" : "+race.name());
//			builder.append(",");
//		}
//		builder.append("}");
//		return builder.toString();
	}

	
	public static JSONObject toJson(Face face) {
		JSONObject jsonObject=new JSONObject();
		try{
			if(face!=null){
				jsonObject.put("facePosition", FacePosition.toJson(face.getFacePosition()));
				
				if(face.getFaceOrientation()!=null){
					jsonObject.put("faceOrientation", FaceOrientation.toJson(face.getFaceOrientation()));
				}
				
				if(face.getAge()!=null){
					jsonObject.put("age", face.getAge());
				}
				
				if(face.getFaceLandmarks()!=null){
					JSONArray landmarks=new JSONArray();
					for(FaceLandmark landmark:face.getFaceLandmarks())
						landmarks.put(FaceLandmark.toJson(landmark));
					jsonObject.put("faceLandmarks", landmarks);
				}
				if(face.getFaceAttributes()!=null){
					JSONArray attributes=new JSONArray();
					for(FaceAttribute attribute:face.getFaceAttributes())
						attributes.put(FaceAttribute.toJson(attribute));
					jsonObject.put("faceAttributes", attributes);
				}
			}
		}catch(JSONException e){
			e.printStackTrace();
		}
		return jsonObject;
	}

	public static Face fromJson(JSONObject jsonObject)  throws JSONException{
		FaceImpl face=new FaceImpl();
		
		if(jsonObject!=null){
			if(jsonObject.has("facePosition"))
				face.setFacePosition(FacePosition.fromJson(jsonObject.getJSONObject("facePosition")));
			if(jsonObject.has("faceOrientation"))
				face.setFaceOrientation(FaceOrientation.fromJson(jsonObject.getJSONObject("faceOrientation")));
			if(jsonObject.has("age"))
				face.setAge(jsonObject.getInt("age"));
			if(jsonObject.has("faceLandmarks")){
				JSONArray array=jsonObject.getJSONArray("faceLandmarks");
				for(int i=0;i<array.length();i++){
					FaceLandmark landmark=FaceLandmark.fromJson(array.getJSONObject(i));
					face.addFaceLandmarks(landmark);
				}
			}
			if(jsonObject.has("faceAttributes")){
				JSONArray array=jsonObject.getJSONArray("faceAttributes");
				for(int i=0;i<array.length();i++){
					FaceAttribute attribute=FaceAttribute.fromJson(array.getJSONObject(i));
					face.addFaceAttributes(attribute);
				}
			}

		}
		return face;
	}
}
