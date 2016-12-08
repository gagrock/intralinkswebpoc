package com.metricstream.intralinkswebpoc.controller;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;
// ...
import org.json.JSONObject;

import com.metricstream.intralinkswebpoc.util.AppConfig;

@WebServlet("/upload")
public class Upload extends HttpServlet{
	Logger logger = Logger.getLogger(getClass().getName());
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("upload servlet ");
		HttpSession session = request.getSession(false); 
		if(session != null){
			AppConfig config = (AppConfig) request.getServletContext().getAttribute("config");
			System.out.println(session.getAttribute("accessToken"));
			System.out.println(config.getUploadEndPoint());
			Response resp = upload((String)session.getAttribute("accessToken"), config.getUploadEndPoint());
			System.out.println(resp.getStatus());
			System.out.println(resp.readEntity(String.class));
		}
		else{
			System.out.println("user has logged out.. probabaly would redirect to login page");
			System.out.println("for now redirecting to main");
			response.sendRedirect("main");
		}
	   
		
		
	}

	
	public Response upload(String accessToken,String endpoint) throws IOException
	{
		System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
	

		Feature feature = new LoggingFeature(logger, Level.INFO, null, null);
		Client client = ClientBuilder
				   .newBuilder()
				   .register(MultiPartFeature.class)
				   .register(feature)
				   .build();
		
		WebTarget target = client.target(endpoint).queryParam("uploadType", "multipart");
		JSONObject obj = new JSONObject();
		obj.put("name", "app.properties");
		obj.put("originalFilename", "app.properties");
	   
		MultiPart multiPart = new MultiPart();
		multiPart.bodyPart(obj, MediaType.APPLICATION_JSON_TYPE);
		File uploadFile = new File(getClass().getClassLoader().getResource("/app.properties").getFile());
		FileDataBodyPart fbdPart = new FileDataBodyPart(uploadFile.getName(), uploadFile, MediaType.TEXT_HTML_TYPE);
		fbdPart.setName("app.txt");
	
		multiPart.bodyPart(fbdPart);
		
		
		Invocation.Builder builder = target.request(MediaType.APPLICATION_JSON_TYPE);
								.header("Content-Type", "multipart/related")
							    .header("Content-Length", uploadFile.length())
								.header("Authorization", accessToken);
								Response resp = builder.post(Entity.entity(multiPart, multiPart.getMediaType()));
															
		
		
		
		return resp;
			
		
	}

	
	
}