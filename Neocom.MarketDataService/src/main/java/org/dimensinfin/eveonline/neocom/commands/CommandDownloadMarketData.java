//	PROJECT:      Neocom.MarketDataService (NEOC-MKDS)
//	AUTHORS:      Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:	SpringBoot-MS-Java 1.8.
//	DESCRIPTION:	This project contains a MicroService specially dedicated to get and store the Market Data
// information. I should be exposed on a new port and should be accesible to all the backend MS to consult 
// Item Market Data.
package org.dimensinfin.eveonline.neocom.commands;

import java.util.logging.Logger;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixThreadPoolKey;

// - CLASS IMPLEMENTATION ...................................................................................
public class CommandDownloadMarketData extends HystrixCommand<String> {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger	logger	= Logger.getLogger("CommandDownloadMarketData.java");

	// - F I E L D - S E C T I O N ............................................................................
	private final String	name;

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public CommandDownloadMarketData(String name) {
		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"))
				.andCommandKey(HystrixCommandKey.Factory.asKey("HelloWorld"))
				.andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("HelloWorldPool")));
		this.name = name;
	}

	@Override
	protected String getFallback() {
		return "Hello Failure " + name + "!";
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	@Override
	protected String run() {
		// a real example would do work like a network call here
		return "Hello " + name + "!";
	}
}

// - UNUSED CODE ............................................................................................
