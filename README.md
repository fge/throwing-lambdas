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

The current version is **0.3.0**. It is available on Maven central:

* group: `com.github.fge`;
* artifact: `throwing-lambdas`.

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
// Declare it as a ThrowingFunction
final ThrowingFunction<Path, Path> function = Path::toRealPath;

// Since the argument type and return type are the same, this is also a unary operator:
final ThrowingUnaryOperator<Path> operator = Path::toRealPath;
```

For all functional interfaces used in streams, this package defines a throwing equivalent which
extends the base interface; therefore you can use the variables above directly in streams:

```java
// Since ThrowingFunction extends Function...
Files.list(somedir).map(function).forEach(System.out::println);

// Since ThrowingUnaryOperator extends Operator...
Files.list(somedir).map(operator).forEach(System.out::println);
```

Wrapper also exist so that you don't even need to declare variables before using such methods:

```java
// Function...
Files.list(someDirectory).map(Functions.wrap(Path::toRealPath)).etc().etc()

// UnaryOperator...
Files.list(someDirectory).map(Operators.wrap(Path::toRealPath)).etc().etc()
```

But you can do more; for instance:

```java
// Throw a custom RuntimeException instead of the default ThrownByLambdaException:
Functions.rethrow(Statement::executeQuery).as(MyException.class);

// Fall back to a non throwing version of the interface:
Operators.wrap(Path::toRealPath).fallbackTo(Path::toAbsolutePath);

// Try with another throwing lambda if the first throws an exception, and if both fail launch a
// custom exception. Both methods below "are" BinaryOperator<Path>s:
Operators.tryWith(Files::createLink).orTryWith(Files::copy)
    .orThrow(UncheckedIOException.class);
```

And you can do even more than that. Read [this
page](https://github.com/fge/throwing-lambdas/wiki/How-to-use) for more information.

## Further reading

If you want to see how this works, see [this
page](https://github.com/fge/throwing-lambdas/wiki/How-it-works).

Future plans [here](https://github.com/fge/throwing-lambdas/wiki/Future-plans).

There is also a [FAQ](https://github.com/fge/throwing-lambdas/wiki/FAQ) available.

