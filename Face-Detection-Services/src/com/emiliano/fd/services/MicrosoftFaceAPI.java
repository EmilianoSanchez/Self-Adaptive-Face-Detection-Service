package com.emiliano.fd.services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import com.emiliano.fd.FaceAttribute;
import com.emiliano.fd.FaceDetectionException;
import com.emiliano.fd.FaceDetectionResult;
import com.emiliano.fd.FaceDetectionResultImpl;
import com.emiliano.fd.FaceImpl;
import com.emiliano.fd.FaceLandmark;
import com.emiliano.fd.FaceLandmark.LandmarkType;
import com.emiliano.fd.FaceOrientation;
import com.emiliano.fd.FacePosition;
import com.emiliano.fd.utils.ImageHelper;
import com.emiliano.fd.utils.ResourceHelper;
import com.microsoft.projectoxford.face.FaceServiceClient;
import com.microsoft.projectoxford.face.FaceServiceClient.FaceAttributeType;
import com.microsoft.projectoxford.face.FaceServiceRestClient;
import com.microsoft.projectoxford.face.rest.ClientException;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

public class MicrosoftFaceAPI implements FaceDetectionServiceClient {

	private FaceServiceRestClient client;
	private boolean landmarks;
	private Set<FaceAttributeType> faceAttributes;

	public MicrosoftFaceAPI(String microsoft_faceapi_key) {
		this(microsoft_faceapi_key,false, false, false, false, false, false);
	}

	public MicrosoftFaceAPI(String microsoft_faceapi_key,boolean landmarks, boolean age, boolean gender, boolean facialHair, boolean smile,boolean headPose) {
		client = new FaceServiceRestClient(microsoft_faceapi_key);
		faceAttributes = new HashSet<FaceAttributeType>();
		setParams(landmarks, age, gender, facialHair, smile, headPose);
	}

	public void setParams(boolean landmarks, boolean age, boolean gender, boolean facialHair, boolean smile,
			boolean headPose) {
		setLandmarksParam(landmarks);
		setAgeParam(age);
		setGenderParam(gender);
		setFacialHairParam(facialHair);
		setSmileParam(smile);
		setHeadPoseParam(headPose);
	}

	public boolean isHeadPoseParam() {
		return faceAttributes.contains(FaceAttributeType.HeadPose);
	}

	public void setHeadPoseParam(boolean headPose) {
		if (headPose) {
			faceAttributes.add(FaceAttributeType.HeadPose);
		} else {
			faceAttributes.remove(FaceAttributeType.HeadPose);
		}
	}

	public boolean isSmileParam() {
		return faceAttributes.contains(FaceAttributeType.Smile);
	}

	public void setSmileParam(boolean smile) {
		if (smile) {
			faceAttributes.add(FaceAttributeType.Smile);
		} else {
			faceAttributes.remove(FaceAttributeType.Smile);
		}

	}

	public boolean isFacialHairParam() {
		return faceAttributes.contains(FaceAttributeType.FacialHair);
	}

	public void setFacialHairParam(boolean facialHair) {
		if (facialHair) {
			faceAttributes.add(FaceAttributeType.FacialHair);
		} else {
			faceAttributes.remove(FaceAttributeType.FacialHair);
		}

	}

	public boolean isGenderParam() {
		return faceAttributes.contains(FaceAttributeType.Gender);
	}

	public void setGenderParam(boolean gender) {
		if (gender) {
			faceAttributes.add(FaceAttributeType.Gender);
		} else {
			faceAttributes.remove(FaceAttributeType.Gender);
		}

	}

	public boolean isAgeParam() {
		return faceAttributes.contains(FaceAttributeType.Age);
	}

	public void setAgeParam(boolean age) {
		if (age) {
			faceAttributes.add(FaceAttributeType.Age);
		} else {
			faceAttributes.remove(FaceAttributeType.Age);
		}

	}

	public boolean isLandmarksParam() {
		return this.landmarks;
	}

