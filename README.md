## Read me first

This project is licensed under both LGPLv3 and ASL 2.0. See file LICENSE for more details.

Requires Java 8.

## What this is

This package allows an easy usage of lambdas which can potentially throw exceptions.

Let's take an example; you want to list the real paths (ie, following symbolic links and all) of all
entries in a directory. Right now you have to do this:

```java
Files.list(somedir).map(
    path -> try {
        return path.toRealPath();
    } catch (IOException e) {
        throw new RuntimeException(e);
    }).forEach(System.out::println);
```

With this package you can do this instead:

```java
import static com.github.fge.lambdas.Rethrow.rethrow;

Files.list(somedir).map(rethrow(Path::toRealPath))
    .forEach(System.out::println);
```

## How it works

### New interfaces...

For each functional interface `Foo` usable in `Stream`s, this package defines an interface
`ThrowingFoo`. For instance, here is the interface `ThrowingFunction`:

```java
public interface ThrowingFunction<T, R>
{
    R apply(T t)
        throws Throwable;
}
```

### The wrapper

The wrapper method is defined in class `Rethrow` and simply wraps a `ThrowingFoo` into a `Foo`. For
`Function`:

```java
public static <T, R> Function<T, R> rethrow(final ThrowingFunction<T, R> f)
{
    return t -> {
        try {
            return f.apply(t);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable tooBad) {
            throw new ThrownByLambdaException(tooBad);
        }
    };
}
```

As you can see, all `RuntimeException`s and `Error`s are left _untouched_. Any other `Throwable` is
wrapped into an unchecked `ThrownByLambdaException`, and the cause is set as this `Throwable`.

### Why `Throwable`? Why not just `Exception`?

Because some great candidates for lambdas throw that. One example is all the invocation methods of
[`MethodHandle`](http://docs.oracle.com/javase/8/docs/api/java/lang/invoke/MethodHandle.html)s.

