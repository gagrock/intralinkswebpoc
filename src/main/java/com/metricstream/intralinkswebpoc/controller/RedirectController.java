package com.metricstream.intralinkswebpoc.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.oltu.oauth2.client.response.OAuthAuthzResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.OAuthProviderType;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;



public class RedirectController extends HttpServlet {

	private static final long serialVersionUID = -2492946894202615608L;
	
	public static String clientID ="655617654385-dfbg0d4aasth52qhppip6fqmagcf3ttv.apps.googleusercontent.com";
	public static String clientSecret ="BHrgobeZB6dhgNeiKn5X5ny8";
	public static String baseURL = "https://test-api.intralinks.com";
	public static String redirectUrl = "http://localhost:8080/intralinkswebpoc/authredirect";
	public static String driveScope ="https://www.googleapis.com/auth/drive";
	public static String tokenUrl ="https://accounts.google.com/o/oauth2/token";
	public static String AllDocsURL ="https://www.googleapis.com/drive/v2/files";
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	    
		OAuthClient authclient = new OAuthClient(new URLConnectionClient());
		OAuthAuthzResponse oar;
	  	try {
			oar = OAuthAuthzResponse.oauthCodeAuthzResponse(request);
			OAuthClientRequest clientRequest = OAuthClientRequest
					.tokenProvider(OAuthProviderType.GOOGLE)
					.setGrantType(GrantType.AUTHORIZATION_CODE)
					.setCode(oar.getCode())
					.setClientId(clientID)
					.setClientSecret(clientSecret)
					.setRedirectURI(redirectUrl)
					//.setParameter("access_type", "offline")
					//.setParameter("approval_prompt", "force")
					.buildBodyMessage();
			
			OAuthAccessTokenResponse oAuthResponse = authclient.accessToken(clientRequest, OAuth.HttpMethod.POST);
			String appkey = "Bearer " + oAuthResponse.getAccessToken();
			String refreshToken = oAuthResponse.getRefreshToken();
			
			System.out.println("Access Token: "+appkey);
			System.out.println("refreshToken: "+refreshToken);
			
			
			Client client = ClientBuilder.newClient();
			WebTarget target = client.target(AllDocsURL);
			
			Invocation.Builder builder = target.request(MediaType.APPLICATION_JSON_TYPE)
								.header("Authorization", appkey);
			
			
			Response resp = builder.get();
			
			System.out.println(resp.getStatus());
			System.out.println(resp.readEntity(String.class));
			
			
			
			
			
		} catch (OAuthProblemException | OAuthSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		
	}
}
