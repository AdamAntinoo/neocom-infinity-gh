package org.dimensinfin.eveonline.neocom.infinity.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

public abstract class NeoComInterceptor implements HandlerInterceptor {
	public static Logger logger = LoggerFactory.getLogger( NeoComInterceptor.class );
}