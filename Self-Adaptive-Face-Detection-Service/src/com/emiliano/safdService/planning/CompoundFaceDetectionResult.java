package com.emiliano.safdService.planning;

import com.emiliano.fd.FaceDetectionResult;
import com.emiliano.fd.FaceDetectionResultImpl;

public class CompoundFaceDetectionResult extends FaceDetectionResultImpl{
	
	public static final int FEATURE_INTERSECTION=0;
	public static final int FEATURE_UNION=1;
	
	public CompoundFaceDetectionResult(FaceDetectionResult ...detectionResults){
		this(FEATURE_INTERSECTION,detectionResults);
	}
	
	public CompoundFaceDetectionResult(int modo,FaceDetectionResult ...detectionResults){
		if(detectionResults!=null && detectionResults.length>0){
			super.setDetectedFaces(detectionResults[0].getDetectedFaces());
			super.setStringResult(detectionResults[0].getStringResult());
		}
	}

	

}
