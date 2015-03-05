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

The current version is **0.4.0**. It is available on Maven central:

```groovy
    compile(group: "com.github.fge", name: "throwing-lambdas", version: "0.4.0");
```

## Sample usage

For instance, let's suppose you want to sum the size of all regular files in a file tree.

The JDK provides
[`Files.walk()`](http://docs.oracle.com/javase/8/docs/api/java/nio/file/Files.html#walk-java.nio.file.Path-java.nio.file.FileVisitOption...-)
to walk a file tree; you can therefore filter that stream to list only regular files and obtain the
size using
[`Files.size()`](http://docs.oracle.com/javase/8/docs/api/java/nio/file/Files.html#size-java.nio.file.Path-)...

Except no you cannot; `Files.size()` throws an IOException... Therefore you have to write this:

```java
public static long totalSize(final Path baseDir)
    throws IOException
{
    try (
        final Stream<Path> stream = Files.walk(baseDir);
    ) {
        return stream.filter(Files::isRegularFile)
            .mapToLong(path -> {
                try {
                    return Files.size(path);
                } catch (IOException e) {
                    throw new RuntimeException e;
                }
            })
            .sum();
    }
}
```

You cannot throw checked exceptions from stream and this means writing a lot of boilerplate code
like this, which can quickly become unmaintainable. This package, instead, allows you to write this:

```java
public static long totalSize(final Path baseDir)
    throws IOException
{
    try (
        final Stream<Path> stream = Files.walk(baseDir);
    ) {
        return stream.filter(Files::isRegularFile)
            .mapToLong(Throwing.toLongFunction(Files::size))
            .sum();
    }
}
```

You can do more; for instance:

```java
// Throw a custom RuntimeException instead of the default ThrownByLambdaException:
Throwing.function(Statement::executeQuery).orThrow(MyException.class);

// Try with another throwing lambda if the first throws an exception, and if both fail launch a
// custom exception. Both methods below "are" BinaryOperator<Path>s:
Throwing.binaryOperator(Files::createLink).orTryWith(Files::copy)
    .orThrow(UncheckedIOException.class);
```

And you can do even more than that. Read [this
page](https://github.com/fge/throwing-lambdas/wiki/How-to-use) for more information.

## Further reading

If you want to see how this works, see [this
page](https://github.com/fge/throwing-lambdas/wiki/How-it-works).

There is also a [FAQ](https://github.com/fge/throwing-lambdas/wiki/FAQ) available.

