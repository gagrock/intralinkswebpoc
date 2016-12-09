package com.metricstream.intralinkswebpoc.filter;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;

public class LogginFilter implements ClientRequestFilter{

	private static final Logger LOG = Logger.getLogger(LogginFilter.class.getName());
	@Override
	public void filter(ClientRequestContext requestContext) throws IOException {
		LOG.log(Level.INFO, requestContext.getEntity().toString());
		
	}

}
