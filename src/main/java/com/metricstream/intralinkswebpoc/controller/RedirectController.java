package com.metricstream.intralinkswebpoc.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

import com.metricstream.intralinkswebpoc.util.AppConfig;



public class RedirectController extends HttpServlet {


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		AppConfig config = (AppConfig) request.getServletContext().getAttribute("config");
		OAuthClient authclient = new OAuthClient(new URLConnectionClient());
		OAuthAuthzResponse oar;
	  	try {
			oar = OAuthAuthzResponse.oauthCodeAuthzResponse(request);
			OAuthClientRequest clientRequest = OAuthClientRequest
					.tokenProvider(OAuthProviderType.GOOGLE)
					.setGrantType(GrantType.AUTHORIZATION_CODE)
					.setCode(oar.getCode())
					.setClientId(config.getClientId())
					.setClientSecret(config.getClientSecret())
					.setRedirectURI(config.getRedirectUrl())
					.buildBodyMessage();
			
			OAuthAccessTokenResponse oAuthResponse = authclient.accessToken(clientRequest, OAuth.HttpMethod.POST);
			HttpSession session = request.getSession();
			session.setAttribute("accessToken","Bearer " + oAuthResponse.getAccessToken());
			session.setAttribute("refreshToken",oAuthResponse.getRefreshToken());
			
			System.out.println("Access Token: "+request.getAttribute("accessToken"));
			System.out.println("refreshToken: "+request.getAttribute("refreshToken"));
			System.out.println("expiry "+oAuthResponse.getExpiresIn() );
			System.out.println("oAuthToken "+oAuthResponse.getOAuthToken().getRefreshToken());
			
		   request.getRequestDispatcher("files").forward(request, response);
			
		} catch (OAuthProblemException | OAuthSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		
	}
}
