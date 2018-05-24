//	PROJECT:      Neocom.Microservices (NEOC-MS)
//	AUTHORS:      Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:	SpringBoot-MS-Java 1.8.
//	DESCRIPTION:	This is the integration project for all the web server pieces. This is the launcher for
//								the SpringBoot+MicroServices+Angular unified web application.
package org.dimensinfin.eveonline.neocom.connector;

// - CLASS IMPLEMENTATION ...................................................................................
public interface INeoComMSConnector extends IModelAppConnector {
	@Override
	public ICacheConnector getCacheConnector ();

	@Override
	public ICCPDatabaseConnector getCCPDBConnector ();

	@Override
	public INeoComModelDatabase getDBConnector ();

//	@Override
//	public INeoComModelStore getModelStore ();

	//	public void startChrono ();
	//
	//	public Duration timeLapse ();
}

// - UNUSED CODE ............................................................................................