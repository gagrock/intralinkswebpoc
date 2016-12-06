package com.metricstream.intralinkswebpoc.controller;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest.OAuthRequestBuilder;
import org.apache.oltu.oauth2.client.response.GitHubTokenResponse;
import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.oltu.oauth2.client.response.OAuthAuthzResponse;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.common.OAuthProviderType;
import org.apache.oltu.oauth2.common.error.OAuthError.TokenResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;


public class RedirectController extends HttpServlet {

	private static final long serialVersionUID = -2492946894202615608L;
	
	public static String clientID ="655617654385-dfbg0d4aasth52qhppip6fqmagcf3ttv.apps.googleusercontent.com";
	public static String clientSecret ="BHrgobeZB6dhgNeiKn5X5ny8";
	public static String baseURL = "https://test-api.intralinks.com";
	public static String redirectUrl = "http://localhost:8080/intralinkswebpoc/authredirect";
	public static String driveScope ="https://www.googleapis.com/auth/drive";
	public static String tokenUrl ="https://accounts.google.com/o/oauth2/token";
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	    
		try {
			OAuthClient client = new OAuthClient(new URLConnectionClient());
			OAuthAuthzResponse oar = OAuthAuthzResponse.oauthCodeAuthzResponse(request);
		
			OAuthClientRequest authReq = OAuthClientRequest
						.tokenProvider(OAuthProviderType.GOOGLE)
						.setParameter("access_token", "")
						.setCode(oar.getCode())
						
						.buildQueryMessage();
			
			System.out.println(authReq.getLocationUri());
			System.out.println(authReq.getBody());
		    Map<String, String> headers = authReq.getHeaders();
		    for (Map.Entry<String, String> entry : headers.entrySet())
		    {
		        System.out.println(entry.getKey() + " :" + entry.getValue() +" \n");
		    }
			
		  String accessToken = client
				  .accessToken(authReq, GitHubTokenResponse.class)
				  .getAccessToken();  
		  System.out.println("Access Token "+accessToken);
			
		} catch (OAuthSystemException e) {
			// TODO Auto-generated catch block
			System.err.println(e.getMessage());
		} catch (OAuthProblemException e) {
			// TODO Auto-generated catch block
			System.err.println(e.getMessage());
		}
	
	}
}
