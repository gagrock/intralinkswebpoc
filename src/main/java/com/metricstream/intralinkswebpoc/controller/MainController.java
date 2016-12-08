package com.metricstream.intralinkswebpoc.controller;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.common.OAuthProviderType;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

import com.metricstream.intralinkswebpoc.util.AppConfig;

@WebServlet("/login")
public class MainController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		new AppConfig().loadProperties(config);
		
	}
	
    public String buildAuthUri(HttpServletRequest req)
    {
    	AppConfig config = (AppConfig) req.getServletContext().getAttribute("config");
    	OAuthClientRequest request = null;
    	try {
    		
			 request = OAuthClientRequest
					.authorizationProvider(OAuthProviderType.GOOGLE)
					.setClientId(config.getClientId())
					.setResponseType("code")
					.setRedirectURI(config.getRedirectUrl())
					.setScope(config.getScope())	
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
    	String authUri = buildAuthUri(req);
    	System.out.println(authUri);
    	resp.sendRedirect(authUri);
    }
	
	
	
	

}
