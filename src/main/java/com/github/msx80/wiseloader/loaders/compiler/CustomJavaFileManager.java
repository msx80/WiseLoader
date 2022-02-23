package com.github.msx80.wiseloader.loaders.compiler;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.tools.FileObject;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;

class CustomJavaFileManager implements JavaFileManager {
    private final StandardJavaFileManager fileManager;
    private final Map<String, ByteArrayOutputStream> buffers = new LinkedHashMap<String, ByteArrayOutputStream>();
	private ClassFolder[] folders;

    CustomJavaFileManager(StandardJavaFileManager fileManager, ClassFolder... folders) {
        this.fileManager = fileManager;
        this.folders = folders;
    }



    public Iterable<JavaFileObject> list(Location location, String packageName, Set<Kind> kinds, boolean recurse) throws IOException {
    	if(location.equals(StandardLocation.PLATFORM_CLASS_PATH)) 
    	{
    		// for platform stuff, lets call the parent manager
	    	return fileManager.list(location, packageName, kinds, recurse);
    	}
    	else
    	{
    		// search within provided ClassFolders
    		List<JavaFileObject> res = new ArrayList<>();
    		String path = packageName.replace('.', '/');
    		for (ClassFolder cf : folders) {
				res.addAll(cf.list(path, kinds));
			}
    		return res;
    	}
    }

    public String inferBinaryName(Location location, JavaFileObject file) {
    	if(file instanceof Inferable)
    	{
    		return ((Inferable)file).inferBinaryName();
    	}
        return fileManager.inferBinaryName(location, file);
    }

    public boolean isSameFile(FileObject a, FileObject b) {
        return fileManager.isSameFile(a, b);
    }

    public boolean handleOption(String current, Iterator<String> remaining) {
        return fileManager.handleOption(current, remaining);
    }

    public boolean hasLocation(Location location) {
        return fileManager.hasLocation(location);
    }

    public JavaFileObject getJavaFileForInput(Location location, String className, Kind kind) throws IOException {
        if (location == StandardLocation.CLASS_OUTPUT && buffers.containsKey(className) && kind == Kind.CLASS) {
            final byte[] bytes = buffers.get(className).toByteArray();
            return new SimpleJavaFileObject(URI.create(className), kind) {
                
                public InputStream openInputStream() {
                    return new ByteArrayInputStream(bytes);
                }
            };
        }
        JavaFileObject jfo = fileManager.getJavaFileForInput(location, className, kind);
        System.out.println("Returning java file: "+jfo);
        return jfo;
    }

    public JavaFileObject getJavaFileForOutput(Location location, final String className, Kind kind, FileObject sibling) throws IOException {
        return new SimpleJavaFileObject(URI.create(className), kind) {
            
            public OutputStream openOutputStream() {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                buffers.put(className, baos);
                return baos;
            }
        };
    }

    public FileObject getFileForInput(Location location, String packageName, String relativeName) throws IOException {
        return fileManager.getFileForInput(location, packageName, relativeName);
    }

    public FileObject getFileForOutput(Location location, String packageName, String relativeName, FileObject sibling) throws IOException {
        return fileManager.getFileForOutput(location, packageName, relativeName, sibling);
    }

    public void flush() throws IOException {
        // Do nothing
    }

    public void close() throws IOException {
        fileManager.close();
    }

    public int isSupportedOption(String option) {
        return fileManager.isSupportedOption(option);
    }

    public void clearBuffers() {
        buffers.clear();
    }

     public Map<String, byte[]> getAllBuffers() {
        Map<String, byte[]> ret = new LinkedHashMap<String, byte[]>(buffers.size() * 2);
        for (Map.Entry<String, ByteArrayOutputStream> entry : buffers.entrySet()) {
            ret.put(entry.getKey(), entry.getValue().toByteArray());
        }
        return ret;
    }
     
     public ClassLoader getClassLoader(Location location) {
     	//throw new UnsupportedOperationException();
         return new ClassLoader()
         {
         	 protected Class<?> findClass(String name) throws ClassNotFoundException {
         	        throw new UnsupportedOperationException();
         	    }
             protected Class<?> loadClass(String name, boolean resolve)
                     throws ClassNotFoundException
                 {
             	throw new UnsupportedOperationException();
                 }
         	public Class<?> loadClass(String name) throws ClassNotFoundException 
             {
         		throw new UnsupportedOperationException();
         	}	
         }; 
     }
     
     //
     
     /*
       
       RESTORE THESE TO MAKE IT RUN ON JAVA 9+
       
     @Override
     public Iterable<Set<Location>> listLocationsForModules(Location location) throws IOException {
     	return fileManager.listLocationsForModules(location);
     }
     
     @Override 
     public String inferModuleName(Location location) throws IOException {
         
         return fileManager.inferModuleName(location);
     }
     
	*/

}
