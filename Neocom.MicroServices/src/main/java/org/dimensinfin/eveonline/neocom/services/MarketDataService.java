//	PROJECT:      Neocom.MarketDataService (NEOC-MKDS)
//	AUTHORS:      Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:	SpringBoot-MS-Java 1.8.
//	DESCRIPTION:	This project contains a MicroService specially dedicated to get and store the Market Data
// information. I should be exposed on a new port and should be accesible to all the backend MS to consult 
// Item Market Data.
package org.dimensinfin.eveonline.neocom.services;

import java.net.URI;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Logger;

import org.dimensinfin.eveonline.neocom.NeocomMicroServiceApplication;
import org.dimensinfin.eveonline.neocom.controller.XUserAgentInterceptor;
import org.dimensinfin.eveonline.neocom.market.MarketDataSet;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
	/**
	 * The Service will call another external Micro Service to get the data requested. In case of error of if
	 * the data is not available t will fail back to the <code>reliable()</code>method and the data will be
	 * structurally valid even their contents will be stale.
	 * 
	 * @return
	 */
	@HystrixCommand(fallbackMethod = "reliable")
	public Vector<MarketDataSet> getData(int itemid) {
		// Prepare the call to the independent service. Identify the calling application even not being used
		RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory());
		restTemplate.getInterceptors().add(new XUserAgentInterceptor(NeocomMicroServiceApplication.singleton.getAppName()));
		URI uri = URI.create("http://localhost:9002/api/v1/marketdata/" + itemid);

		return restTemplate.getForObject(uri, String.class);
	}

	public ArrayList<String> reliable() {
		ArrayList<String> result = new ArrayList<String>();
		result.add("Cloud Native Java (O'Reilly)");
		return result;
	}

	private ClientHttpRequestFactory clientHttpRequestFactory() {
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
		factory.setReadTimeout(2000);
		factory.setConnectTimeout(2000);
		return factory;
	}
}

// - UNUSED CODE ............................................................................................
