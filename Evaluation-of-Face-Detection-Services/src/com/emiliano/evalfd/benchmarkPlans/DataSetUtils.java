package com.emiliano.evalfd.benchmarkPlans;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.emiliano.evalfd.FaceDetectionInput;
import com.emiliano.fd.FacePosition;

import android.os.Build;

class DataSetUtils {

	private static String EXTERNAL_STORAGE = "/storage/emulated/0";
	private static String EXTERNAL_STORAGE_C2104 = "/storage/sdcard0";

	private static String[] descriptionFilesFDDB = new String[] { "/FDDB-folds/FDDB-fold-01.txt",
			"/FDDB-folds/FDDB-fold-02.txt", "/FDDB-folds/FDDB-fold-03.txt", "/FDDB-folds/FDDB-fold-04.txt",
			"/FDDB-folds/FDDB-fold-05.txt", "/FDDB-folds/FDDB-fold-06.txt", "/FDDB-folds/FDDB-fold-07.txt",
			"/FDDB-folds/FDDB-fold-08.txt", "/FDDB-folds/FDDB-fold-09.txt", "/FDDB-folds/FDDB-fold-10.txt" };
	private static String[] ellipseListDescriptionFilesFDDB = new String[] { "/FDDB-folds/FDDB-fold-01-ellipseList.txt",
			"/FDDB-folds/FDDB-fold-02-ellipseList.txt", "/FDDB-folds/FDDB-fold-03-ellipseList.txt",
			"/FDDB-folds/FDDB-fold-04-ellipseList.txt", "/FDDB-folds/FDDB-fold-05-ellipseList.txt",
			"/FDDB-folds/FDDB-fold-06-ellipseList.txt", "/FDDB-folds/FDDB-fold-07-ellipseList.txt",
			"/FDDB-folds/FDDB-fold-08-ellipseList.txt", "/FDDB-folds/FDDB-fold-09-ellipseList.txt",
			"/FDDB-folds/FDDB-fold-10-ellipseList.txt" };

	private static String imageFolderFDDB = "/originalPicsFiltered";

	private static String descriptionFileFRGC = "/FRGC-query-images.txt";
	private static String imageFolderFRGC = "/FRGC-query-images";
	
	private static String descriptionFile300W = "/300w-resized/300w-ALL FILES.txt";
	private static String imageFolder300W = "/300w-resized";

	public static List<FaceDetectionInput> getInputsFRGC() {
		List<FaceDetectionInput> list = new LinkedList<FaceDetectionInput>();
		if (Build.MODEL.equals("C2104")) {
			list.addAll(
					DataSetUtils.getInputFRGC(EXTERNAL_STORAGE_C2104 + descriptionFileFRGC, EXTERNAL_STORAGE_C2104 + imageFolderFRGC));
		} else {
			list.addAll(DataSetUtils.getInputFRGC(EXTERNAL_STORAGE + descriptionFileFRGC, EXTERNAL_STORAGE + imageFolderFRGC));
		}
		return list;
	}

