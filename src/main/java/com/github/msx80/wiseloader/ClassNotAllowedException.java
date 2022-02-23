package com.github.msx80.wiseloader;

public class ClassNotAllowedException extends ClassNotFoundException {

	private static final long serialVersionUID = -8134219137661015582L;

	public final String className;
	
	public ClassNotAllowedException(String className) {
		this.className = className;
	}

	public ClassNotAllowedException(String className, String s) {
		super(s);
		this.className = className;
	}

	public ClassNotAllowedException(String className, String s, Throwable ex) {
		super(s, ex);
		this.className = className;
	}

}
