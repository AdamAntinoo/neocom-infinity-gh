package org.dimensinfin.eveonline.neocom.infinity.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

@RestController
public abstract class NeoComController {
	protected static Logger logger = LoggerFactory.getLogger(NeoComController.class);
}