package com.emiliano.safdService.planning;

import java.io.File;
import java.util.Arrays;
import java.util.Vector;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.emiliano.fd.FaceDetectionException;
import com.emiliano.fd.FaceDetectionResult;
import com.emiliano.fd.services.FaceDetectionServiceClient;
import com.emiliano.safdService.utils.FaceDetectionFutureTask;

import android.net.Uri;

public class CompoundFaceDetection implements FaceDetectionServiceClient{
	
	private Vector<FaceDetectionServiceClient> faceDetectors;
	private boolean parallelExecution=false;
	
	public CompoundFaceDetection(FaceDetectionServiceClient ... faceDetectors){
		this(false,faceDetectors);
	}
	
	public CompoundFaceDetection(boolean parallelExecution,FaceDetectionServiceClient ... faceDetectors){
		this.faceDetectors=new Vector<FaceDetectionServiceClient>();
		this.faceDetectors.addAll(Arrays.asList(faceDetectors));
		this.parallelExecution=parallelExecution;
	}
	
	public void addFaceDetector(FaceDetectionServiceClient faceDetector){
		this.faceDetectors.add(faceDetector);
	}
	
	@Override
	public FaceDetectionResult execute(File image) throws FaceDetectionException {

		FaceDetectionResult[] results=new FaceDetectionResult[this.faceDetectors.size()];
		
		if(parallelExecution==false){
			for(int i=0;i<faceDetectors.size();i++){
				results[i]=this.faceDetectors.get(i).execute(image);
			}
		}else{
			FaceDetectionFutureTask[] tasks=new FaceDetectionFutureTask[faceDetectors.size()];
			ExecutorService executor = Executors.newFixedThreadPool(2);
	        
			for(int i=0;i<faceDetectors.size();i++){
				tasks[i]=new FaceDetectionFutureTask(faceDetectors.get(i),image);
				executor.execute(tasks[i]);
				
			}
			for(int i=0;i<faceDetectors.size();i++){
				try {
					results[i]=tasks[i].get();
				} catch (InterruptedException e) {
					throw new FaceDetectionException(e);
				} catch (ExecutionException e) {
					throw new FaceDetectionException(e);
				}
			}
		}
		
		return new CompoundFaceDetectionResult(results);
	}

	@Override
	public String getName() {
		return "Compound face detector";
	}

}
