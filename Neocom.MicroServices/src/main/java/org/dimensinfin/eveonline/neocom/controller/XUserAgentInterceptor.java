//	PROJECT:      Neocom.Microservices (NEOC-MS)
//	AUTHORS:      Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:	SpringBoot-MS-Java 1.8.
//	DESCRIPTION:	This is the integration project for all the web server pieces. This is the launcher for
//								the SpringBoot+MicroServices+Angular unified web application.
package org.dimensinfin.eveonline.neocom.controller;

import java.io.IOException;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

// - CLASS IMPLEMENTATION ...................................................................................
@Component
public class XUserAgentInterceptor implements ClientHttpRequestInterceptor {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger	logger	= Logger.getLogger("XUserAgentInterceptor.java");

	// - F I E L D - S E C T I O N ............................................................................
	private String				appName	= "FirstName";

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	@Autowired
	public XUserAgentInterceptor(String setAppName) {
		appName = setAppName;
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {

		HttpHeaders headers = request.getHeaders();
		if (null == appName) appName = "InternalDefault";
		headers.add("X-app-name", appName);
		return execution.execute(request, body);
	}
}

// - UNUSED CODE ............................................................................................
