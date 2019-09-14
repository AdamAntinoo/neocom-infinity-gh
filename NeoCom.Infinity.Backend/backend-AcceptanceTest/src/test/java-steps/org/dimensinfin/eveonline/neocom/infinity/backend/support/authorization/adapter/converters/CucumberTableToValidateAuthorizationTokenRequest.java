package org.dimensinfin.eveonline.neocom.infinity.backend.support.authorization.adapter.converters;

import org.dimensinfin.eveonline.neocom.infinity.authorization.rest.dto.ValidateAuthorizationTokenRequest;
import org.dimensinfin.eveonline.neocom.infinity.backend.test.support.CucumberTableToRequestConverter;
import org.dimensinfin.eveonline.neocom.infinity.backend.test.support.RequestType;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CucumberTableToValidateAuthorizationTokenRequest extends CucumberTableToRequestConverter<ValidateAuthorizationTokenRequest> {
    private static final String CODE = "code";
    private static final String STATE = "state";
    private static final String DATA_SOURCE = "dataSource";

    @Override
    public RequestType getType() {
        return RequestType.VALIDATE_AUTHORIZATION_TOKEN_ENDPOINT_NAME;
    }

    @Override
    public ValidateAuthorizationTokenRequest convert( Map<String, String> cucumberRow) {
        return new ValidateAuthorizationTokenRequest.Builder()
                .withCode(cucumberRow.get(CODE))
                       .withState(cucumberRow.get(STATE))
                       .optionalDataSource(cucumberRow.get(DATA_SOURCE))
                       .build();
    }
}
