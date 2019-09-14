package org.dimensinfin.eveonline.neocom.infinity.backend.support;

import org.apache.commons.lang.NotImplementedException;
import org.dimensinfin.eveonline.neocom.infinity.backend.test.support.CucumberTableToRequestConverter;
import org.dimensinfin.eveonline.neocom.infinity.backend.test.support.RequestType;

import java.util.List;

public class SupportSteps {
	protected List<CucumberTableToRequestConverter> cucumberTableToRequestConverters;

	public SupportSteps( final List<CucumberTableToRequestConverter> cucumberTableToRequestConverters ) {this.cucumberTableToRequestConverters = cucumberTableToRequestConverters;}

	protected CucumberTableToRequestConverter findConverter( RequestType requestType) {
		return cucumberTableToRequestConverters.stream()
				       .filter(cucumberTableToRequestConverter -> cucumberTableToRequestConverter.getType() == requestType)
				       .findFirst()
				       .orElseThrow(NotImplementedException::new);
	}
}
