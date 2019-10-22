package org.dimensinfin.eveonline.neocom.infinity.core;

import org.junit.Assert;
import org.junit.Test;

import org.dimensinfin.eveonline.neocom.infinity.core.exceptions.ErrorInfo;
import org.dimensinfin.eveonline.neocom.infinity.core.exceptions.NeoComSBException;

public class NeoComSBExceptionTest {
	@Test
	public void constructorErrorInfo() {
		final NeoComSBException exception = new NeoComSBException( ErrorInfo.AUTHORIZATION_TRANSLATION );
		Assert.assertNotNull(exception);
	}
}