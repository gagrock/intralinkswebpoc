package com.metricstream.intralinkswebpoc.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.common.OAuthProviderType;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

@WebServlet("/main")
public class MainController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static String clientID ="655617654385-dfbg0d4aasth52qhppip6fqmagcf3ttv.apps.googleusercontent.com";
	public static String clientSecret ="BHrgobeZB6dhgNeiKn5X5ny8";
	public static String baseURL = "https://test-api.intralinks.com";
	public static String redirectUrl = "http://localhost:8080/intralinkswebpoc/authredirect";
	public static String driveScope ="https://www.googleapis.com/auth/drive";
	
    public String initialize()
    {
    	OAuthClientRequest request = null;
    	try {
    		
			 request = OAuthClientRequest
					.authorizationProvider(OAuthProviderType.GOOGLE)
					.setClientId(clientID)
					.setResponseType("code")
					.setRedirectURI(redirectUrl)
					.setScope(driveScope)
					
					.buildQueryMessage();
			
			
			
			
		} catch (OAuthSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	return 	request.getLocationUri();	
    	
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
    	String authUrl = initialize();
    	System.out.println(authUrl);
    	resp.sendRedirect(authUrl);
    }
	
	
	
	

}
