//	PROJECT:      Neocom.Microservices (NEOC-MS)
//	AUTHORS:      Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:	SpringBoot-MS-Java 1.8.
//	DESCRIPTION:	This is the integration project for all the web server pieces. This is the launcher for
//								the SpringBoot+MicroServices+Angular unified web application.
package org.dimensinfin.eveonline.neocom.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.dimensinfin.eveonline.neocom.connector.AppConnector;
import org.dimensinfin.eveonline.neocom.model.EveLocation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.beimin.eveapi.exception.ApiException;
import com.beimin.eveapi.model.eve.Station;
import com.beimin.eveapi.parser.eve.ConquerableStationListParser;
import com.beimin.eveapi.response.eve.StationListResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.nikr.eve.jeveasset.data.Citadel;

// - CLASS IMPLEMENTATION ...................................................................................
@RestController
public class LocationController {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger logger = Logger.getLogger("LocationController");

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
		try {
			AppConnector.startChrono();
			return "<< [LocationController.updateCitadels]>[COUNTER]> Citadels Processed: - "
					+ updateCitadelsProcess().size();
		} catch (RuntimeException rtex) {
			return "<< [LocationController.updateCitadels]>[COUNTER]> Detected Exception: - " + rtex.getMessage();
		} finally {
			logger.info("<< [LocationController.updateCitadels]>[TIMING] Processing Time: - " + AppConnector.timeLapse());
		}
	}

	@CrossOrigin()
	@RequestMapping(value = "/api/v1/location/updateoutposts", method = RequestMethod.GET, produces = "application/json")
	public String updateOutposts() {
		logger.info(">>>>>>>>>>>>>>>>>>>>NEW REQUEST: " + "/api/v1/location/updateoutposts");
		logger.info(">> [LocationController.updateOutposts]");
		try {
			AppConnector.startChrono();
			return "<< [LocationController.updateOutposts]>[COUNTER]> Outposts Processed: - "
					+ updateOutpostsProcess().getStations().size();
		} catch (RuntimeException rtex) {
			return "<< [LocationController.updateOutposts]>[COUNTER]> Detected Exception: - " + rtex.getMessage();
		} finally {
			logger.info("<< [LocationController.updateOutposts]>[TIMING] Processing Time: - " + AppConnector.timeLapse());
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
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public void checkClientTrusted(final X509Certificate[] certs, final String authType) {
			}

			public void checkServerTrusted(final X509Certificate[] certs, final String authType) {
			}

			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		} };
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
	public boolean verify(final String hostname, final SSLSession session) {
		return true;
	}
}

// - UNUSED CODE ............................................................................................
