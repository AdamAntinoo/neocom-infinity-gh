package org.dimensinfin.eveonline.neocom.infinity.test.support;

import org.dimensinfin.eveonline.neocom.infinity.support.RequestType;

public abstract class CucumberTableToRequestConverter<T> extends CucumberTableConverter<T> {
    public abstract RequestType getType();
}
