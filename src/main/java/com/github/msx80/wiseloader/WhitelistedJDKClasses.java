package com.github.msx80.wiseloader;

public class WhitelistedJDKClasses {
	
	// TODO to be expanded
	
	public static final String[] LIST = 
		{
				
			"java.math.BigInteger",
			"java.math.BigDecimal",
			"java.io.ByteArrayInputStream",
			"java.io.ByteArrayOutputStream",
			"java.io.InputStream", // abstract, access nothing
			"java.io.OutputStream", // abstract, access nothing
			"java.util.zip.GZIPInputStream", // based on InputStream, no access to resources
			"java.util.zip.GZIPOutputStream", // based on OutputStream, no access to resources
			"java.io.IOException",
			"java.io.Serializable",
			"java.io.EOFException",
			"java.util.Map$Entry",
			"java.util.Arrays",
			"java.lang.CharSequence",
			"java.lang.Character",
			"java.lang.Comparable",
			"java.lang.Double",
			"java.lang.Enum",
			"java.lang.Boolean",
			"java.util.concurrent.Callable",
			"java.lang.Exception",
			"java.lang.Float",
			"java.lang.Integer",
			"java.lang.Iterable",
			"java.lang.Long",
			"java.lang.Math",
			"java.lang.NoSuchFieldError",
			"java.lang.Object",
			"java.lang.Runnable",
			"java.lang.RuntimeException",
			"java.lang.IllegalArgumentException",
			"java.lang.String",
			"java.lang.StringBuilder",
			"java.lang.StringBuffer",
			"java.lang.Throwable",
			"java.lang.invoke.LambdaMetafactory",
			"java.lang.invoke.StringConcatFactory", // TODO check this one
			"java.nio.charset.Charset",
			"java.util.ArrayList",
			"java.util.Collection",
			"java.util.HashMap",
			"java.util.HashSet",
			"java.util.Iterator",
			"java.util.LinkedList",
			"java.util.List",
			"java.util.Map",
			"java.util.Random",
			"java.util.Comparator",
			"java.util.Set",
			"java.util.Stack",
			"java.util.StringJoiner",
			"java.util.Vector",
			"java.util.function.Consumer",
			"java.util.function.Function",
			"java.util.function.BiFunction",
			"java.util.function.IntConsumer",
			"java.util.function.IntPredicate",
			"java.util.function.IntSupplier",
			"java.util.function.Predicate",
			"java.util.function.ToIntFunction",
			"java.util.function.ToDoubleFunction",
			"java.util.stream.IntStream",
			"java.util.function.Supplier",
			"java.util.regex.Pattern",
			"java.util.stream.Collectors",
			"java.util.stream.Stream", // imports java.nio.file.Files and java.nio.file.Path but doesn't seems to use it
			"java.io.Externalizable",
			"java.lang.Cloneable",
			"java.lang.UnsupportedOperationException",
			"java.text.DecimalFormat",
			"java.text.NumberFormat"
		};
	

	// Unused, but keep tracks of classes that are known to be unsafe
	// so they don't get researched multiple times.
	@SuppressWarnings("unused")
	private static final String[] BAD_CLASSES = 
		{
			"java.lang.System", // has exit(), loadLibrary(), etc..
			"java.lang.Runtime", // can execute processes, exit() etc..
			"java.lang.Thread", // not exacly bad per se but probably is not ok to start threads
			"java.lang.Process", // can start external programs
		};
}
