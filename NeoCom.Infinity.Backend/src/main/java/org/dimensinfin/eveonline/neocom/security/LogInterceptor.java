//  PROJECT:     CitaMed.backend (CMBK.SB)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2018, 2019 by Endless Dimensions Ltd., all rights reserved.
//  ENVIRONMENT: SpringBoot 2.0.
//  SITE:        citasmedico.com
//  DESCRIPTION: CitasMedico. Sistema S1. Aplicacion SpringBoot adaptada para Heroku y diseñada con arquitectura
//               JPA como sistema de persistencia. Se configura para utilizar una base de datos PostgreSQL
//               y dotado de un modelo de acceso RESTful para poder exportar el api y el modelo para acceso
//               desde aplicaciones dispares, como Angular 6 o Android.
package org.dimensinfin.eveonline.neocom.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.dimensinfin.core.util.Chrono;

/**
 * @author Adam Antinoo
 */
// - CLASS IMPLEMENTATION ...................................................................................
public class LogInterceptor implements HandlerInterceptor {
	// - S T A T I C - S E C T I O N ..........................................................................
	public static Logger logger = LoggerFactory.getLogger("LogInterceptor");

	// - F I E L D - S E C T I O N ............................................................................
	private String requestPath;
	private Chrono timer;

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public LogInterceptor() {
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	@Override
	public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) {
		requestPath = request.getRequestURI();
		timer = new Chrono();
		logger.info(">>>>>>>>>> [{}]", requestPath);
		return true;
	}

	@Override
	public void postHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler, final ModelAndView modelAndView) {

	}

	@Override
	public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response, final Object handler, final Exception ex) {
		logger.info("<<<<<<<<<< [{}]> TIMING: {}", requestPath, timer.printElapsed(Chrono.ChronoOptions.SHOWMILLIS));
	}
}

// - UNUSED CODE ............................................................................................
//[01]
