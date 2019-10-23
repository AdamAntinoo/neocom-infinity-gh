package org.dimensinfin.eveonline.neocom.infinity.config.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.dimensinfin.eveonline.neocom.infinity.core.NeoComInterceptor;

public class LogResponseInterceptor extends NeoComInterceptor {
	private ObjectMapper mapper = new ObjectMapper();

	@Override
	public void afterCompletion( final HttpServletRequest request, final HttpServletResponse response, final Object handler,
	                             final Exception ex ) {
		try {
			if (null != response) logger.info( "[REQUEST]> {}", mapper.writeValueAsString( response ) );
		} catch (final JsonProcessingException jsone) {
		}
	}
}
