package com.github.msx80.wiseloader.loaders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.github.msx80.wiseloader.BytesLoader;

public class MultiBytesLoader implements BytesLoader
{
	List<BytesLoader> loaders = new ArrayList<>();

	public MultiBytesLoader(BytesLoader... loaders) {
		this.loaders.addAll(Arrays.asList(loaders));
	}
	
	public MultiBytesLoader(List<BytesLoader> loaders) {
		this.loaders.addAll(loaders);
	}
	
	public void add(BytesLoader loader)
	{
		this.loaders.add(loader);
	}

	@Override
	public byte[] loadFile(String name) throws Exception {
		for (BytesLoader loader : loaders) 
		{
			byte[] data = loader.loadFile(name);
			if (data!= null) {
				return data;
			}
		}
		return null;
	}

	
}