	public static List<FaceDetectionInput> getInputFRGC(String descriptionFilePath, String folderPath) {
		List<FaceDetectionInput> result = new LinkedList();
		File file = new File(descriptionFilePath);
		if (file.exists()) {
			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
				String line = reader.readLine();
				while (line != null) {
					FaceDetectionInput input = new FaceDetectionInput(new File(folderPath + "/" + line));
					input.numberOfFaces=1;
					result.add(input);
					line = reader.readLine();
				}
			} catch (FileNotFoundException e) {
				throw new IllegalArgumentException("File " + descriptionFilePath + " do not exist");
			} catch (IOException e) {
				throw new IllegalArgumentException(e);
			}
		} else
			throw new IllegalArgumentException("File " + descriptionFilePath + " do not exist");
		return result;
	};

	public static List<FaceDetectionInput> getInputsFDDB() {
		List<FaceDetectionInput> list = new LinkedList<FaceDetectionInput>();
		if (Build.MODEL.equals("C2104")) {
			for (String path : descriptionFilesFDDB)
				list.addAll(DataSetUtils.getInputFDDB(EXTERNAL_STORAGE_C2104 + path,
						EXTERNAL_STORAGE_C2104 + imageFolderFDDB));
		} else {
			for (String path : descriptionFilesFDDB)
				list.addAll(DataSetUtils.getInputFDDB(EXTERNAL_STORAGE + path, EXTERNAL_STORAGE + imageFolderFDDB));
		}
		return list;
	};

	public static List<FaceDetectionInput> getInputsFDDBFromEllipseList() {
		List<FaceDetectionInput> list = new LinkedList<FaceDetectionInput>();
		if (Build.MODEL.equals("C2104")) {
			for (String path : ellipseListDescriptionFilesFDDB)
				list.addAll(DataSetUtils.getInputFDDBFromEllipseList(EXTERNAL_STORAGE_C2104 + path,
						EXTERNAL_STORAGE_C2104 + imageFolderFDDB));
		} else {
			for (String path : ellipseListDescriptionFilesFDDB)
				list.addAll(DataSetUtils.getInputFDDBFromEllipseList(EXTERNAL_STORAGE + path,
						EXTERNAL_STORAGE + imageFolderFDDB));
		}
		return list;
	};

	private static List<FaceDetectionInput> getInputFDDBFromEllipseList(String fddbFilePath, String folder) {
		List<FaceDetectionInput> result = new LinkedList();
		File file = new File(fddbFilePath);
		if (file.exists()) {
			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
				String line = reader.readLine();
				String auxLine;
				while (line != null) {
					auxLine = reader.readLine();
					int realFaces = Integer.parseInt(auxLine);
					FacePosition[] facePositions = new FacePosition[realFaces];
					for (int i = 0; i < realFaces; i++) {
						auxLine = reader.readLine();
						String[] splited = auxLine.split(" ");
						double major_axis_radius = Double.parseDouble(splited[0]);
						double minor_axis_radius = Double.parseDouble(splited[1]);
						double angle = Double.parseDouble(splited[2]);
						double center_x = Double.parseDouble(splited[3]);
						double center_y = Double.parseDouble(splited[4]);
						facePositions[i] = new FacePosition(center_x - minor_axis_radius, center_y - major_axis_radius,
								minor_axis_radius * 2.0, major_axis_radius * 2.0);
					}

					FaceDetectionInput input = new FaceDetectionInput(new File(folder + "/" + line + ".jpg"),
							facePositions);
					result.add(input);
					line = reader.readLine();
				}
			} catch (FileNotFoundException e) {
				throw new IllegalArgumentException("File " + fddbFilePath + " do not exist");
			} catch (IOException e) {
				throw new IllegalArgumentException(e);
			}
		} else
			throw new IllegalArgumentException("File " + fddbFilePath + " do not exist");
		return result;
	}

	public static List<FaceDetectionInput> getInputFDDB(String fddbFilePath, String folder) {
		List<FaceDetectionInput> result = new LinkedList();
		File file = new File(fddbFilePath);
		if (file.exists()) {
			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
				String line = reader.readLine();
				while (line != null) {
					FaceDetectionInput input = new FaceDetectionInput(new File(folder + "/" + line + ".jpg"));
					result.add(input);
					line = reader.readLine();
				}
			} catch (FileNotFoundException e) {
				throw new IllegalArgumentException("File " + fddbFilePath + " do not exist");
			} catch (IOException e) {
				throw new IllegalArgumentException(e);
			}
		} else
			throw new IllegalArgumentException("File " + fddbFilePath + " do not exist");
		return result;
	};
	
	public static List<FaceDetectionInput> getInputs300W() {
		List<FaceDetectionInput> list = new LinkedList<FaceDetectionInput>();
		if (Build.MODEL.equals("C2104")) {
			list.addAll(
					DataSetUtils.getInputFRGC(EXTERNAL_STORAGE_C2104 + descriptionFile300W, EXTERNAL_STORAGE_C2104 + imageFolder300W));
		} else {
			list.addAll(DataSetUtils.getInputFRGC(EXTERNAL_STORAGE + descriptionFile300W, EXTERNAL_STORAGE + imageFolder300W));
		}
		return list;
	}
	
	public static List<FaceDetectionInput> getInputs300W(String descriptionFilePath, String folderPath) {
		List<FaceDetectionInput> result = new LinkedList();
		File file = new File(descriptionFilePath);
		if (file.exists()) {
			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
				String line = reader.readLine();
				while (line != null) {
					FaceDetectionInput input = new FaceDetectionInput(new File(folderPath + "/" + line));
					input.numberOfFaces=1;
					result.add(input);
					line = reader.readLine();
				}
			} catch (FileNotFoundException e) {
				throw new IllegalArgumentException("File " + descriptionFilePath + " do not exist");
			} catch (IOException e) {
				throw new IllegalArgumentException(e);
			}
		} else
			throw new IllegalArgumentException("File " + descriptionFilePath + " do not exist");
		return result;
	};

	public static List<FaceDetectionInput> getInputFDDB_FlattenFolder(String fddbFilePath) {
		String folder = fddbFilePath.substring(0, fddbFilePath.lastIndexOf("/"));
		return getInputFDDB(fddbFilePath, folder);
	};

	public static List<FaceDetectionInput> getInputFDDBFromEllipseList_FlattenFolder(String fddbFilePath) {
		String folder = fddbFilePath.substring(0, fddbFilePath.lastIndexOf("/"));
		return getInputFDDBFromEllipseList(fddbFilePath, folder);
	}

}
