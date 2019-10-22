package org.dimensinfin.eveonline.neocom.infinity.steps;

import org.apache.commons.lang3.NotImplementedException;

import org.dimensinfin.eveonline.neocom.infinity.support.RequestType;
import org.dimensinfin.eveonline.neocom.infinity.test.support.ConverterContainer;
import org.dimensinfin.eveonline.neocom.infinity.test.support.CucumberTableToRequestConverter;

public class SupportSteps {
	protected ConverterContainer cucumberTableToRequestConverters;

	public SupportSteps( final ConverterContainer cucumberTableToRequestConverters ) {
		this.cucumberTableToRequestConverters = cucumberTableToRequestConverters;
	}

	protected CucumberTableToRequestConverter findConverter( RequestType requestType ) {
		return cucumberTableToRequestConverters.getConverters().stream()
				       .filter(cucumberTableToRequestConverter -> cucumberTableToRequestConverter.getType() == requestType)
				       .findFirst()
				       .orElseThrow(()-> new NotImplementedException("Request not implemented."));
	}
}
