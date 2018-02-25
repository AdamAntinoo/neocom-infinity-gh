//  PROJECT:     NeoCom.Microservices (NEOC.MS)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2017-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Java 1.8 / SpringBoot-1.3.5 / Angular 5.0
//  DESCRIPTION: This is the SpringBoot MicroServices module to run the backend services to complete the web
//               application based on Angular+SB. This is the web version for the NeoCom Android native
//               application. Most of the source code is common to both platforms and this module includes
//               the source for the specific functionality for the backend services.
package org.dimensinfin.eveonline.neocom.exception;

/**
 * @author Adam Antinoo
 */
// - CLASS IMPLEMENTATION ...................................................................................
public class JsonExceptionInstance {
	// - S T A T I C - S E C T I O N ..........................................................................
//	private static Logger logger = LoggerFactory.getLogger("JsonExceptionInstance");

	// - F I E L D - S E C T I O N ............................................................................
	private String errorMessage = "-NO MESSAGE-";

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public JsonExceptionInstance( final String message ) {
		errorMessage = message;
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	public String toJson() {
		return new StringBuffer()
				.append("{").append('\n')
				.append(quote("jsonClass")).append(":").append(quote("JsonException")).append(",")
				.append(quote("message")).append(":").append(quote(errorMessage)).append(" ")
				.append("}")
				.toString();
	}

	private String quote( final String content ) {
		return String.format("\"%s\"", content);
	}

	@Override
	public String toString() {
		return new StringBuffer("JsonExceptionInstance[")
				.append("message:").append(errorMessage).append(" ")
				.append("]")
//				.append("->").append(super.toString())
				.toString();
	}
}

// - UNUSED CODE ............................................................................................
//[01]
