package org.dimensinfin.eveonline.neocom.infinity.backend.support;

import org.apache.commons.lang.NotImplementedException;
import org.dimensinfin.eveonline.neocom.infinity.backend.test.support.ConverterContainer;
import org.dimensinfin.eveonline.neocom.infinity.backend.test.support.CucumberTableToRequestConverter;
import org.dimensinfin.eveonline.neocom.infinity.backend.test.support.RequestType;

public class SupportSteps {
	protected ConverterContainer cucumberTableToRequestConverters;

	public SupportSteps( final ConverterContainer cucumberTableToRequestConverters ) {
		this.cucumberTableToRequestConverters = cucumberTableToRequestConverters;
	}

	protected CucumberTableToRequestConverter findConverter( RequestType requestType ) {
		return cucumberTableToRequestConverters.getConverters().stream()
				       .filter(cucumberTableToRequestConverter -> cucumberTableToRequestConverter.getType() == requestType)
				       .findFirst()
				       .orElseThrow(NotImplementedException::new);
	}
}
