package org.dimensinfin.eveonline.neocom.infinity.config;

import java.io.IOException;
import java.util.UUID;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.slf4j.MDC;

@Component
public class LogMDCFilter extends OncePerRequestFilter {
	public static Logger logger = LoggerFactory.getLogger("LogMDCFilter");
	private static final String mdcRequestIdKey = "MDC.REQUEST-ID";
	private static final String mdcEntryPointKey = "MDC.ENTRY-POINT";
	private static final String mdcApplicationCodeKey = "MDC.APP-IDENTIFIER";
	private static final String mdcAgentIdentifierKey = "MDC.AGENT-IDENTIFIER";

	@Override
	protected void doFilterInternal( final HttpServletRequest request, final HttpServletResponse response,
	                                 final FilterChain filterChain ) throws ServletException, IOException {
//		logger.info("[TRACING][LogMDCFilter.doFilterInternal]");
		try {
			// Read all request headers and extract the data we need for the logs.
			// From Heroku get the request unique identifier.
			if ( request instanceof HttpServletRequest ) {
				// Configure the request unique identifier
				String requestId = ((HttpServletRequest) request).getHeader("X-Request-ID");
				if ( requestId != null ) {
					logger.info("[TRACING][LogMDCFilter.doFilterInternal]> Found Heroku RUID: {}", requestId);
					MDC.put(mdcRequestIdKey, requestId);
				} else {
					requestId = UUID.randomUUID().toString().toUpperCase().replace("-", "");
					logger.info("[TRACING][LogMDCFilter.doFilterInternal]> Created new RUID: {}", requestId);
					MDC.put(mdcRequestIdKey, requestId);
				}
				response.addHeader("xApp-Request-ID", requestId);

				// Configure the entry point name.
				final String requestPath = request.getRequestURI();
				MDC.put(mdcEntryPointKey, requestPath);

				// Configure the application code identifier.
				String appCode = ((HttpServletRequest) request).getHeader("xapp-name");
				if ( appCode != null ) {
					MDC.put(mdcApplicationCodeKey, appCode);
				}

				// Configure the agent identifier.
//				final String headerValue = request.getHeader("xApp-Authentication");
//				if ( null != headerValue ) {
//					// Search for the local session and check it is still valid.
//					AppSession session = CitasBackendApplication.getSessionManager().retrieve(headerValue);
//					if ( null != session ) {
//						final String agentIdentifier = (String) session.getUserIdentifier();
//						MDC.put(mdcAgentIdentifierKey, agentIdentifier);
//					}
//				}
			}
			filterChain.doFilter(request, response);
		} finally {
			MDC.clear();
		}
	}
}