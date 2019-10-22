package org.dimensinfin.eveonline.neocom.infinity.universe;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.dimensinfin.eveonline.neocom.adapters.ESIDataAdapter;
import org.dimensinfin.eveonline.neocom.infinity.core.NeoComController;
import org.dimensinfin.eveonline.neocom.infinity.universe.client.v1.ServerStatus;

@RestController
@Validated
@RequestMapping("/api/v1/neocom")
public class UniverseController extends NeoComController {
	private final UniverseService universeService;

	@Autowired
	public UniverseController( final UniverseService universeService ) {
		this.universeService = universeService;
	}

	@GetMapping(path = { "/server/datasource/{dataSource}",
			"/server/datasource/",
			"/server/",
			"/server" },
//			consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<ServerStatus> getServerStatus( @PathVariable final Optional<String> dataSource ) {
		logger.info( ">>>>>>>>>>>>>>>>>>>>NEW REQUEST: " + "/server/datasource/{}", dataSource );
		String server;
		if (!dataSource.isPresent()) server = ESIDataAdapter.DEFAULT_ESI_SERVER;
		else server = dataSource.get();
		return this.universeService.getServerStatus( server );
	}
}