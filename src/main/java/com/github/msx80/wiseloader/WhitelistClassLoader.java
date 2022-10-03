package com.github.msx80.wiseloader;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


public class WhitelistClassLoader extends ArbitratorClassLoader 
{
    private final Set<String> allowedClasses = new HashSet<>();
	
	public WhitelistClassLoader(BytesLoader loader, String... allowedClasses) 
	{
		super(loader);
		this.allowedClasses.addAll(Arrays.asList(allowedClasses));
	}
    
	public WhitelistClassLoader(BytesLoader loader, ClassLoader parent, String... allowedClasses) 
	{
		super(loader, parent);
		this.allowedClasses.addAll(Arrays.asList(allowedClasses));
	}

	public WhitelistClassLoader allowClasses(Collection<String> classes)
	{
		this.allowedClasses.addAll(classes);
		return this;
	}

	public WhitelistClassLoader allowClasses(String... classes)
	{
		this.allowedClasses.addAll(Arrays.asList(classes));
		return this;
	}
	
	@Override
	protected boolean allowClass(String name) 
	{
		return allowedClasses.contains(name);
	}

}