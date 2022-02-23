package com.github.msx80.wiseloader.loaders.compiler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.URI;

import javax.tools.SimpleJavaFileObject;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.model.FileHeader;

public class JarFileObject extends SimpleJavaFileObject implements Inferable {

	private ZipFile zip;
	private FileHeader f;

	public JarFileObject(ZipFile zip, FileHeader f) {
		super(URI.create("jar:///"+(zip.getFile().toString().replace('\\', '/'))+"!"+f.getFileName()), Kind.CLASS);
		this.zip = zip;
		this.f = f;
	}

	@Override
	public InputStream openInputStream() throws IOException {
		
		return zip.getInputStream(f);
	}

	@Override
	public OutputStream openOutputStream() throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Reader openReader(boolean ignoreEncodingErrors) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Writer openWriter() throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String inferBinaryName() {
		String s = f.getFileName();
		s = s.substring(0, s.length()-6); // .class
		s = s.replace('/', '.');
		return s;
	}


}
