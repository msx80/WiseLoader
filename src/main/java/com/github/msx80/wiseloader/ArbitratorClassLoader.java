package com.github.msx80.wiseloader;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;

public abstract class ArbitratorClassLoader extends ClassLoader 
{

    private ClassLoader parent = ArbitratorClassLoader.class.getClassLoader();
	private BytesLoader loader;
    
	public ArbitratorClassLoader(BytesLoader loader) 
	{
		this.loader = loader;

	}
    
	public ArbitratorClassLoader(BytesLoader loader, ClassLoader parent) 
	{
		this.loader = loader;
		this.parent = parent;
	}

    @Override
	public Class<?> loadClass(String name) throws ClassNotFoundException 
    {
    	System.out.println("Accessing class "+name);
    	
    	if(allowClass(name))
    	{
    		return loadAllowedClass(name);
    	}
    	else
    	{
    		if(name.startsWith("java"))
    		{
    			throw new ClassNotAllowedException(name, "Class "+name+" is not whitelisted.");
    		}
    		Class<?> a = loadClassFromSecuredContext(name);
    		if(a==null) throw new ClassNotAllowedException(name, "Class "+name+" is not whitelisted and was not found secured context.");
    		return a;
    	}
	}

	protected abstract boolean allowClass(String name);

	private Class<?> loadClassFromSecuredContext(String name) throws ClassNotFoundException {
		
		// Just try in custom classloader ?
		
		byte[] newClassData;
		try {
			newClassData = loadNewClass(name);
		} catch (Exception e1) {
			throw new ClassNotFoundException("Error loading class", e1);
		}
		if (newClassData != null) {
			System.out.println("Loaded non whitelisted class from secured context: "+name);
			return define(newClassData, name);
		} else {
			return null;
		}
	}

	private Class<?> loadAllowedClass(String name) throws ClassNotFoundException {
		// allowed class, try the parent classloader first and eventually try the custom loader
		
		try {
			return parent.loadClass(name);
		} catch (ClassNotFoundException e) {
			Class<?> a = loadClassFromSecuredContext(name);
			if(a==null) throw new ClassNotFoundException("Class "+name+" not found.");
			return a;
		}
	}


	private byte[] loadNewClass(String name) throws Exception {
		System.out.println("Loading bytes for class " + name);
		String filePath = toFilePath(name);
		return loader.loadFile(filePath);
		
	}
	private Class<?> define(byte[] classData, String name) {
		Class<?> clazz = defineClass(name, classData, 0, classData.length);
		if (clazz != null) {
			if (clazz.getPackage() == null) {
				definePackage(name.replaceAll("\\.\\w+$", ""), null, null, null, null, null, null, null);
			}
			resolveClass(clazz);
		}
		return clazz;
	}

	@Override
	public InputStream getResourceAsStream(String name) {
		
			try {
				byte[] data = loader.loadFile(name);
				if (data== null)
					return null;
				else
					return new ByteArrayInputStream(data);
				
			} catch (Exception e) {
				throw new RuntimeException("Unable to read resource", e);
			}
	    }

	
	public static String toFilePath(String name) {
		return name.replaceAll("\\.", "/") + ".class";
	}
	
	@Override
	public URL getResource(String name) {
		throw new UnsupportedOperationException("getResource can't return a meaningful URL, use getResourceAsStream");
	}

}