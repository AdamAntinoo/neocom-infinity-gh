package org.dimensinfin.eveonline.neocom.infinity.config.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.dimensinfin.eveonline.neocom.infinity.core.NeoComInterceptor;

public class HeaderResponseInterceptor extends NeoComInterceptor {
	private ObjectMapper mapper = new ObjectMapper();

	@Override
	public void afterCompletion( final HttpServletRequest request, final HttpServletResponse response, final Object handler,
	                             final Exception ex ) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Headers", "Accept, Origin, X-Requested-With, Content-Type, Authorization, " +
				"xapp-name, xapp-platform, xapp-version");
//		response.addHeader("Access-Control-Allow-Credentials", "true");
		response.addHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS,HEAD");
	}
}
