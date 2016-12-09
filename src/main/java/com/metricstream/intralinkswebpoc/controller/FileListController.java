package com.metricstream.intralinkswebpoc.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Response;

import com.metricstream.intralinkswebpoc.util.AppConfig;
import com.metricstream.intralinkswebpoc.util.RestClient;

@WebServlet("/files")
public class FileListController  extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println(" in the Files serlvet");
		HttpSession session = request.getSession(false);
		
		if(session != null){
			AppConfig config = (AppConfig) request.getServletContext().getAttribute("config");
			RestClient client = new RestClient();
			Response resp =client.get((String)session.getAttribute("accessToken"), config.getFilesEndPoint());
			
			
			System.out.println(resp.getStatus());
			System.out.println(resp.readEntity(String.class));
		}
		else{
			System.out.println("user has logged out.. probabaly would redirect to login page");
			System.out.println("for now redirecting to main");
			response.sendRedirect("main");
		}
		
	}
	
}
