package com.emiliano.fd.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import android.content.Context;
import android.net.Uri;

public class ResourceHelper {

	public static String getStringFromRawResource(Context context,int id) {
		InputStream is = context.getResources().openRawResource(id);
		Writer writer = new StringWriter();
		char[] buffer = new char[1024];
		try {
			Reader reader = new BufferedReader(new InputStreamReader(is,
					"UTF-8"));
			int n;
			while ((n = reader.read(buffer)) != -1) {
				writer.write(buffer, 0, n);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	
		String string = writer.toString();
		return string;
	}
	
	public static File uriToFile(Uri uri){
		return new File(uri.getPath());
	}
	
	public static Uri fileToUri(File file){
		return Uri.fromFile(file);
	}
}
