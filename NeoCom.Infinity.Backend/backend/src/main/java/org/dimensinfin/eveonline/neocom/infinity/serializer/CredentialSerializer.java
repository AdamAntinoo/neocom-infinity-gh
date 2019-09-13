package org.dimensinfin.eveonline.neocom.infinity.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import org.dimensinfin.eveonline.neocom.database.entities.Credential;

import java.io.IOException;

public class CredentialSerializer extends JsonSerializer<Credential> {
//	private NeoComComponentFactory componentFactory;

//	public CredentialSerializer(@Autowired final NeoComComponentFactory componentFactory) {
//		this.componentFactory = componentFactory;
//	}
	@Override
	public void serialize(final Credential value, final JsonGenerator jgen, final SerializerProvider provider)
			throws IOException, JsonProcessingException {
		jgen.writeStartObject();

		jgen.writeStringField("jsonClass", value.getJsonClass());
//		jgen.writeNumberField("characterId", value.getCharacterId());
//		jgen.writeStringField("name", value.getName());
//		jgen.writeObjectField("corporation", value.getCorporation());
//		jgen.writeObjectField("alliance", value.getAlliance());
//		jgen.writeObjectField("race", value.getRace());
//		jgen.writeObjectField("bloodline", value.getBloodline());
//		jgen.writeObjectField("ancestry", value.getAncestry());
//
//		// Transform the birthday time to a date string.
//		DateTime dt = new DateTime(value.getBirthday());
//		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MMMM-dd'T'HH:mm:ssZ");
//		jgen.writeStringField("birthday", fmt.print(dt));
//		jgen.writeNumberField("birthdayNumber", value.getBirthday());
//		jgen.writeStringField("gender", value.getGender());
//		jgen.writeNumberField("securityStatus", value.getSecurityStatus());
//		jgen.writeNumberField("accountBalance", value.getAccountBalance());
//		jgen.writeStringField("urlforAvatar", value.getUrlforAvatar());
//		jgen.writeObjectField("lastKnownLocation", value.getLastKnownLocation());
//		jgen.writeObjectField("locationRoles", value.getLocationRoles());
//		jgen.writeObjectField("actions4Pilot", value.getActions4Pilot());

		jgen.writeEndObject();
	}

	// - B U I L D E R
//	public static class Builder {
//		private CredentialSerializer onConstruction;
//
//		public Builder() {
//			this.onConstruction = new CredentialSerializer();
//		}
//
//		public CredentialSerializer build() {
//			return this.onConstruction;
//		}
//	}
}
