package com.metricstream.intralinkswebpoc.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.json.JSONObject;
//java - File upload along with other object in Jersey restful web service - Stack Overflow
public class demo {
	
	
	public static void main(String[] args) throws IOException {
		
		new demo().ok();
		

	}
	
	public void ok() throws IOException{
		File uploadFile = new File("/index.html");
		System.out.println(Files.probeContentType(uploadFile.toPath()));
	}

}
