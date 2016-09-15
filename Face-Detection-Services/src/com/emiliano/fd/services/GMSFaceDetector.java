package com.emiliano.fd.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import com.emiliano.fd.Face;
import com.emiliano.fd.FaceDetectionException;
import com.emiliano.fd.FaceDetectionResult;
import com.emiliano.fd.FaceDetectionResultImpl;
import com.emiliano.fd.FaceImpl;
import com.emiliano.fd.FaceLandmark;
import com.emiliano.fd.FaceOrientation;
import com.emiliano.fd.FacePosition;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.FaceDetector;
import com.google.android.gms.vision.face.Landmark;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.util.SparseArray;

public class GMSFaceDetector implements FaceDetectionServiceClient{

	private FaceDetector detector;
	private Context context;
	private boolean allLandmarks;
	private boolean allClassifications;
	private boolean accurateMode;
	
	public GMSFaceDetector(Context context){
		this(context, false, false, false);
	}
	
	public GMSFaceDetector(Context context,boolean allLandmarks,boolean allClassifications,boolean accurateMode){
		this.context=context;
		this.allLandmarks=allLandmarks;
		this.allClassifications=allClassifications;
		this.accurateMode=accurateMode;
		buildDetector();
	}
	
	public void setParams(boolean allLandmarks,boolean allClassifications,boolean accurateMode) {
		if(this.allClassifications!=allClassifications || this.allLandmarks!=allLandmarks || this.accurateMode!=accurateMode){
			this.allClassifications = allClassifications;
			this.allLandmarks = allLandmarks;
			this.accurateMode = accurateMode;
			buildDetector();
		}
	}
	
	public boolean isAllLandmarksParam() {
		return allLandmarks;
	}

	public void setAllLandmarksParam(boolean allLandmarks) {
		if(this.allLandmarks!=allLandmarks){
			this.allLandmarks = allLandmarks;
			buildDetector();
		}
	}

	public boolean isAllClassificationsParam() {
		return allClassifications;
	}

	public void setAllClassificationsParam(boolean allClassifications) {
		if(this.allClassifications!=allClassifications){
			this.allClassifications = allClassifications;
			buildDetector();
		}
	}

	public boolean isAccurateModeParam() {
		return accurateMode;
	}

	public void setAccurateModeParam(boolean accurateMode) {
		if(this.accurateMode!=accurateMode){
			this.accurateMode = accurateMode;
			buildDetector();
		}
	}

	private void buildDetector() {
		FaceDetector.Builder builder = new FaceDetector.Builder(context)
        .setTrackingEnabled(false)
        .setProminentFaceOnly(false);
		if(allLandmarks)
			builder.setLandmarkType(FaceDetector.ALL_LANDMARKS);
		else
			builder.setLandmarkType(FaceDetector.NO_LANDMARKS);
		if(allClassifications)
			builder.setClassificationType(FaceDetector.ALL_CLASSIFICATIONS);
		else
			builder.setClassificationType(FaceDetector.NO_CLASSIFICATIONS);
		if(accurateMode)
			builder.setMode(FaceDetector.ACCURATE_MODE);
		else
			builder.setMode(FaceDetector.FAST_MODE);
        
        this.detector=builder.build();
	}

	@Override
	public String getName() {
		String name=new String("GMSFaceDetector");
		if(this.allLandmarks)
			name+="_allLandmarks";
		if(this.allClassifications)
			name+="_allClassifications";
		if(this.accurateMode)
			name+="_accurateMode";	
		return name;
	}

	@Override
	public FaceDetectionResult execute(File input) throws FaceDetectionException{
		
		try {
			InputStream stream;
			stream = new FileInputStream(input);
	        Bitmap bitmap = BitmapFactory.decodeStream(stream);
	        Frame frame = new Frame.Builder().setBitmap(bitmap).build();
	        SparseArray<com.google.android.gms.vision.face.Face> gfaces = detector.detect(frame);
			Face [] faces=new Face[gfaces.size()];
			for(int i=0;i<gfaces.size();i++)
				faces[i]=faceToface(gfaces.get(i));	
			FaceDetectionResultImpl result=new FaceDetectionResultImpl();
			result.setDetectedFaces(faces);
			result.setStringResult(gfaces.toString());
			return result;
		} catch (FileNotFoundException e) {
			throw new FaceDetectionException(e);
		}
	}
	
	private com.emiliano.fd.Face faceToface(com.google.android.gms.vision.face.Face gface) {
		FaceImpl face = new FaceImpl();

		if(gface!=null && gface.getPosition()!=null){
			int left=(int)gface.getPosition().x;
			int top=(int)gface.getPosition().y;
			int width=(int)gface.getWidth();
			int height=(int)gface.getHeight();
			face.setFacePosition(new FacePosition(left,top,width,height));
			
			double yaw=gface.getEulerY();
			double roll=gface.getEulerZ();
			face.setFaceOrientation(new FaceOrientation(yaw,roll,0.0));
			
			if(this.allLandmarks && gface.getLandmarks()!=null){
				List<Landmark> landmarks=gface.getLandmarks();
				for(Landmark landmark:landmarks){
					face.addFaceLandmarks(new FaceLandmark(landmarkTypeToLandmarkType(landmark.getType()), landmark.getPosition().x, landmark.getPosition().y));
				}
					
			}
			if(this.allClassifications){
				face.setIsSmilingConfidence((double) gface.getIsSmilingProbability());
				face.setIsLeftEyeOpenConfidence((double) gface.getIsLeftEyeOpenProbability());
				face.setIsRightEyeOpenConfidence((double) gface.getIsRightEyeOpenProbability());
			}
		}
		return face;
	}

	private FaceLandmark.LandmarkType landmarkTypeToLandmarkType(int glandmarktype){
		switch(glandmarktype){
		case Landmark.BOTTOM_MOUTH:
			return FaceLandmark.LandmarkType.mouthCenter;
		case Landmark.LEFT_CHEEK:
			return FaceLandmark.LandmarkType.cheekLeft;
		case Landmark.LEFT_EAR:
			return FaceLandmark.LandmarkType.earLeft;
		case Landmark.LEFT_EAR_TIP:
			return FaceLandmark.LandmarkType.earLeftTip;
		case Landmark.LEFT_EYE:
			return FaceLandmark.LandmarkType.pupilLeft;
		case Landmark.LEFT_MOUTH:
			return FaceLandmark.LandmarkType.mouthLeft;
		case Landmark.NOSE_BASE:
			return FaceLandmark.LandmarkType.noseTip;
		case Landmark.RIGHT_CHEEK:
			return FaceLandmark.LandmarkType.cheekRight;
		case Landmark.RIGHT_EAR:
			return FaceLandmark.LandmarkType.earRight;
		case Landmark.RIGHT_EAR_TIP:
			return FaceLandmark.LandmarkType.earRightTip;
		case Landmark.RIGHT_EYE:
			return FaceLandmark.LandmarkType.pupilRight;
		case Landmark.RIGHT_MOUTH:
			return FaceLandmark.LandmarkType.mouthRight;
		}
		return FaceLandmark.LandmarkType.unknown;
	}
}
