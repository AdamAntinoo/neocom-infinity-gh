//	PROJECT:      Neocom.MarketDataService (NEOC-MKDS)
//	AUTHORS:      Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:	SpringBoot-MS-Java 1.8.
//	DESCRIPTION:	This project contains a MicroService specially dedicated to get and store the Market Data
//								information. I should be exposed on a new port and should be accesible to all the backend MS to consult 
//								Item Market Data.
package org.dimensinfin.eveonline.neocom.commands;

import java.util.logging.Logger;

import org.dimensinfin.eveonline.neocom.enums.EMarketSide;
import org.dimensinfin.eveonline.neocom.market.MarketDataSet;
import org.dimensinfin.eveonline.neocom.services.MarketDataServer;
import org.springframework.beans.factory.annotation.Autowired;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

// - CLASS IMPLEMENTATION ...................................................................................
public class FallbackMarketDataCommand extends HystrixCommand<MarketDataSet> {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger				logger						= Logger.getLogger("FallbackMarketDataCommand");
	private static final String	COMMAND_GROUP			= "MarketData";

	// - F I E L D - S E C T I O N ............................................................................
	private String							itemIdInput				= null;
	private String							sideInput					= null;
	private int									itemidnumber			= 3645;
	private EMarketSide					sideenumerated		= EMarketSide.BUYER;
	@Autowired
	private MarketDataServer		marketDataService	= new MarketDataServer();

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public FallbackMarketDataCommand() {
		super(HystrixCommandGroupKey.Factory.asKey(COMMAND_GROUP));
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	public FallbackMarketDataCommand setItemId(String itemId) {
		this.itemIdInput = itemId;
		return this;
	}

	public FallbackMarketDataCommand setSide(String side) {
		this.sideInput = side;
		return this;
	}

	@Override
	protected MarketDataSet getFallback() {
		logger.warning("W> [FallbackMarketDataCommand.marketData]> Error detected. Falling back to default value.");
		return new MarketDataSet(itemidnumber, sideenumerated);
	}

	@Override
	protected MarketDataSet run() throws Exception {
		// Convert parameters to command fields.
		if (null == itemIdInput) return new MarketDataSet(3645, EMarketSide.BUYER);
		itemidnumber = Integer.valueOf(itemIdInput).intValue();
		if (null == sideInput) sideenumerated = EMarketSide.BUYER;
		if (sideInput.equalsIgnoreCase(EMarketSide.SELLER.name())) sideenumerated = EMarketSide.SELLER;
		if (sideInput.equalsIgnoreCase(EMarketSide.BUYER.name())) sideenumerated = EMarketSide.BUYER;

		// Request the data to the service.
		MarketDataSet data = marketDataService.marketDataServiceEntryPoint(itemidnumber, sideenumerated);
		return data;
	}
}

// - UNUSED CODE ............................................................................................
