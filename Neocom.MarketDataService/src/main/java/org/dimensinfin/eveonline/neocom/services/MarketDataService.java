//	PROJECT:      Neocom.MarketDataService (NEOC-MKDS)
//	AUTHORS:      Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:	SpringBoot-MS-Java 1.8.
//	DESCRIPTION:	This project contains a MicroService specially dedicated to get and store the Market Data
// information. I should be exposed on a new port and should be accesible to all the backend MS to consult 
// Item Market Data.
package org.dimensinfin.eveonline.neocom.services;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

// - CLASS IMPLEMENTATION ...................................................................................
@EnableCircuitBreaker
@Service
public class MarketDataService {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger										logger				= Logger.getLogger("MarketDataService");
	private static final ArrayList<String>	fixedBookList	= new ArrayList<String>();

	static {
		fixedBookList.add("El señor de los anillos");
		fixedBookList.add("Ender el Genocida");
		fixedBookList.add("El quijote");
	}

	// - F I E L D - S E C T I O N ............................................................................

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public MarketDataService() {
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	@HystrixCommand(fallbackMethod = "reliable")
	public ArrayList<String> getBookStoreList() {
		ArrayList<String> result = new ArrayList<String>();
		//		result.add(applicationName);
		result.addAll(fixedBookList);
		return result;
	}

	public ArrayList<String> reliable() {
		ArrayList<String> result = new ArrayList<String>();
		result.add("Cloud Native Java (O'Reilly)");
		return result;
	}
}

// - UNUSED CODE ............................................................................................
