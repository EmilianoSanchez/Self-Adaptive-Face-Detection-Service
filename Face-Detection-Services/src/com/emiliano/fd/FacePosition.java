package com.emiliano.fd;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Rect;
import android.util.Log;

public class FacePosition {
	
	public FacePosition(int left,int top,int width,int height){
		this.left=left;
		this.top=top;
		this.width=width;
		this.height=height;
	}
	
	public FacePosition(double left,double top,double width,double height){
		this((int)left,(int)top,(int)width,(int)height);
	}
	
	public int left;
	public int top;
	public int width;
	public int height;
	
	@Override
	public String toString() {
		return " \"facePosition\" : { \"left\" : "+left+" , \"top\" : "+top+" , \"width\" : "+width+" , \"height\" : "+height+" } ";
	}
	
	public Rect getRect(){
		return new Rect(left,top,left+width,top+height);
	}
	
	public int getArea(){
		return this.height*this.width;
	}
	
	public int getOverlapedArea(FacePosition other){
		if(other!=null){
			int mayorLeft=0;
			if(this.left<other.left)
				mayorLeft=other.left;
			else
				mayorLeft=this.left;
			int minorRight=0;
			if(this.left+this.width>other.left+other.width)
				minorRight=other.left+other.width;
			else
				minorRight=this.left+this.width;
			int overlapedWidth = 0;
			if(minorRight>mayorLeft)
				overlapedWidth = minorRight-mayorLeft;
			
			int mayorTop=0;
			if(this.top<other.top)
				mayorTop=other.top;
			else
				mayorTop=this.top;
			int minorBottom=0;
			if(this.top+this.height>other.top+other.height)
				minorBottom=other.top+other.height;
			else
				minorBottom=this.top+this.height;
			int overlapedHeight = 0;
			if(minorBottom>mayorTop)
				overlapedHeight = minorBottom-mayorTop;
			
			return overlapedHeight*overlapedWidth;
		}else
			return 0;
	}
	//return (this AND other)/(this OR other)
	public double getOverlapedAreaRatio(FacePosition other){
		double overlappingRatio=0.0;
		if(other!=null){
			int overlapedArea=this.getOverlapedArea(other);
			int fullArea = this.getArea()+other.getArea()-overlapedArea;
			overlappingRatio = ((double)overlapedArea / (double)fullArea);
		}
		return overlappingRatio;
	}

	public static JSONObject toJson(FacePosition facePosition) {
		JSONObject jsonObject=new JSONObject();
		if(facePosition!=null){
			try {
				jsonObject.put("left", facePosition.left);
				jsonObject.put("top", facePosition.top);
				jsonObject.put("width", facePosition.width);
				jsonObject.put("height", facePosition.height);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return jsonObject;
	}

	public static FacePosition fromJson(JSONObject jsonObject) throws JSONException{
		if(jsonObject!=null){
			int left=jsonObject.getInt("left");
			int top=jsonObject.getInt("top");
			int width=jsonObject.getInt("width");
			int height=jsonObject.getInt("height");
			
			FacePosition position=new FacePosition(left,top,width,height);
			return position;
		}else
			return null;
	}
}