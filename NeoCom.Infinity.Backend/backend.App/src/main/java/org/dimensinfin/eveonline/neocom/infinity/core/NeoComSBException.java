package org.dimensinfin.eveonline.neocom.infinity.core;

import org.springframework.http.HttpStatus;

public class NeoComSBException extends RuntimeException {
	private String sourceClass;
	private String sourceMethod;
	private Exception rootException;
	private String rootMessage;
	private ErrorInfo errorInfo;

	public NeoComSBException() {
		final StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
		final StackTraceElement stackElement = stacktrace[3]; // This is to check if we are using Dalvik
		this.sourceMethod = stackElement.getMethodName();
		this.sourceClass = stackElement.getClassName();
	}

	public NeoComSBException( final ErrorInfo errorInfo ) {
		this();
		this.errorInfo = errorInfo;
	}

	public NeoComSBException( final Exception rootException ) {
		this();
		this.errorInfo = ErrorInfo.NOT_INTERCEPTED_EXCEPTION;
		this.rootException = rootException;
	}

	public NeoComSBException( final ErrorInfo errorInfo, final Exception rootException ) {
		this(errorInfo);
		this.rootException = rootException;
	}

	public String getMessage() {
		String message = this.errorInfo.errorMessage;
		if (null != this.rootMessage) message = message.concat(":").concat(this.rootMessage);
		if (null != this.rootException) message = message.concat(":").concat(this.rootException.getMessage());
		return message;
	}

	public String getSourceClass() {
		return this.sourceClass;
	}

	public String getSourceMethod() {
		return this.sourceMethod;
	}

	public Exception getRootException() {
		return this.rootException;
	}

	public HttpStatus getHttpStatus() {
		return this.errorInfo.status;
	}

	public String getExceptionType() {
		if ( null != this.rootException) return this.rootException.getClass().getSimpleName();
		else return "NeoComSBException";
	}
}
