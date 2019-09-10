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

import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Adam Antinoo
 */
// - CLASS IMPLEMENTATION ...................................................................................
public class HeaderVerifierInterceptor implements HandlerInterceptor {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger logger = LoggerFactory.getLogger("HeaderVerifierInterceptor");

	// - F I E L D - S E C T I O N ............................................................................

	// - C O N S T R U C T O R - S E C T I O N ................................................................

	// - M E T H O D - S E C T I O N ..........................................................................
	@Override
	public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) {
		// Filter the requests with the OPTIONS calls because they should not be verified
		String meth = request.getMethod();
		if ( meth.equalsIgnoreCase("OPTIONS") ) return true;
		// Check thet the mandatory headers are present. If not then reject the request.
		final Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			final String header = headerNames.nextElement();
			final String headerValue = request.getHeader(header);
			if ( header.equalsIgnoreCase("xApp-Signature") ) {
				// Check that is one of the allowed signatures.
				if ( headerValue.equalsIgnoreCase("S0000.0011.0000") ) return true;
			}
		}
		response.setStatus(HttpServletResponse.SC_PRECONDITION_FAILED);
		return false;
	}

	@Override
	public void postHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler, final ModelAndView modelAndView) {

	}

	@Override
	public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response, final Object handler, final Exception ex) {

	}
}

// - UNUSED CODE ............................................................................................
//[01]
