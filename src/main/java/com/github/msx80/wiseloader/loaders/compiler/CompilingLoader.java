package com.github.msx80.wiseloader.loaders.compiler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticListener;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import com.github.msx80.wiseloader.BytesLoader;
import com.github.msx80.wiseloader.WhitelistClassLoader;

/**
 * A BytesLoader that tries to compile a set of java sources, 
 * to provide the required bytecode. 
 * 
 */
public class CompilingLoader implements BytesLoader {

	private Map<String, byte[]> files;
	private ClassFolder[] classFolders;
	
	public CompilingLoader( Map<String, String> javaSources, Map<String, byte[]> extraResourceFiles, ClassFolder... classFolders)
	{
		this.files = new HashMap<>();
		this.classFolders = classFolders;
		files.putAll(extraResourceFiles);
		files.putAll(compileFromJava(javaSources));
	}
		
	boolean errors;
	
	@Override
	public byte[] loadFile(String filePath) {
		
		byte[] b = files.get(filePath);
		
		return b;
	}
	
	private Map<String, byte[]> compileFromJava(Map<String, String> sources) 
	{
        
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		if(compiler==null) throw new CompilationError("JavaCompiler not found. Not running on a jdk?");

		Map<String, JavaFileObject> javaFileObjects = new HashMap<String, JavaFileObject>();

		for (Entry<String, String> e : sources.entrySet()) {
			javaFileObjects.put(e.getKey(), new JavaSourceFromString(e.getKey(), e.getValue()));
		}
		
		List<Diagnostic<? extends JavaFileObject>> errors = new ArrayList<Diagnostic<? extends JavaFileObject>>();
		                 
         Collection<JavaFileObject> compilationUnits = javaFileObjects.values();
        
         StandardJavaFileManager s_standardJavaFileManager = compiler.getStandardFileManager(null, null, null);
         CustomJavaFileManager s_fileManager = new CustomJavaFileManager(s_standardJavaFileManager, classFolders);
         
        compiler.getTask(null, s_fileManager, new DiagnosticListener<JavaFileObject>() {
            @Override
            public void report(Diagnostic<? extends JavaFileObject> diagnostic) {
                if (diagnostic.getKind() == Diagnostic.Kind.ERROR) {
                    errors.add(diagnostic);
                }
            }
        }, null, null, compilationUnits).call();
        
        if (!errors.isEmpty()) {
			throw new CompilationError(errors.get(0).toString());
		}
        
        Map<String, byte[]> comp = s_fileManager.getAllBuffers();
        Map<String, byte[]> result = new HashMap<>();
        
        for (String cn : comp.keySet()) {
			System.out.println("Compiled: "+cn);
			result.put(WhitelistClassLoader.toFilePath(cn), comp.get(cn));
		}
       
        return result;
    }

	
}