	public void setLandmarksParam(boolean landmarks) {
		this.landmarks = landmarks;
	}

	@Override
	public String getName() {
		String name=new String("ProjectOxfordFaceAPI");
		if(this.landmarks)
			name+="_landmarks";
		if(this.isAgeParam())
			name+="_age";
		if(this.isGenderParam())
			name+="_gender";	
		if(this.isFacialHairParam())
			name+="_facialHair";	
		if(this.isSmileParam())
			name+="_smile";	
		if(this.isHeadPoseParam())
			name+="_headPose";	
		return name;
	}

	@Override
	public FaceDetectionResult execute(File image) throws FaceDetectionException {

		// Uri mImageUri=ResourceHelper.fileToUri(image);
		// Bitmap mBitmap = ImageHelper.loadSizeLimitedBitmapFromUri(
		// mImageUri, context.getContentResolver());
		// if(mBitmap!=null)
		// throw new FaceDetectionException("Image couldn't be loaded");
		//
		// ByteArrayOutputStream output = new ByteArrayOutputStream();
		// mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
		// ByteArrayInputStream inputStream = new
		// ByteArrayInputStream(output.toByteArray());

		try {
			InputStream inputStream = new FileInputStream(image);

			com.microsoft.projectoxford.face.contract.Face[] facesAux = client.detect(
					inputStream, /* Input stream of image to detect */
					false, /* Whether to return face ID */
					landmarks, /* Whether to return face landmarks */
					/*
					 * Which face attributes to analyze, currently we support:
					 * age,gender,headPose,smile,facialHair
					 */
					faceAttributes);

			com.emiliano.fd.Face[] faces = new com.emiliano.fd.Face[facesAux.length];
			for (int i = 0; i < facesAux.length; i++)
				faces[i] = faceToface(facesAux[i]);
			FaceDetectionResultImpl result = new FaceDetectionResultImpl();
			result.setDetectedFaces(faces);
			result.setStringResult(client.getLastJsonResponse());
			return result;
		} catch (ClientException | IOException e) {
			throw new FaceDetectionException(e);
		}
	}

