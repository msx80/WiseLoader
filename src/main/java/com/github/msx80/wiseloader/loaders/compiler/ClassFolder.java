package com.github.msx80.wiseloader.loaders.compiler;

import java.util.List;
import java.util.Set;

import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;

public interface ClassFolder {

	List<JavaFileObject> list(String path, Set<Kind> kinds);

}
