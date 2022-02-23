package com.github.msx80.wiseloader.loaders.compiler;

public class CompilationError extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3724227920727781437L;

	public CompilationError() {
	}

	public CompilationError(String message) {
		super(message);
	}

	public CompilationError(Throwable cause) {
		super(cause);
	}

	public CompilationError(String message, Throwable cause) {
		super(message, cause);
	}

	public CompilationError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