	private com.emiliano.fd.Face faceToface(com.microsoft.projectoxford.face.contract.Face faceAux) {
		FaceImpl face = new FaceImpl();
		if (faceAux.faceRectangle != null)
			face.setFacePosition(new FacePosition(faceAux.faceRectangle.left, faceAux.faceRectangle.top,
					faceAux.faceRectangle.width, faceAux.faceRectangle.height));
		//Landmarks are changed left-right
		if (isLandmarksParam() && faceAux.faceLandmarks != null) {
			if (faceAux.faceLandmarks.eyebrowLeftInner != null)
				face.addFaceLandmarks(new FaceLandmark(LandmarkType.eyebrowRightInner,
						faceAux.faceLandmarks.eyebrowLeftInner.x, faceAux.faceLandmarks.eyebrowLeftInner.y));
			if (faceAux.faceLandmarks.eyebrowLeftOuter != null)
				face.addFaceLandmarks(new FaceLandmark(LandmarkType.eyebrowRightOuter,
						faceAux.faceLandmarks.eyebrowLeftOuter.x, faceAux.faceLandmarks.eyebrowLeftOuter.y));
			if (faceAux.faceLandmarks.eyebrowRightInner != null)
				face.addFaceLandmarks(new FaceLandmark(LandmarkType.eyebrowLeftInner,
						faceAux.faceLandmarks.eyebrowRightInner.x, faceAux.faceLandmarks.eyebrowRightInner.y));
			if (faceAux.faceLandmarks.eyebrowRightOuter != null)
				face.addFaceLandmarks(new FaceLandmark(LandmarkType.eyebrowLeftOuter,
						faceAux.faceLandmarks.eyebrowRightOuter.x, faceAux.faceLandmarks.eyebrowRightOuter.y));

			if (faceAux.faceLandmarks.eyeLeftBottom != null)
				face.addFaceLandmarks(new FaceLandmark(LandmarkType.eyeRightBottom, faceAux.faceLandmarks.eyeLeftBottom.x,
						faceAux.faceLandmarks.eyeLeftBottom.y));
			if (faceAux.faceLandmarks.eyeLeftInner != null)
				face.addFaceLandmarks(new FaceLandmark(LandmarkType.eyeRightInner, faceAux.faceLandmarks.eyeLeftInner.x,
						faceAux.faceLandmarks.eyeLeftInner.y));
			if (faceAux.faceLandmarks.eyeLeftOuter != null)
				face.addFaceLandmarks(new FaceLandmark(LandmarkType.eyeRightOuter, faceAux.faceLandmarks.eyeLeftOuter.x,
						faceAux.faceLandmarks.eyeLeftOuter.y));
			if (faceAux.faceLandmarks.eyeLeftTop != null)
				face.addFaceLandmarks(new FaceLandmark(LandmarkType.eyeRightTop, faceAux.faceLandmarks.eyeLeftTop.x,
						faceAux.faceLandmarks.eyeLeftTop.y));

			if (faceAux.faceLandmarks.eyeRightBottom != null)
				face.addFaceLandmarks(new FaceLandmark(LandmarkType.eyeLeftBottom, faceAux.faceLandmarks.eyeRightBottom.x,
						faceAux.faceLandmarks.eyeRightBottom.y));
			if (faceAux.faceLandmarks.eyeRightInner != null)
				face.addFaceLandmarks(new FaceLandmark(LandmarkType.eyeLeftInner, faceAux.faceLandmarks.eyeRightInner.x,
						faceAux.faceLandmarks.eyeRightInner.y));
			if (faceAux.faceLandmarks.eyeRightOuter != null)
				face.addFaceLandmarks(new FaceLandmark(LandmarkType.eyeLeftOuter, faceAux.faceLandmarks.eyeRightOuter.x,
						faceAux.faceLandmarks.eyeRightOuter.y));
			if (faceAux.faceLandmarks.eyeRightTop != null)
				face.addFaceLandmarks(new FaceLandmark(LandmarkType.eyeLeftTop, faceAux.faceLandmarks.eyeRightTop.x,
						faceAux.faceLandmarks.eyeRightTop.y));

			if (faceAux.faceLandmarks.mouthLeft != null)
				face.addFaceLandmarks(new FaceLandmark(LandmarkType.mouthRight, faceAux.faceLandmarks.mouthLeft.x,
						faceAux.faceLandmarks.mouthLeft.y));
			if (faceAux.faceLandmarks.mouthRight != null)
				face.addFaceLandmarks(new FaceLandmark(LandmarkType.mouthLeft, faceAux.faceLandmarks.mouthRight.x,
						faceAux.faceLandmarks.mouthRight.y));

			if (faceAux.faceLandmarks.noseLeftAlarOutTip != null)
				face.addFaceLandmarks(new FaceLandmark(LandmarkType.noseRightAlarOutTip,
						faceAux.faceLandmarks.noseLeftAlarOutTip.x, faceAux.faceLandmarks.noseLeftAlarOutTip.y));
			if (faceAux.faceLandmarks.noseLeftAlarTop != null)
				face.addFaceLandmarks(new FaceLandmark(LandmarkType.noseRightAlarTop,
						faceAux.faceLandmarks.noseLeftAlarTop.x, faceAux.faceLandmarks.noseLeftAlarTop.y));
			if (faceAux.faceLandmarks.noseRightAlarOutTip != null)
				face.addFaceLandmarks(new FaceLandmark(LandmarkType.noseLeftAlarOutTip,
						faceAux.faceLandmarks.noseRightAlarOutTip.x, faceAux.faceLandmarks.noseRightAlarOutTip.y));
			if (faceAux.faceLandmarks.noseRightAlarTop != null)
				face.addFaceLandmarks(new FaceLandmark(LandmarkType.noseLeftAlarTop,
						faceAux.faceLandmarks.noseRightAlarTop.x, faceAux.faceLandmarks.noseRightAlarTop.y));
			if (faceAux.faceLandmarks.noseRootLeft != null)
				face.addFaceLandmarks(new FaceLandmark(LandmarkType.noseRootRight, faceAux.faceLandmarks.noseRootLeft.x,
						faceAux.faceLandmarks.noseRootLeft.y));
			if (faceAux.faceLandmarks.noseRootRight != null)
				face.addFaceLandmarks(new FaceLandmark(LandmarkType.noseRootLeft, faceAux.faceLandmarks.noseRootRight.x,
						faceAux.faceLandmarks.noseRootRight.y));
			if (faceAux.faceLandmarks.noseTip != null)
				face.addFaceLandmarks(new FaceLandmark(LandmarkType.noseTip, faceAux.faceLandmarks.noseTip.x,
						faceAux.faceLandmarks.noseTip.y));

			if (faceAux.faceLandmarks.pupilLeft != null)
				face.addFaceLandmarks(new FaceLandmark(LandmarkType.pupilRight, faceAux.faceLandmarks.pupilLeft.x,
						faceAux.faceLandmarks.pupilLeft.y));
			if (faceAux.faceLandmarks.pupilRight != null)
				face.addFaceLandmarks(new FaceLandmark(LandmarkType.pupilLeft, faceAux.faceLandmarks.pupilRight.x,
						faceAux.faceLandmarks.pupilRight.y));

			if (faceAux.faceLandmarks.underLipBottom != null)
				face.addFaceLandmarks(new FaceLandmark(LandmarkType.underLipBottom, faceAux.faceLandmarks.underLipBottom.x,
						faceAux.faceLandmarks.underLipBottom.y));
			if (faceAux.faceLandmarks.underLipTop != null)
				face.addFaceLandmarks(new FaceLandmark(LandmarkType.underLipTop, faceAux.faceLandmarks.underLipTop.x,
						faceAux.faceLandmarks.underLipTop.y));
			if (faceAux.faceLandmarks.upperLipBottom != null)
				face.addFaceLandmarks(new FaceLandmark(LandmarkType.upperLipBottom, faceAux.faceLandmarks.upperLipBottom.x,
						faceAux.faceLandmarks.upperLipBottom.y));
			if (faceAux.faceLandmarks.upperLipTop != null)
				face.addFaceLandmarks(new FaceLandmark(LandmarkType.upperLipTop, faceAux.faceLandmarks.upperLipTop.x,
						faceAux.faceLandmarks.upperLipTop.y));
		}
		
		if(faceAux.faceAttributes!=null){
			if(isAgeParam() && faceAux.faceAttributes.age>0)
				face.setAge((int)faceAux.faceAttributes.age);
			if(isFacialHairParam() && faceAux.faceAttributes.facialHair!=null){
				face.addFaceAttributes(new FaceAttribute("beard",faceAux.faceAttributes.facialHair.beard));
				face.addFaceAttributes(new FaceAttribute("moustache",faceAux.faceAttributes.facialHair.moustache));
				face.addFaceAttributes(new FaceAttribute("sideburns",faceAux.faceAttributes.facialHair.sideburns));
			}
			if(isGenderParam() && faceAux.faceAttributes.gender!=null){
				if(faceAux.faceAttributes.gender.equals("male"))
					face.setIsFemaleConfidence(0.0);
				else
					face.setIsFemaleConfidence(1.0);
			}
			if(isHeadPoseParam() && faceAux.faceAttributes.headPose!=null){
				FaceOrientation faceOrientation=new FaceOrientation(faceAux.faceAttributes.headPose.yaw,faceAux.faceAttributes.headPose.roll,faceAux.faceAttributes.headPose.pitch);
				face.setFaceOrientation(faceOrientation);
			}	
			if(isSmileParam() && faceAux.faceAttributes.smile>=0.0){
				face.setIsSmilingConfidence(faceAux.faceAttributes.smile);
			}	
		}
		return face;
	}
}
