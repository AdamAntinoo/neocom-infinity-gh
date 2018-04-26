//  PROJECT:     NeoCom.Microservices (NEOC.MS)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2017-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Java 1.8 / SpringBoot-1.3.5 / Angular 5.0
//  DESCRIPTION: This is the SpringBoot MicroServices module to run the backend services to complete the web
//               application based on Angular+SB. This is the web version for the NeoCom Android native
//               application. Most of the source code is common to both platforms and this module includes
//               the source for the specific functionality for the backend services.
package org.dimensinfin.eveonline.neocom.controller;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import net.nikr.eve.jeveasset.data.Citadel;

import com.beimin.eveapi.exception.ApiException;
import com.beimin.eveapi.model.eve.Station;
import com.beimin.eveapi.parser.eve.ConquerableStationListParser;
import com.beimin.eveapi.response.eve.StationListResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import org.dimensinfin.core.util.Chrono;
import org.dimensinfin.eveonline.neocom.datamngmt.GlobalDataManager;
import org.dimensinfin.eveonline.neocom.model.EveLocation;

// - CLASS IMPLEMENTATION ...................................................................................
@RestController
public class LocationController {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger logger = LoggerFactory.getLogger("LocationController");

	// - F I E L D - S E C T I O N ............................................................................

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public LocationController() {
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	@CrossOrigin()
	@RequestMapping(value = "/api/v1/location/updatecitadels", method = RequestMethod.GET, produces = "application/json")
	public String updateCitadels() {
		logger.info(">>>>>>>>>>>>>>>>>>>>NEW REQUEST: " + "/api/v1/location/updatecitadels");
		logger.info(">> [LocationController.updateCitadels]");
		Chrono totalUpdateDuration = new Chrono();
		try {
			return "<< [LocationController.updateCitadels]>[COUNTER]> Citadels Processed: "
					+ updateCitadelsProcess().size();
		} catch (RuntimeException rtex) {
			return "<< [LocationController.updateCitadels]>[COUNTER]> Detected Exception: - " + rtex.getMessage();
		} finally {
			GlobalDataManager.writeLocationsDatacache();
			logger.info("<< [LocationController.updateCitadels]> [TIMING] Processing Time: {}", totalUpdateDuration.printElapsed
					(Chrono.ChronoOptions.DEFAULT));
		}
	}

	@CrossOrigin()
	@RequestMapping(value = "/api/v1/location/updateoutposts", method = RequestMethod.GET, produces = "application/json")
	public String updateOutposts() {
		logger.info(">>>>>>>>>>>>>>>>>>>>NEW REQUEST: " + "/api/v1/location/updateoutposts");
		logger.info(">> [LocationController.updateOutposts]");
		Chrono totalUpdateDuration = new Chrono();
		try {
			return "<< [LocationController.updateOutposts]>[COUNTER]> Outposts Processed: "
					+ updateOutpostsProcess().getStations().size();
		} catch (RuntimeException rtex) {
			return "<< [LocationController.updateOutposts]>[COUNTER]> Detected Exception: - " + rtex.getMessage();
		} finally {
			GlobalDataManager.writeLocationsDatacache();
			logger.info("<< [LocationController.updateCitadels]> [TIMING] Processing Time: {}", totalUpdateDuration.printElapsed
					(Chrono.ChronoOptions.DEFAULT));
		}
	}

	/**
	 * The datasource is ready and the new hierarchy should be created from the current model. All the stages
	 * are executed at this time both the model contents update and the list of parts to be used on the
	 * ListView. First, the model is checked to be initialized and if not then it is created. Then the model is
	 * run from start to end to create all the visible elements and from this list then we create the full list
	 * of the parts with their right renders.<br>
	 * This is the task executed every time a datasource gets its model modified and hides all the update time
	 * from the main thread as it is recommended by Google.
	 */
	private Map<Long, Citadel> updateCitadelsProcess() {
		logger.info(">> [LocationController.updateCitadelsProcess]");
		String destination = "https://stop.hammerti.me.uk/api/citadel/all";
		// Create a trust manager that does not validate certificate chains
		TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
			@Override
			public void checkClientTrusted( final X509Certificate[] certs, final String authType ) {
			}

			@Override
			public void checkServerTrusted( final X509Certificate[] certs, final String authType ) {
			}

			@Override
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		}};
		// Install the all-trusting trust manager
		SSLContext sc;
		InputStream in = null;
		Map<Long, Citadel> results = null;
		try {
			sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
			// Create all-trusting host name verifier
			HostnameVerifier allHostsValid = new Verifier();

			// Install the all-trusting host verifier
			HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

			ObjectMapper mapper = new ObjectMapper(); //create once, reuse
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			URL url = new URL(destination);
			URLConnection con = url.openConnection();
			con.setRequestProperty("Accept-Encoding", "gzip");
			//				long contentLength = con.getContentLength();
			String contentEncoding = con.getContentEncoding();
			InputStream inputStream = con.getInputStream();
			if ("gzip".equals(contentEncoding)) {
				in = new GZIPInputStream(inputStream);
			} else {
				in = inputStream;
			}
			results = mapper.readValue(in, new TypeReference<Map<Long, Citadel>>() {
			});
			if (results != null) {
				for (Map.Entry<Long, Citadel> entry : results.entrySet()) {
					// Convert each Citadel to a new Location and update the database if needed.
					EveLocation loc = new EveLocation(entry.getKey(), entry.getValue());
					logger.info("-- [LocationController.updateCitadelsProcess]> Created location: " + loc);
				}
			}
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException ex) {
					ex.printStackTrace();
					//No problem...
				}
			}
		}
		logger.info("<< [LocationController.updateCitadelsProcess]");
		return results;
	}

	/**
	 * The datasource is ready and the new hierarchy should be created from the current model. All the stages
	 * are executed at this time both the model contents update and the list of parts to be used on the
	 * ListView. First, the model is checked to be initialized and if not then it is created. Then the model is
	 * run from start to end to create all the visible elements and from this list then we create the full list
	 * of the parts with their right renders.<br>
	 * This is the task executed every time a datasource gets its model modified and hides all the update time
	 * from the main thread as it is recommended by Google.
	 */
	private StationListResponse updateOutpostsProcess() {
		logger.info(">> [LocationController.updateOutpostsProcess]");
		StationListResponse response = null;
		try {
			//				StationListResponse response = null;
			ConquerableStationListParser parser = new ConquerableStationListParser();
			response = parser.getResponse();
			if (null != response) {
				Map<Long, Station> stations = response.getStations();
				for (Long stationid : stations.keySet()) {
					// Convert the station to an EveLocation
					EveLocation loc = new EveLocation(stations.get(stationid));
					logger.info("-- [LocationController.updateOutpostsProcess]> Created location: " + loc);
				}
			}
		} catch (final RuntimeException rtex) {
			rtex.printStackTrace();
		} catch (ApiException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		logger.info("<< [LocationController.updateOutpostsProcess]");
		return response;
	}

}

final class Verifier implements HostnameVerifier {
	@Override
	public boolean verify( final String hostname, final SSLSession session ) {
		return true;
	}
}

// - UNUSED CODE ............................................................................................
