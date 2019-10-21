package org.dimensinfin.eveonline.neocom.infinity.core.exceptions;

import org.springframework.http.HttpStatus;

public class NeoComNotFoundException extends RuntimeException {
	protected String sourceClass;
	protected String sourceMethod;
	private ErrorInfo errorInfo;
	private String message;

	private NeoComNotFoundException() {
		final StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
		final StackTraceElement stackElement = stacktrace[3]; // This is to check if we are using Dalvik
		this.sourceMethod = stackElement.getMethodName();
		this.sourceClass = stackElement.getClassName();
	}

	public NeoComNotFoundException( final ErrorInfo errorInfo, final String... arguments ) {
		this();
		this.errorInfo = errorInfo;
		this.message = this.prepareMessage( arguments );
	}

	public String getMessage() {
		return this.message;
	}

	private String prepareMessage( final String... arguments ) {
		if (arguments.length < 1) return this.errorInfo.getErrorMessage( "<undefined>", "0" );
		if (arguments.length < 2) return this.errorInfo.getErrorMessage( arguments[0], "0" );
		return this.errorInfo.getErrorMessage( arguments );
	}

	public String getSourceClass() {
		return this.sourceClass;
	}

	public String getSourceMethod() {
		return this.sourceMethod;
	}

	public HttpStatus getHttpStatus() {
		return this.errorInfo.status;
	}
}
