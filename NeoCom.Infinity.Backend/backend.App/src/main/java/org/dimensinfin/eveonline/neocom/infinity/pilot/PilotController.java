package org.dimensinfin.eveonline.neocom.infinity.pilot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import org.dimensinfin.eveonline.neocom.domain.Pilot;
import org.dimensinfin.eveonline.neocom.infinity.core.NeoComController;
import org.dimensinfin.eveonline.neocom.infinity.core.exceptions.ErrorInfo;
import org.dimensinfin.eveonline.neocom.infinity.core.exceptions.NeoComAuthorizationException;
import org.dimensinfin.eveonline.neocom.infinity.security.NeoComAuthenticationProvider;

@RestController
@CrossOrigin()
//@Validated
//@RequestMapping("/api/v1/neocom")
public class PilotController extends NeoComController {
	private final PilotService pilotService;
	private final NeoComAuthenticationProvider neoComAuthenticationProvider;

	@Autowired
	public PilotController( final PilotService pilotService,
	                        final NeoComAuthenticationProvider neoComAuthenticationProvider ) {
		this.pilotService = pilotService;
		this.neoComAuthenticationProvider = neoComAuthenticationProvider;
	}

	@GetMapping(path = "/api/v1/neocom/pilots/{pilotId}",
			consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<Pilot> getPilotData( @PathVariable final Integer pilotId ) {
//		logger.info( ">>>>>>>>>>>>>>>>>>>>NEW REQUEST: " + "/api/v1/neocom/pilots/{}", pilotId );
		final Integer authorizedPilotId = this.neoComAuthenticationProvider.getAuthenticatedPilot();
		if (authorizedPilotId.intValue() != pilotId.intValue())
			throw new NeoComAuthorizationException( ErrorInfo.PILOT_ID_NOT_AUTHORIZED );
		return this.pilotService.getPilotData( pilotId );
	}
}