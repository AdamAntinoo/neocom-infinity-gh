package org.dimensinfin.eveonline.neocom.infinity.core.exceptions;

import org.junit.Assert;
import org.junit.Test;

public class NeoComNotFoundExceptionTest {
	@Test
	public void constructorsContract() {
		final NeoComNotFoundException exception1 = new NeoComNotFoundException( ErrorInfo.TARGET_NOT_FOUND );
		Assert.assertNotNull( exception1 );
		Assert.assertEquals( "The entity of class <undefined> with identifier 0 is not found.",
				exception1.getMessage() );

		final NeoComNotFoundException exception2 = new NeoComNotFoundException( ErrorInfo.TARGET_NOT_FOUND, "EntityType" );
		Assert.assertNotNull( exception2 );
		Assert.assertEquals( "The entity of class EntityType with identifier 0 is not found.",
				exception2.getMessage() );

		final NeoComNotFoundException exception3 = new NeoComNotFoundException( ErrorInfo.TARGET_NOT_FOUND, "EntityType", "123" );
		Assert.assertNotNull( exception3 );
		Assert.assertEquals( "The entity of class EntityType with identifier 123 is not found.",
				exception3.getMessage() );
	}
}