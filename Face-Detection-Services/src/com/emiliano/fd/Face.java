package com.emiliano.fd;

import java.util.Collection;

public interface Face {
	
	FacePosition getFacePosition();
	FaceOrientation getFaceOrientation();
	Collection<FaceLandmark> getFaceLandmarks();
	Collection<FaceAttribute> getFaceAttributes(); 
	Integer getAge();
	Double getIsLeftEyeOpenConfidence();
	Double getIsRightEyeOpenConfidence();
	Double getIsSmilingConfidence();
	Double getIsFemaleConfidence();
}
