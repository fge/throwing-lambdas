## Read me first

This project is licensed under both LGPLv3 and ASL 2.0. See file LICENSE for more details.

Requires Java 8.

## What this is

This package allows an easy usage of lambdas which can potentially throw exceptions.

Let's take an example; you want to list the real paths (ie, following symbolic links and all) of all
entries in a directory. Right now you have to do this:

```java
Files.list(somedir).map(
    path -> {
        try {
            return path.toRealPath();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }).forEach(System.out::println);
```

With this package you can do this instead:

```java
import static com.github.fge.lambdas.functions.Functions.rethrow;

Files.list(somedir).map(rethrow(Path::toRealPath))
    .forEach(System.out::println);
```

## How it works

### New interfaces...

_Credits where they are due: thanks to [Vincentyfication](https://github.com/Vincentyification) for
the design idea_

For each functional interface `Foo` usable in `Stream`s, this package defines an interface
`ThrowingFoo`. For instance, here is the interface `ThrowingFunction`:

```java
public interface ThrowingFunction<T, R>
    extends Function<T, R>
{
    R doApply(T t)
        throws Throwable;

    @Override
    default R apply(T t)
    {
        try {
            return doApply(t);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable tooBad) {
            throw new ThrownByLambdaException(tooBad);
        }
    }
}
```

As you can see, all `RuntimeException`s and `Error`s are left _untouched_. Any other `Throwable` is
wrapped into an unchecked `ThrownByLambdaException`, and the cause is set as this `Throwable`.

### The wrapper

The wrapper method is defined in a utility class and simply wraps a `ThrowingFoo` into a `Foo`.  For
`Function`, this is defined in class `Functions`:

```java
public static <T, R> Function<T, R> rethrow(final ThrowingFunction<T, R> f)
{
    return f;
}
```

### Why `Throwable`? Why not just `Exception`?

Because some great candidates for lambdas throw that. One example is all the invocation methods of
[`MethodHandle`](http://docs.oracle.com/javase/8/docs/api/java/lang/invoke/MethodHandle.html)s.

### Why don't these interfaces implement `Serializable`?

"Serialization is, unquestionably, the worst language feature ever added to any language that made
it out of the nursery [...] And it's a gift that keeps on giving".

This is not me saying it, it's [Brian Goetz](https://www.youtube.com/watch?v=C_QbkGU_lqY#t=45m35s).
You know the name, right?

Long story short: a serializable `@FunctionalInterface` ruins linkage performance and most
optimizations which make up the strength of lambdas are they are implemented by the JVM. You _did_
notice that none of the new interfaces didn't implement it, right? Well, that's the reason why.

Therefore, any request to make them `Serializable` will be rejected with a flat out "NO", and the
link to the video above will be added to any and all such requests.

Don't even ask. That won't happen.

