package org.dimensinfin.eveonline.neocom.infinity.config.interceptor;

import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

public class LogRequestHeaderInterceptor implements HandlerInterceptor {
	private static Logger logger = LoggerFactory.getLogger( "LogRequestHeaderInterceptor" );

	@Override
	public boolean preHandle( final HttpServletRequest request, final HttpServletResponse response, final Object handler ) {
		// Filter the requests with the OPTIONS calls because they should not be verified
		String meth = request.getMethod();
		if (meth.equalsIgnoreCase( "OPTIONS" )) return true;
		// Check that the mandatory headers are present. If not then reject the request.
		final Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			final String header = headerNames.nextElement();
			final String headerValue = request.getHeader( header );
			logger.info( "AP [VERIFICATION   ] > {}={}", header, headerValue );
//			if ( header.equalsIgnoreCase("xApp-Signature") ) {
//				// Check that is one of the allowed signatures.
//				if ( headerValue.equalsIgnoreCase("S0000.0011.0000") ) return true;
//				if ( headerValue.equalsIgnoreCase("S0000.0100.0000") ) return true;
//			}
		}
//		logger.info("AP [VERIFICATION   ] > Failure to validate request. {}", HttpServletResponse.SC_PRECONDITION_FAILED);
//		response.setStatus(HttpServletResponse.SC_PRECONDITION_FAILED);
		return true;
	}
}
