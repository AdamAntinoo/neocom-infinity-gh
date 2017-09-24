//	PROJECT:      Neocom.Microservices (NEOC-MS)
//	AUTHORS:      Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:	SpringBoot-MS-Java 1.8.
//	DESCRIPTION:	This is the integration project for all the web server pieces. This is the launcher for
//								the SpringBoot+MicroServices+Angular unified web application.
package org.dimensinfin.eveonline.neocom.services;

import java.io.IOException;

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
	//	private static Logger	logger	= Logger.getLogger("XUserAgentInterceptor");

	// - F I E L D - S E C T I O N ............................................................................
	private String appName = "NeoComMicroServiceApplication";

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	@Autowired
	public XUserAgentInterceptor(final String setAppName) {
		//	appName = setAppName;
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	@Override
	public ClientHttpResponse intercept(final HttpRequest request, final byte[] body,
			final ClientHttpRequestExecution execution) throws IOException {

		HttpHeaders headers = request.getHeaders();
		if (null == appName) appName = "InternalDefault";
		headers.add("X-app-name", appName);
		return execution.execute(request, body);
	}
}

// - UNUSED CODE ............................................................................................
