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

import java.util.HashMap;

import org.dimensinfin.eveonline.neocom.database.entity.Credential;

/**
 * @author Adam Antinoo
 */
// - CLASS IMPLEMENTATION ...................................................................................
public class SessionManager {
	// - S T A T I C - S E C T I O N ..........................................................................
//	private static Logger logger = LoggerFactory.getLogger("SessionManager");
	public static HashMap<String, AppSession> sessionStore = new HashMap();

	public static void store( final AppSession _session ) {
		sessionStore.put(_session.getId(), _session);
	}

	public static AppSession retrieve( final String _id ) {
		return sessionStore.get(_id);
	}

	// - F I E L D - S E C T I O N ............................................................................

	// - C O N S T R U C T O R - S E C T I O N ................................................................

	// - M E T H O D - S E C T I O N ..........................................................................
	// --- G E T T E R S   &   S E T T E R S
	public static class AppSession {
		private String authorizationToken;
		private String payload;
		private String authorizationPassword;
		private int userIdentifier;
		private String userName;
		private String role;
		private Credential credential;

		public String getId() {
			return this.authorizationToken;
		}

		public String getAuthorizationToken() {
			return authorizationToken;
		}

		public AppSession setAuthorizationToken( final String authorizationToken ) {
			this.authorizationToken = authorizationToken;
			return this;
		}

		public String getPayload() {
			return payload;
		}

		public AppSession setPayload( final String payload ) {
			this.payload = payload;
			return this;
		}

		public String getAuthorizationPassword() {
			return authorizationPassword;
		}

		public AppSession setAuthorizationPassword( final String authorizationPassword ) {
			this.authorizationPassword = authorizationPassword;
			return this;
		}

		public int getUserIdentifier() {
			return userIdentifier;
		}

		public AppSession setUserIdentifier( final int userIdentifier ) {
			this.userIdentifier = userIdentifier;
			return this;
		}

		public String getUserName() {
			return userName;
		}

		public AppSession setUserName( final String userName ) {
			this.userName = userName;
			return this;
		}


		public String getRole() {
			return role;
		}

		public AppSession setRole( final String role ) {
			this.role = role;
			return this;
		}

		public Credential getCredential() {
			return credential;
		}

		public AppSession setCredential( final Credential credential ) {
			this.credential = credential;
			return this;
		}

		public void store() {
			SessionManager.store(this);
		}
	}
}

// - UNUSED CODE ............................................................................................
//[01]
