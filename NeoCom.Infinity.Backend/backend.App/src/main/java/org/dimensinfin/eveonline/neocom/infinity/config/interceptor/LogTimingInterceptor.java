package org.dimensinfin.eveonline.neocom.infinity.config.interceptor;

import java.time.Duration;
import java.time.Instant;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dimensinfin.eveonline.neocom.infinity.core.NeoComInterceptor;

public class LogTimingInterceptor extends NeoComInterceptor {
	private Instant timer;

	@Override
	public boolean preHandle( final HttpServletRequest request, final HttpServletResponse response, final Object handler ) {
		timer = Instant.now();
		return true;
	}

	@Override
	public void afterCompletion( final HttpServletRequest request, final HttpServletResponse response, final Object handler, final Exception ex ) {
		logger.info( "[TIMING]> {}", Duration.between( this.timer, Instant.now() ).toMillis() + "ms" );
	}
}
