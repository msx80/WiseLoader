package com.github.msx80.wiseloader.loaders;

import java.io.File;
import java.io.IOException;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import com.github.msx80.wiseloader.BytesLoader;

public class JarLoader implements BytesLoader {
	File file;
	JarFile jarFile;
	
	public JarLoader(File file)  {
		this.file = file;
		try {
			this.jarFile = new JarFile(file);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public byte[] loadFile(String filePath) throws Exception {
		ZipEntry entry = jarFile.getJarEntry(filePath);
		if (entry == null) {
			if(filePath.startsWith("/"))
			{
				// retry without leading slash
				return loadFile(filePath.substring(1));
			}
			else
			{
				return null;
			}
		}
		try {
			return FileUtil.readData(jarFile.getInputStream(entry));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}


	public byte[] loadFileInternal(String cf) {
		ZipEntry entry = jarFile.getJarEntry(cf);
		if (entry == null) {
			
				return null;
			
		}
		try {
			return FileUtil.readData(jarFile.getInputStream(entry));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void finalize() throws Throwable {
		FileUtil.close(jarFile);
		super.finalize();
	}

	public void close() {
		FileUtil.close(jarFile);
		
	}

	
}
