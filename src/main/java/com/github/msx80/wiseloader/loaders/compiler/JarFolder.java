package com.github.msx80.wiseloader.loaders.compiler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;

public class JarFolder implements ClassFolder {

	ZipFile zip;
	private List<FileHeader> headers;
	
	public JarFolder(File f)
	{
		zip = new ZipFile(f);
		try {
			headers = zip.getFileHeaders();
		} catch (ZipException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public List<JavaFileObject> list(String path, Set<Kind> kinds) {
		List<JavaFileObject> res = new ArrayList<JavaFileObject>();
		if(kinds.contains(Kind.CLASS))
		{
			for (FileHeader f : headers) {
				if(f.getFileName().startsWith(path) && f.getFileName().endsWith(".class"))
				{
					if(!f.isDirectory())
					{
						String rem = f.getFileName().substring(path.length()+1);
						if(!rem.contains("/"))
						{
							res.add(new JarFileObject(zip, f));
						}
					}
				}
			}
		}
		return res;
	}

}
