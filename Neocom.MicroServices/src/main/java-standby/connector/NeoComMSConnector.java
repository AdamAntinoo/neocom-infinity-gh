//	PROJECT:      Neocom.Microservices (NEOC-MS)
//	AUTHORS:      Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:	SpringBoot-MS-Java 1.8.
//	DESCRIPTION:	This is the integration project for all the web server pieces. This is the launcher for
//								the SpringBoot+MicroServices+Angular unified web application.
package org.dimensinfin.eveonline.neocom.connector;

// - CLASS IMPLEMENTATION ...................................................................................
public class NeoComMSConnector extends ModelAppConnector implements INeoComMSConnector {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static NeoComMSConnector _singleton = null;

	public static NeoComMSConnector getSingleton() {
		if (null == NeoComMSConnector._singleton) throw new RuntimeException(
				"RTEX [NeoComAppConnector.getSingleton]> Application chain not initialized. All class functionalities disabled.");
		return NeoComMSConnector._singleton;
	}

	// - F I E L D - S E C T I O N ............................................................................
	private final INeoComMSConnector _connector;

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public NeoComMSConnector(final INeoComMSConnector application) {
		super(application);
		_connector = application;
		_singleton = this;
	}

	// - M E T H O D - S E C T I O N ..........................................................................

}

// - UNUSED CODE ............................................................................................
