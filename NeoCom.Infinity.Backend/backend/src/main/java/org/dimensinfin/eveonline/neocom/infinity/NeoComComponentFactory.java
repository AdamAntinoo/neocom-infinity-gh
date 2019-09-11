package org.dimensinfin.eveonline.neocom.infinity;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class NeoComComponentFactory {
    public ObjectMapper jsonMapper = new ObjectMapper();

    public ObjectMapper getJsonMapper() {
        if (null == this.jsonMapper) this.jsonMapper = new ObjectMapper();
        Objects.requireNonNull(this.jsonMapper);
        return this.jsonMapper;
    }
}
