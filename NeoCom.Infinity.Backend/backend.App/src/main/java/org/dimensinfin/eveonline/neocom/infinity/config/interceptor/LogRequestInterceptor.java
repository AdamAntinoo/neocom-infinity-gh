package org.dimensinfin.eveonline.neocom.infinity.config.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.dimensinfin.eveonline.neocom.infinity.core.NeoComInterceptor;

public class LogRequestInterceptor extends NeoComInterceptor {
	private ObjectMapper mapper = new ObjectMapper();

	@Override
	public boolean preHandle( final HttpServletRequest request, final HttpServletResponse response, final Object handler ) {
		try {
			logger.info( "[REQUEST]> {}", mapper.writeValueAsString( request ) );
		} catch (final JsonProcessingException jsone) {
		}
		return true;
	}
}
