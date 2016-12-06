package com.metricstream.intralinkswebpoc.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class AppConfig {
	
	private String clientId;
	private String clientSecret;
	private String accessToken;
	private String refreshToken;
	private final String fileName ="app.properties";
	
   public Properties  loadProperties(){
	   InputStream inStream = null;
	   Properties prop = new Properties(); 
	   
	   try{
		   inStream = new FileInputStream(fileName);	
		   
		   prop.load(inStream);
			} catch (Exception e) {
				e.printStackTrace();
				
			}
	   
	   
	   return prop;
   }	


}
