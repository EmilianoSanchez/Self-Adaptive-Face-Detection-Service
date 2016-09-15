package com.emiliano.evalfd;

import java.io.File;

import com.emiliano.fd.FacePosition;

public class FaceDetectionInput {
	public File file;
	public int numberOfFaces;
	public FacePosition[] realPositions;

	public FaceDetectionInput(File file) {
		this(file, 0);
	}

	public FaceDetectionInput(File file, int numberOfFaces) {
		this.file = file;
		this.numberOfFaces = numberOfFaces;
	}

	public FaceDetectionInput(File file, FacePosition... realPositions) {
		this.file = file;
		this.numberOfFaces = realPositions.length;
		this.realPositions = realPositions;
	}

	@Override
	public String toString() {
		return this.file.getPath();
	}
}
