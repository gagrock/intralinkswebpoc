package com.metricstream.intralinkswebpoc.util;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class RestClient {
	
	private final Client client = ClientBuilder.newClient();
	
	public Response get(String accessToken,String endpoint)
	{
		WebTarget target = client.target(endpoint);
		
		Invocation.Builder builder = target.request(MediaType.APPLICATION_JSON_TYPE)
							.header("Authorization",accessToken);
		return builder.get();

		
	}

}
