package com.github.msx80.wiseloader.loaders;

import com.github.msx80.wiseloader.BytesLoader;

public class MultiBytesLoader implements BytesLoader
{
	BytesLoader[] loaders;

	public MultiBytesLoader(BytesLoader... loaders) {
		this.loaders = loaders;
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
