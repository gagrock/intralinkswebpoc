package com.metricstream.intralinkswebpoc.controller;
import java.io.File;
import java.io.IOException;

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
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;
// ...

import com.metricstream.intralinkswebpoc.util.AppConfig;

@WebServlet("/upload")
public class Upload extends HttpServlet{
	
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
		Client client = ClientBuilder
				   .newBuilder()
				   .register(MultiPartFeature.class)
				   .build();
			ClassLoader classLoader = getClass().getClassLoader();
		    File uploadFile = new File(classLoader.getResource("/app.properties").getFile());
			FileDataBodyPart filePart = new FileDataBodyPart("file",uploadFile );
			FormDataMultiPart formDataMultiPart = new FormDataMultiPart();
	
			FormDataMultiPart multiPart = (FormDataMultiPart)formDataMultiPart
										.field("title", uploadFile.getName())
										.field("description", uploadFile.getName())
										.bodyPart(filePart);
		
		WebTarget target = client.target(endpoint).queryParam("uploadType", "multipart");
		System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
		Invocation.Builder builder = target.request(multiPart.getMediaType())
						    .header("Content-Type", "multipart/related")
						    .header("Content-Length", uploadFile.length())
							.header("Authorization", accessToken);
		Response resp = builder.post(Entity.entity(multiPart, multiPart.getMediaType()));	
		formDataMultiPart.close();
		multiPart.close();
		return resp;
		
	}

	
	
}