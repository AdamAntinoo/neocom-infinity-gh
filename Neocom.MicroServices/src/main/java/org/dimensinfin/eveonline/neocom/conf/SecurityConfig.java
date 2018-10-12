//  PROJECT:     CitaMed.backend (CMBK.SB)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2018, 2019 by Endless Dimensions Ltd., all rights reserved.
//  ENVIRONMENT: SpringBoot 2.0.
//  SITE:        citasmedico.com
//  DESCRIPTION: CitasMedico. Sistema S1. Aplicacion SpringBoot adaptada para Heroku y diseñada con arquitectura
//               JPA como sistema de persistencia. Se configura para utilizar una base de datos PostgreSQL
//               y dotado de un modelo de acceso RESTful para poder exportar el api y el modelo para acceso
//               desde aplicaciones dispares, como Angular 6 o Android.
package org.dimensinfin.citamed.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.dimensinfin.eveonline.neocom.datamngmt.InfinityGlobalDataManager;
import org.dimensinfin.eveonline.neocom.security.HeaderVerifierInterceptor;
import org.dimensinfin.eveonline.neocom.security.LogInterceptor;
import org.dimensinfin.eveonline.neocom.security.SecurityInterceptor;

/**
 * @author Adam Antinoo
 */
// - CLASS IMPLEMENTATION ...................................................................................
@Configuration
public class SecurityConfig implements WebMvcConfigurer {
	// - S T A T I C - S E C T I O N ..........................................................................

	// - F I E L D - S E C T I O N ............................................................................

	// - C O N S T R U C T O R - S E C T I O N ................................................................

	// - M E T H O D - S E C T I O N ..........................................................................
	@Override
	public void addInterceptors( InterceptorRegistry registry ) {
		registry.addInterceptor(new LogInterceptor()).addPathPatterns("/api/**");
		if ( InfinityGlobalDataManager.getResourceString("P.runtime.security.headers").equalsIgnoreCase("on") ) {
			registry.addInterceptor(new HeaderVerifierInterceptor()).addPathPatterns("/api/**");
		}
		if ( InfinityGlobalDataManager.getResourceString("P.runtime.security.handlers").equalsIgnoreCase("on") ) {
			registry.addInterceptor(new SecurityInterceptor()).addPathPatterns("/api/v1/centros/**");
			registry.addInterceptor(new SecurityInterceptor()).addPathPatterns("/api/v1/medicos/**");
			registry.addInterceptor(new SecurityInterceptor()).addPathPatterns("/api/v1/citas/**");
			registry.addInterceptor(new SecurityInterceptor()).addPathPatterns("/api/v1/paciente/**");
			registry.addInterceptor(new SecurityInterceptor()).addPathPatterns("/api/v1/processor/**");
		}
	}
}

// - UNUSED CODE ............................................................................................
//[01]
