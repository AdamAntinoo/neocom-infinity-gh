package org.dimensinfin.eveonline.neocom.infinity.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import org.dimensinfin.eveonline.neocom.database.entities.Credential;
import org.dimensinfin.eveonline.neocom.infinity.authorization.rest.dto.ValidateAuthorizationTokenResponse;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class ValidateAuthorizationTokenResponseSerializer extends JsonSerializer<ValidateAuthorizationTokenResponse> {
	@Override
	public void serialize( final ValidateAuthorizationTokenResponse value, final JsonGenerator jgen, final SerializerProvider provider )
			throws IOException, JsonProcessingException {
		jgen.writeStartObject();

		jgen.writeObjectField("jwtToken", value.getJwtToken());
		jgen.writeObjectField("credential", value.getCredential());

		jgen.writeEndObject();
	}
}
