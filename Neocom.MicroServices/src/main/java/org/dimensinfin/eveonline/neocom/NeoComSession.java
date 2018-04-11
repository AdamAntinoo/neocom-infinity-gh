//  PROJECT:     NeoCom.Microservices (NEOC.MS)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2017-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Java 1.8 / SpringBoot-1.3.5 / Angular 5.0
//  DESCRIPTION: This is the SpringBoot MicroServices module to run the backend services to complete the web
//               application based on Angular+SB. This is the web version for the NeoCom Android native
//               application. Most of the source code is common to both platforms and this module includes
//               the source for the specific functionality for the backend services.
package org.dimensinfin.eveonline.neocom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.dimensinfin.eveonline.neocom.database.entity.Credential;

/**
 * @author Adam Antinoo
 */
// - CLASS IMPLEMENTATION ...................................................................................
public class NeoComSession {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger logger = LoggerFactory.getLogger("NeoComSession");

	// - F I E L D - S E C T I O N ............................................................................
	/**
	 * Copy of the front end serialized public key generated fro this login session.
	 */
	private String frontendPublicKey = null;
	private Credential credential = null;

	// - C O N S T R U C T O R - S E C T I O N ................................................................

	// - M E T H O D - S E C T I O N ..........................................................................
	//--- G E T T E R S   &   S E T T E R S
	public String getPublicKey() {
		return frontendPublicKey;
	}

	public int getPilotIdentifier() {
		return credential.getAccountId();
	}

	public Credential getCredential() {
		return credential;
	}

	public NeoComSession setCredential( final Credential credential ) {
		this.credential = credential;
//		identifier = encrypt(privateKey, credential.getValidationCode());
		return this;
	}

	public NeoComSession setPublicKey( final String publicKey ) {
		this.frontendPublicKey = publicKey;
		return this;
	}

	public String getSessionId() {
		return credential.getAccessToken();
	}

	//	public KeyPair buildKeyPair() throws NoSuchAlgorithmException {
//		final int keySize = 2048;
//		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
//		keyPairGenerator.initialize(keySize);
//		return keyPairGenerator.genKeyPair();
//	}
	// --- D E L E G A T E D   M E T H O D S
	@Override
	public String toString() {
		final StringBuffer buffer = new StringBuffer("NeoComSession [ ");
		if (null != credential) buffer.append("identifier:").append(credential.getAccountId()).append(" ");
		return buffer.append("]")
				.append("->").append(super.toString())
				.toString();
	}
}

// - UNUSED CODE ............................................................................................
//[01]
