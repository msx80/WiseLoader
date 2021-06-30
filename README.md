# WiseLoader

WiseLoader (Whitelist Secured Environment Loader or something) is a Java Classloader that loads class from a given set of whitelist class names. Attempting to load classes not in the whitelist will result in an error

## Why

Following [JEP 411: Deprecate the Security Manager for Removal](https://openjdk.java.net/jeps/411), it will be impossible to load third party classes in a sandboxed environment. All classes will have the same permission within a VM (typically the permissions of the user running the vm). This will make it exceptionally dangerous to run external, untrusted jars. A typical use case for this is an application plugin system. For example, you build an application (ie, a graphic editor like Gimp) that allow users to develop their own plugins to add new functionalities. Plugins could be distributed as Jars on some "store like" site or something. Now, without a security manager, it will be impossible to grant safety for the final user, without careful inspection of the sources of all plugins. Jars could have malicious code that deletes unrelated user files or worst.

This project explores a different approach. It loads external classes with a classloader that is only allowed to access a predetermined set of classes. A (partial, work in progress) list of all "safe" JDK classes is provided, where "safe" means it only works on data in memory and doesn't deal with files, sockets, classloading, reflection, native interfaces or other unsecure activities.

Without access to classes that communicates in any way with the machine, it should be impossible for an untrusted jar to cause damage.

NOTE: this is experimental and needs to be examined carefully.

# How to use

Something like this will be the typical usage:

    BytesLoader bl = new JarLoader(new File(myJarFile));
    WhitelistClassLoader d = new WhitelistClassLoader(bl, WhitelistedJDKClasses.LIST);
    Class<?> c = d.loadClass("untrustedclass.PluginExample");
    c.newInstance();
