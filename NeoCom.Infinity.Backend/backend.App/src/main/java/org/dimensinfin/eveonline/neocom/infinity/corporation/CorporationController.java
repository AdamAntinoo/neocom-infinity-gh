package org.dimensinfin.eveonline.neocom.infinity.corporation;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import org.dimensinfin.eveonline.neocom.domain.Corporation;
import org.dimensinfin.eveonline.neocom.infinity.core.NeoComController;
import org.dimensinfin.eveonline.neocom.infinity.core.exceptions.ErrorInfo;
import org.dimensinfin.eveonline.neocom.infinity.core.exceptions.NeoComAuthorizationException;
import org.dimensinfin.eveonline.neocom.infinity.security.NeoComAuthenticationProvider;
//import org.dimensinfin.eveonline.neocom.infinity.security.NeoComAuthenticationProvider;

@RestController
@CrossOrigin()
//@Validated
//@RequestMapping("/api/v1/neocom")
public class CorporationController extends NeoComController {
	private final CorporationService corporationService;
	private final NeoComAuthenticationProvider neoComAuthenticationProvider;

	@Autowired
	public CorporationController( final CorporationService corporationService,
	                              final NeoComAuthenticationProvider neoComAuthenticationProvider ) {
		this.corporationService = corporationService;
		this.neoComAuthenticationProvider = neoComAuthenticationProvider;
	}

	@GetMapping(path = "/api/v1/neocom/corporations/{corporationId}",
			consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<Corporation> getCorporationData( @PathVariable final Integer corporationId ) {
//		logger.info( ">>>>>>>>>>>>>>>>>>>>NEW REQUEST: " + "/api/v1/neocom/corporations/{}",
//				corporationId );
		// Check corporation identifier access is authorized.
		try {
			final Integer authorizedCorporationId = this.neoComAuthenticationProvider.getAuthenticatedCorporation(corporationId);
			if (authorizedCorporationId.intValue() != corporationId.intValue())
				throw new NeoComAuthorizationException( ErrorInfo.CORPORATION_ID_NOT_AUTHORIZED );
		} catch (final IOException ioe) {
			throw new NeoComAuthorizationException( ErrorInfo.CORPORATION_ID_NOT_AUTHORIZED );
		}
		return this.corporationService.getCorporationData( corporationId );
	}
}
