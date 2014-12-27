## Read me first

This project is licensed under both LGPLv3 and ASL 2.0. See file LICENSE for more details.

Requires Java 8.

No further dependencies than the JRE itself is required.

## What this is

This package allows you to use lambdas, methods or interfaces whose only impediment to their usages
as [functional
interfaces](http://docs.oracle.com/javase/8/docs/api/java/lang/FunctionalInterface.html) is the fact
that they throw one or more exception(s).

All functional interfaces used in
[`Stream`](http://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html)s are covered.

## Sample usage

For instance, let's take
[`Path.toRealPath()`](http://docs.oracle.com/javase/8/docs/api/java/nio/file/Path.html#toRealPath-java.nio.file.LinkOption...-);
provided you don't supply any link option, it is pretty close to being a `Function<Path, Path>` or
even a `UnaryOperator<Path>`... Except that it can throw `IOException`!

Therefore, using it as a mapping function in
[`Stream.map()`](http://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html#map-java.util.function.Function-),
for instance, requires that you write some nasty-looking lambda:

```java
// Try and resolve all paths in some directory, following symlinks:
Files.list(someDirectory).map(path -> {
    try {
        return path.toRealPath();
    } catch (IOException e) {
        throw new RuntimeException(e); // or something else
    }
}).etc().etc()
```

With this package, instead, you can do this:

```java
// Throw ThrownByLambdaException if Path::toRealPath fails
Files.list(someDirectory).map(Functions.wrap(Path::toRealPath)).etc().etc()

// Throw a custom exception instead of ThrownByLambdaException
Files.list(someDirectory)
    .map(Functions.wrap(Path::toRealPath).orThrow(MyException.class))
    .etc().etc()

// Return self instead; Path::toRealPath is also a UnaryOperator<Path>
Files.list(someDirectory).map(Operators.wrap(Path::toRealPath).orReturnSelf())
    .etc().etc()
```

You can see more about what you can do [here](https://github.com/fge/throwing-lambdas/wiki/How-to-use).

## Versions

The current version is **0.2.0**. It is available on Maven central. Using
gradle:

```gradle
compile(group: "com.github.fge", name: "throwing-lambdas", version: "0.2.0");
```

Using maven:

```xml
<dependency>
    <groupId>com.github.fge</groupId>
    <artifactId>throwing-lambdas</artifactId>
    <version>0.2.0</version>
</dependency>
```

## Further reading

If you want to see how this works, see [this
page](https://github.com/fge/throwing-lambdas/wiki/How-it-works).

Future plans [here](https://github.com/fge/throwing-lambdas/wiki/Future-plans).

There is also a [FAQ](https://github.com/fge/throwing-lambdas/wiki/FAQ) available.

