package org.dimensinfin.eveonline.neocom.infinity.test.support;

public abstract class CucumberTableToRequestConverter<T> extends CucumberTableConverter<T> {
    public abstract RequestType getType();
}
