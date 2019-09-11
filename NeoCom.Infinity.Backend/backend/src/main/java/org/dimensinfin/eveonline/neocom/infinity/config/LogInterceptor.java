package org.dimensinfin.eveonline.neocom.infinity.config;

import java.time.Duration;
import java.time.Instant;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author Adam Antinoo
 */
public class LogInterceptor implements HandlerInterceptor {
	public static Logger logger = LoggerFactory.getLogger(LogInterceptor.class);

	//    private ConfigurationProviderComponent configurationProvider;
	private String requestPath;
	private Instant timer;

//    public LogInterceptor(final ConfigurationProviderComponent configurationProvider) {
//        this.configurationProvider = configurationProvider;
//    }

	@Override
	public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) {
		requestPath = request.getRequestURI();
		timer = Instant.now();
//        if (this.configurationProvider.getResourceBoolean("P.runtime.logging.request.headers")) {
		if (false) {
			// Dump to logger almost all headers.
			final Enumeration<String> headerNames = request.getHeaderNames();
			while (headerNames.hasMoreElements()) {
				final String header = headerNames.nextElement();
				final String headerValue = request.getHeader(header);
				if (header.equalsIgnoreCase("xapp-authentication")) continue;
				logger.info("[REQUEST]> HeaderData: {}={}", header, headerValue);
			}
		} else logger.info("[REQUEST]> Configuration requests to skip headers.");
		return true;
	}

	@Override
	public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response, final Object handler, final Exception ex) {
		logger.info("[TIMING]> {}", Duration.between(this.timer, Instant.now()).toMillis() + "ms");
	}
}
