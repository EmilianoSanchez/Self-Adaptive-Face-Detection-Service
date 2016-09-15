package com.emiliano.fd.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.objdetect.CascadeClassifier;

import com.emiliano.fd.Face;
import com.emiliano.fd.FaceDetectionException;
import com.emiliano.fd.FaceDetectionResult;
import com.emiliano.fd.FaceDetectionResultImpl;
import com.emiliano.fd.FaceImpl;
import com.emiliano.fd.FacePosition;
import com.emiliano.fd.R;

import android.content.Context;
import android.util.Log;

public class ViolaJonesOpenCVFaceDetector implements FaceDetectionServiceClient {

	@Override
	public String getName() {
		return "ViolaJonesOpenCVFaceDetector";
	}

	public static final int JAVA_DETECTOR = 0;
	public static final int NATIVE_DETECTOR = 1;
	private CascadeClassifier mJavaDetector;
	private File mCascadeFile;


	public ViolaJonesOpenCVFaceDetector(Context context) {
		this(context, false);
	}

	private Context context;
	
	public ViolaJonesOpenCVFaceDetector(Context context, final boolean nativeDetector) {
		this.context=context;
		if (!OpenCVLoader.initDebug()) {
			Log.d("CV", "Internal OpenCV library not found. Using OpenCV Manager for initialization");
//			OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, context, mLoaderCallback);

		} else {
			Log.d("CV", "OpenCV library found inside package. Using it!");
			loadResources();
		}

	}
	
	private void loadResources(){
		Log.i("CV", "OpenCV loaded successfully");

		try {
			// load cascade file from application resources
			InputStream is = context.getResources().openRawResource(R.raw.lbpcascade_frontalface);
			File cascadeDir = context.getDir("cascade", Context.MODE_PRIVATE);
			mCascadeFile = new File(cascadeDir, "lbpcascade_frontalface.xml");
			FileOutputStream os = new FileOutputStream(mCascadeFile);

			byte[] buffer = new byte[4096];
			int bytesRead;
			while ((bytesRead = is.read(buffer)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
			is.close();
			os.close();

			mJavaDetector = new CascadeClassifier(mCascadeFile.getAbsolutePath());
			if (mJavaDetector.empty()) {
				Log.e("CV", "Failed to load cascade classifier");
				mJavaDetector = null;
			} else
				Log.i("CV", "Loaded cascade classifier from " + mCascadeFile.getAbsolutePath());

			cascadeDir.delete();

		} catch (IOException e) {
			e.printStackTrace();
			Log.e("CV", "Failed to load cascade. Exception thrown: " + e);
		}
	}
	
	public void setParams(float minFaceSize){
		setMinFaceSize(minFaceSize);
	}

	private float mRelativeFaceSize = 0.2f;
	private int mAbsoluteFaceSize = 0;

	public float getMinFaceSize() {
		return mRelativeFaceSize;
	}

	public void setMinFaceSize(float minFaceSize) {
		mRelativeFaceSize = minFaceSize;
		mAbsoluteFaceSize = 0;
	}

	@Override
	public FaceDetectionResult execute(File image) throws FaceDetectionException {
		try{
			Log.i("CV", "IMREAD_GRAYSCALE "+Imgcodecs.IMREAD_GRAYSCALE);
	//		Mat mImage = Imgcodecs.imread(image.getAbsolutePath(),Imgcodecs.IMREAD_GRAYSCALE);
			Mat mImage;
			
			mImage= Imgcodecs.imread(image.getAbsolutePath());
			
			if (mAbsoluteFaceSize == 0) {
				int height = mImage.rows();
				if (Math.round(height * mRelativeFaceSize) > 0) {
					mAbsoluteFaceSize = Math.round(height * mRelativeFaceSize);
				}
			}
	
			MatOfRect facesAux1 = new MatOfRect();
	

				if (mJavaDetector != null){
					Log.i("CV", "mJavaDetector != null ");
			
					mJavaDetector.detectMultiScale(mImage, facesAux1, 1.1, 2, 2, // TODO:
																					// objdetect.CV_HAAR_SCALE_IMAGE
							new Size(mAbsoluteFaceSize, mAbsoluteFaceSize), new Size());
				}
			
			
	//		Log.i("CV", "OpenCV Detected faces "+faces.length);
	//		for(Face face:faces)
	//			Log.i("CV", "OpenCV Detected faces "+face.toString());
//			Log.i("CV", "facesAux2.length "+facesAux2.length);
			Rect[] facesAux2 = facesAux1.toArray();
			Face[] faces = new Face[facesAux2.length];
			for(int i=0;i<facesAux2.length;i++)
				faces[i]=faceToFace(facesAux2[i]);	
			FaceDetectionResultImpl result=new FaceDetectionResultImpl();
			result.setDetectedFaces(faces);
			result.setStringResult(facesAux2.toString());
			return result;
		}catch(Exception e){
			throw new FaceDetectionException(e);
		}
	}

	private Face faceToFace(Rect rect){
		FaceImpl face=new FaceImpl();
		if(rect!=null){
			face.setFacePosition(new FacePosition(rect.x, rect.y, rect.width, rect.height));
		}
		return face;
	}
}
