 ![CI](https://github.com/msx80/WiseLoader/actions/workflows/maven.yml/badge.svg) [![JitPack](https://jitpack.io/v/msx80/wiseloader.svg)](https://jitpack.io/#msx80/wiseloader)

# WiseLoader

WiseLoader (Whitelist Secured Environment Loader or something) is a Java Classloader that loads class from a given set of whitelist class names. Attempting to load classes not in the whitelist will result in an error

## Why

Following [JEP 411: Deprecate the Security Manager for Removal](https://openjdk.java.net/jeps/411), it will be impossible to load third party classes in a sandboxed environment. All classes will have the same permission within a VM (typically the permissions of the user running the vm). This will make it exceptionally dangerous to run external, untrusted jars. A typical use case for this is an application plugin system. For example, you build an application (ie, a graphic editor like Gimp) that allow users to develop their own plugins to add new functionalities. Plugins could be distributed as Jars on some "store like" site or something. Now, without a security manager, it will be impossible to grant safety for the final user, without careful inspection of the sources of all plugins. Jars could have malicious code that deletes unrelated user files or worst.

This project explores a different approach. It loads external classes with a classloader that is only allowed to access a predetermined set of classes. You can provide your own list of classes, and/or use a (partial, work in progress) [list of all "safe" JDK classes](https://github.com/msx80/WiseLoader/blob/main/src/main/java/com/github/msx80/wiseloader/WhitelistedJDKClasses.java) that is provided, where "safe" means it only works on data in memory and doesn't deal with files, sockets, classloading, reflection, native interfaces or other unsecure activities.

Without access to classes that communicates in any way with the machine, it should be impossible for an untrusted jar to cause damage.

NOTE: as of now this project is experimental, i'm not sure it can provide complete safety. You're more than welcome to experiment and try and break it!

# How to use

Something like this will be the typical usage:

    BytesLoader bl = new JarLoader(new File(myJarFile));
    WhitelistClassLoader d = new WhitelistClassLoader(bl, WhitelistedJDKClasses.LIST);
    Class<?> c = d.loadClass("untrustedclass.PluginExample");
    MyInterface o = (MyInterface)c.newInstance();

# Limitations

Some generally useful jre classes are not safe, best example being java.lang.System. System has unsafe methods like exit(), loadLibrary(), get/setProperty, etc. Since it also has commonly used stuff like in/out, currentTimeMillis() etc, it can be a problem for plugin writers that can't access them anymore. In this case, the host application can provide their own objects to act as bridges in their API, and expose only the interesting stuff, something like this:

    public class SecureSystem {
      public static long currentTimeMillis()
      {
        return System.currentTimeMillis();
      }
      ...
    }
    
(The SecureSystem will need to be whitelisted, obviously).

# How it works

When the classloader is asked to load a class, it checks if it's whitelisted. If it is, it first try to load it from the parent (the standard) classloader, if it doesn't find it it loads from the "secured context", ie from the jar passed to the WhitelistClassLoader. If the class is not whitelisted, an attempt is made to load it from the secured context (with some exception: you can't load classes from the java* package), failing that an exception is thrown.
