package com.github.msx80.wiseloader;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.github.msx80.wiseloader.loaders.JarLoader;
import com.github.msx80.wiseloader.loaders.compiler.CompilingLoader;

public class MainWiseloader {

	public static void main(String[] args) throws Exception
	{
		testCompilation();
		BytesLoader loader = new JarLoader(new File("C:\\Users\\niclugat\\dev\\omicron\\demo\\Snake\\SnakeMain.omicron"));
		
		WhitelistClassLoader d = new WhitelistClassLoader(loader, true, WhitelistedJDKClasses.LIST);
		//d.allowClasses("com.github.msx80.omicron.api.Game");
		InputStream is = d.getResourceAsStream("/omicron/demo/snake/sheet2.png");
		System.out.println("Resource: "+is);
		Class<?> c = d.loadClass("omicron.demo.snake.SnakeMain");
		System.out.println(c.newInstance());
	}

	private static void testCompilation() throws Exception 
	{
		
		String demo = Files.readAllLines(Paths.get("Demo.java")).stream().collect(Collectors.joining("\n"));
		System.out.println(demo);
		Map<String, String> classes = new HashMap<>();
		classes.put("wise.demo.Demo", demo);
		WhitelistClassLoader d = new WhitelistClassLoader(new CompilingLoader(classes, new HashMap<>()), true);
		d.allowClasses(WhitelistedJDKClasses.LIST);
		d.allowClasses(
				"java.lang.Class",
				"java.nio.file.Paths"
				);
		Class<?> c = d.loadClass("wise.demo.Demo");
		System.out.println(c.newInstance());
	}

}
