//	PROJECT:      Neocom.MarketDataService (NEOC-MKDS)
//	AUTHORS:      Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:	SpringBoot-MS-Java 1.8.
//	DESCRIPTION:	This project contains a MicroService specially dedicated to get and store the Market Data
// information. I should be exposed on a new port and should be accesible to all the backend MS to consult 
// Item Market Data.
package org.dimensinfin.eveonline.neocom.services;

import java.net.URI;
import java.util.logging.Logger;

import org.dimensinfin.eveonline.neocom.NeocomMicroServiceApplication;
import org.dimensinfin.eveonline.neocom.connector.AppConnector;
import org.dimensinfin.eveonline.neocom.controller.XUserAgentInterceptor;
import org.dimensinfin.eveonline.neocom.enums.EMarketSide;
import org.dimensinfin.eveonline.neocom.market.MarketDataSet;
import org.dimensinfin.eveonline.neocom.model.EveItem;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

// - CLASS IMPLEMENTATION ...................................................................................
@EnableCircuitBreaker
@Service
public class MarketDataClient {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger				logger							= Logger.getLogger("MarketDataService");
	private static final String	SERVICE_HOST				= "http://localhost:9002";
	private static final String	API_VERSION					= "/api/v1";
	private static final String	ENTRYPOINT_GETDATA	= "/marketdata/";

	// - F I E L D - S E C T I O N ............................................................................
	private int									itemidcopy					= -1;
	private EMarketSide					sidecopy						= EMarketSide.BUYER;

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	//	public MarketDataService() {
	//	}

	//- M E T H O D - S E C T I O N ..........................................................................
	public MarketDataSet errorFallback(final int itemid, final EMarketSide side) {
		return new MarketDataSet(itemid, side);
	}

	/**
	 * The Service will call another external Micro Service to get the data requested. In case of error of if
	 * the data is not available t will fail back to the <code>reliable()</code>method and the data will be
	 * structurally valid even their contents will be stale.
	 */
	@HystrixCommand(fallbackMethod = "reliable")
	public MarketDataSet getData(final int itemid, final EMarketSide side) {
		logger.info(">< [MarketDataService.getData]> itemid: " + itemid + " side: " + side.name());
		// Store parameters to be used on fallback methods.
		EveItem item = AppConnector.getDBConnector().searchItembyID(itemid);
		itemidcopy = itemid;
		String itemnamecopy = "";
		if (null != item) itemnamecopy = item.getName();
		sidecopy = side;
		// Prepare the call to the independent service. Identify the calling application even not being used
		RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory());
		restTemplate.getInterceptors().add(new XUserAgentInterceptor(NeocomMicroServiceApplication.singleton.getAppName()));
		URI uri = URI
				.create(SERVICE_HOST + API_VERSION + ENTRYPOINT_GETDATA + itemid + "/" + itemnamecopy + "/" + side.name());

		MarketDataSet resultData = restTemplate.getForObject(uri, MarketDataSet.class);
		// Check that the data returned is the valid data expected. Otherwise resort to the 'reliable' call.
		// TODO I do not know how to detect bad from good results until testing.
		if (resultData.valid)
			return resultData;
		else
			return errorFallback(itemid, side);
	}

	public MarketDataSet reliable() {
		logger.info(">< [MarketDataService.reliable]>Using the fail back Hystrix call");
		return new MarketDataSet(itemidcopy, EMarketSide.BUYER);
	}

	private ClientHttpRequestFactory clientHttpRequestFactory() {
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
		factory.setReadTimeout(2000);
		factory.setConnectTimeout(2000);
		return factory;
	}
}

// - UNUSED CODE ............................................................................................
