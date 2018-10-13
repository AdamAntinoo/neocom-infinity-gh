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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Hashtable;

import org.joda.time.DateTime;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.dimensinfin.eveonline.neocom.datamngmt.ESINetworkManager;
import org.dimensinfin.eveonline.neocom.datamngmt.GlobalDataManager;
import org.dimensinfin.eveonline.neocom.datamngmt.InfinityGlobalDataManager;
import org.dimensinfin.eveonline.neocom.entities.Credential;
import org.dimensinfin.eveonline.neocom.model.EveLocation;

/**
 * @author Adam Antinoo
 */
// - CLASS IMPLEMENTATION ...................................................................................
public class SessionManager {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger logger = LoggerFactory.getLogger("SessionManager");
	public static HashMap<String, AppSession> sessionStore = new HashMap();

	public static void store( final AppSession _session ) {
		sessionStore.put(_session.getId(), _session);
	}

	public static AppSession retrieve( final String _id ) {
		// If using mock data we should ever return the testing credential.
		if ( InfinityGlobalDataManager.getResourceBoolean("R.runtime.mockdata") ) {
			final int newAccountIdentifier = 92223647;
			final String salt = BCrypt.gensalt(8);
			final String payload = DateTime.now().toString() + ":"
					+ newAccountIdentifier + ":"
					+ "Beth Ripley";
			final String authorizationToken = BCrypt.hashpw(payload, salt);
			// Build up the session along with the credential data.
			final SessionManager.AppSession session = new SessionManager.AppSession()
					.setAuthorizationToken(authorizationToken)
					.setPayload(payload)
					.setAuthorizationPassword(salt)
					.setUserIdentifier(newAccountIdentifier)
					.setUserName("Beth Ripley");
//			logger.info("-- [LoginController.checkCredencial]> Session id: {}", session.getId());

			// Construct the login response that it is a structure with the token and the Pilot credential.
			final Credential credential = new Credential(newAccountIdentifier)
					.setAccountName("Beth Ripley")
//					.setAccessToken(token.getAccessToken())
//					.setTokenType(token.getTokenType())
//					.setExpires(Instant.now().plus(TimeUnit.SECONDS.toMillis(token.getExpires())).getMillis())
//					.setRefreshToken(token.getRefreshToken())
					.setDataSource(GlobalDataManager.SERVER_DATASOURCE)
					.setScope(ESINetworkManager.getStringScopes());
			session.setCredential(credential);
			return session;
		} else return sessionStore.get(_id);
	}

	public static void readSessionData() {
		logger.info(">> [SessionManager.readSessionData]");
		final String cacheFileName = GlobalDataManager.getResourceString("R.cache.directorypath")
				+ GlobalDataManager.getResourceString("R.runtime.session.savedata.filename");
		logger.info("-- [SessionManager.readSessionData]> Opening cache file: {}", cacheFileName);
		try {
			final BufferedInputStream buffer = new BufferedInputStream(
					GlobalDataManager.openResource4Input(cacheFileName)
			);
			final ObjectInputStream input = new ObjectInputStream(buffer);
			try {
				synchronized (sessionStore) {
					sessionStore = (HashMap<String, AppSession>) input.readObject();
					logger.info("-- [SessionManager.readSessionData]> Restored Session Data: " + sessionStore.size()
							+ " entries.");
				}
			} finally {
				input.close();
				buffer.close();
			}
		} catch ( final ClassNotFoundException ex ) {
			logger.warn("W> [SessionManager.readSessionData]> ClassNotFoundException."); //$NON-NLS-1$
		} catch ( final FileNotFoundException fnfe ) {
			logger.warn("W> [SessionManager.readSessionData]> FileNotFoundException."); //$NON-NLS-1$
		} catch ( final IOException ex ) {
			logger.warn("W> [SessionManager.readSessionData]> IOException."); //$NON-NLS-1$
		} catch ( final RuntimeException rex ) {
			rex.printStackTrace();
		} finally {
			logger.info("<< [SessionManager.readSessionData]");
		}
	}

	public static void writeSessionData() {
		logger.info(">> [SessionManager.writeSessionData]");
		final String cacheFileName = GlobalDataManager.getResourceString("R.cache.directorypath")
				+ GlobalDataManager.getResourceString("R.runtime.session.savedata.filename");
		logger.info("-- [SessionManager.writeSessionData]> Opening cache file: {}", cacheFileName);
		//		File modelStoreFile = new File(cacheFileName);
		try {
			final BufferedOutputStream buffer = new BufferedOutputStream(
					GlobalDataManager.openResource4Output(cacheFileName)
			);
			final ObjectOutput output = new ObjectOutputStream(buffer);
			try {
				synchronized (sessionStore) {
					output.writeObject(sessionStore);
					logger.info(
							"-- [SessionManager.writeSessionData]> Wrote Session Data: " + sessionStore.size() + " entries.");
				}
			} finally {
				output.flush();
				output.close();
				buffer.close();
			}
		} catch ( final FileNotFoundException fnfe ) {
			logger.warn("W> [SessionManager.writeSessionData]> FileNotFoundException."); //$NON-NLS-1$
		} catch ( final IOException ex ) {
			logger.warn("W> [SessionManager.writeSessionData]> IOException."); //$NON-NLS-1$
		} finally {
			logger.info("<< [SessionManager.writeSessionData]");
		}
	}
	// - F I E L D - S E C T I O N ............................................................................

	// - C O N S T R U C T O R - S E C T I O N ................................................................

	// - M E T H O D - S E C T I O N ..........................................................................
	// --- G E T T E R S   &   S E T T E R S
	public static class AppSession implements Serializable {
		private static final long serialVersionUID = -945048861313405949L;

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
