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

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * This implements my own security verification mechanism.
 * During the login phase we should receive a Base64 encoded Basic authorization header with the user unique identifier (NIF) and
 * the entered password. Then we check against the database backend if the user is defined and the password matches. If validated
 * then we return anauthorized credential with a salted session unique identifier that will leave the user information in local.
 * This unique identifier hides the user/pass information that will never travel again and serves as the pointer to the local copy
 * of the session information.
 * After the session information there should be a header named 'xApp-Authentication' that contains this unique identifier. The with
 * this interceptor we have to check that the pointed session is still valid, that the decoded contents match the local session
 * registered contentns and that the user is still enabled at the database. If all it is OK then we can authorise the request and we
 * pass it to next interceptor.
 * @author Adam Antinoo
 */
// - CLASS IMPLEMENTATION ...................................................................................
@Component
public class SecurityInterceptor implements HandlerInterceptor {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger logger = LoggerFactory.getLogger("HeaderVerifierInterceptor");

	// - F I E L D - S E C T I O N ............................................................................
//	@Autowired
//	protected SeedDataCreationService creationService;

	// - C O N S T R U C T O R - S E C T I O N ................................................................

	// - M E T H O D - S E C T I O N ..........................................................................
	@Override
	public boolean preHandle( final HttpServletRequest request, final HttpServletResponse response, final Object handler ) {
		logger.info(">> [SecurityInterceptor.preHandle]");
//		if ( creationService == null ) {
//			ServletContext servletContext = request.getServletContext();
//			WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
//			creationService = webApplicationContext.getBean(SeedDataCreationService.class);
//		}

		// Filter the requests with the OPTIONS calls because they should not be verified
		String meth = request.getMethod();
		logger.info("-- [SecurityInterceptor.preHandle]> Method: {}", meth);
		if ( meth.equalsIgnoreCase("OPTIONS") ) return true;

		// Remove secutory header for Ionic and app calls.
		String platform = request.getHeader("xApp-Platform");
		logger.info("-- [SecurityInterceptor.preHandle]> Platform: {}", platform);
		if ( null != platform )
			if ( platform.equalsIgnoreCase("Ionic 4.x") ) return true;

		final String headerValue = request.getHeader("xApp-Authentication");
		if ( null != headerValue ) {
			logger.info(">> [SecurityInterceptor.preHandle]> Header valid.");
			// Search for the local session and check it is still valid.
			final SessionManager.AppSession session = SessionManager.retrieve(headerValue);
			logger.info("-- [LoginController.checkCredencial]> Session key: {}", headerValue);
			if ( null != session ) {
				logger.info(">> [SecurityInterceptor.preHandle]> Session valid.");
				logger.info("-- [LoginController.checkCredencial]> Session id: {}", session.getId());
				// Decode the identifier and check that the data matches the session data.
				final String payload = (String) session.getPayload();
				logger.info(">> [SecurityInterceptor.preHandle]> Payload: {}", payload);
				if ( null != payload ) {
					logger.info(">> [SecurityInterceptor.preHandle]> Payload validated.");
					// Check that an unencrypted key matches or not
					boolean match = BCrypt.checkpw(payload, headerValue);
					if ( match ) {
						logger.info(">> [SecurityInterceptor.preHandle]> Payload match.");
						// Check that the user is still enabled at the database.
//						final SeedDataCreationService creationService = CitaMedSBApplication.getService();
//						if ( null != creationService ) {
						final String agentIdentifier = session.getUserName();
//							final boolean enabled = creationService.checkUserIsEnabled(agentIdentifier);
//							logger.info(">> [SecurityInterceptor.preHandle]> User enabled: {}", enabled);
//							if ( enabled ) {
						// Log the user access.
						LogInterceptor.logger.info("++++++++++ [{}] <{}>", request.getRequestURI(), agentIdentifier);
						return true;
//							}
//						}
					}
				}
			}
		}
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		return false;
	}
}

// - UNUSED CODE ............................................................................................
//[01]
