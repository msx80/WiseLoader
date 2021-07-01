package com.github.msx80.wiseloader;

public class WhitelistedJDKClasses {
	
	// TODO to be expanded
	
	public static final String[] LIST = 
		{
			"java.lang.Object",
			"java.lang.Runnable",
			"java.lang.RuntimeException",
			"java.lang.String",
			"java.lang.Enum",
			"java.lang.CharSequence",
			"java.lang.StringBuilder",
			"java.lang.Throwable",
			"java.lang.invoke.LambdaMetafactory",
			"java.util.ArrayList",
			"java.util.LinkedList",
			"java.util.List",
			"java.util.Set",
			"java.util.HashSet",
			"java.util.Vector",
			"java.util.Map",
			"java.util.HashMap",
			"java.util.Collection",
			"java.util.Random",
			"java.util.function.Function",
			"java.util.function.Consumer",
			"java.util.stream.Stream", // imports java.nio.file.Files and java.nio.file.Path but doesn't seems to use it
			"java.util.stream.Collectors",
			"java.nio.charset.Charset",
			"java.lang.Exception",
			"java.io.IOException",
			"java.lang.NoSuchFieldError",
			"java.util.Iterator",
			"java.lang.Math",
			"java.lang.Integer",
			"java.lang.Long",
			"java.lang.Character",
			"java.lang.Float",
			"java.lang.Double",
			"java.util.Stack",
			"java.io.Serializable",
			"java.lang.Comparable",
			"java.util.StringJoiner",
			"java.io.ByteArrayInputStream",
			"java.lang.invoke.StringConcatFactory", // TODO check this one

		};
	

	// Unused, but keep tracks of classes that are known to be unsafe
	// so they don't get researched multiple times.
	@SuppressWarnings("unused")
	private static final String[] BAD_CLASSES = 
		{
			"java.lang.System", // has exit(), loadLibrary(), etc..
			"java.lang.Runtime", // can execute processes, exit() etc..
			"java.lang.Thread", // not exacly bad per se but probably is not ok to start threads
		};
}
