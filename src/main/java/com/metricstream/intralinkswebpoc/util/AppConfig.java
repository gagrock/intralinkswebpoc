package com.metricstream.intralinkswebpoc.util;

import java.util.Properties;

import javax.servlet.ServletConfig;

import com.fasterxml.jackson.core.util.VersionUtil;

/**
 * @author irshad.sheikh
 *
 */
public class AppConfig {

	private final String fileName ="/app.properties";
	
	private String clientId;
	private String clientSecret;
	private String redirectUrl;
	private String scope;
	private String filesEndPoint;
	private String uploadEndPoint;
	
	
   public void  loadProperties(ServletConfig config){
	  
	   Properties prop = new Properties();    
	   try{
		    prop.load(VersionUtil.class.getResourceAsStream(fileName));
		    this.setClientId(prop.getProperty("clientId"));
		    this.setClientSecret(prop.getProperty("clientSecret"));
		    this.setRedirectUrl(prop.getProperty("redirectUrl"));
		    this.setScope(prop.getProperty("scope"));
		    this.setFilesEndPoint(prop.getProperty("filesUrl"));
		    this.setUploadEndPoint(prop.getProperty("uploadUrl"));
		    
		    config.getServletContext().setAttribute("config", this);
		 } catch (Exception e) {
			 e.printStackTrace();
		}
	  
    }
   	
      
		public String getUploadEndPoint() {
			return uploadEndPoint;
		}
		
		
		private void setUploadEndPoint(String uploadEndPoint) {
			this.uploadEndPoint = uploadEndPoint;
		}


		public String getFilesEndPoint() {
			return filesEndPoint;
		}
		
		
		private void setFilesEndPoint(String filesEndPoint) {
			this.filesEndPoint = filesEndPoint;
		}
		



		public String getScope() {
			return scope;
		}


		private void setScope(String scope) {
			this.scope = scope;
		}


		private void setClientId(String clientId) {
			this.clientId = clientId;
		}
		
		
		private void setClientSecret(String clientSecret) {
			this.clientSecret = clientSecret;
		}
		
			

		public String getRedirectUrl() {
			return redirectUrl;
		}


		private void setRedirectUrl(String redirectUrl) {
			this.redirectUrl = redirectUrl;
		}


		public String getClientId() {
			return clientId;
		}


		public String getClientSecret() {
			return clientSecret;
		}


		
      
   
   
   
}
