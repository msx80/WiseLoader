package com.github.msx80.wiseloader;


/**
 * Load bytes from some source, by name. 
 *
 */
public interface BytesLoader 
{
	public byte[] loadFile(String name) throws Exception;
}
