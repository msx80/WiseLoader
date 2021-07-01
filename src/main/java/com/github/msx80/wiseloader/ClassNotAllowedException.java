package com.github.msx80.wiseloader;

public class ClassNotAllowedException extends ClassNotFoundException {

	private static final long serialVersionUID = -8134219137661015582L;

	public ClassNotAllowedException() {
	}

	public ClassNotAllowedException(String s) {
		super(s);
	}

	public ClassNotAllowedException(String s, Throwable ex) {
		super(s, ex);
	}

}
