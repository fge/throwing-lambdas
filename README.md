## Read me first

This project is licensed under both LGPLv3 and ASL 2.0. See file LICENSE for more details.

Requires Java 8.

## What this is

This package allows an easy usage of lambdas which can potentially throw exceptions. Its focus is
first and foremost on all functional interfaces used in streams (either general streams or their
primitive specializations: `IntStream`, `LongStream` and `DoubleStream`).

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

## Further reading

A quick usage guide is [here](https://github.com/fge/throwing-lambdas/wiki/How-to-use).

If you want to see how this works, see [this
page](https://github.com/fge/throwing-lambdas/wiki/How-it-works).

Future plans [here](https://github.com/fge/throwing-lambdas/wiki/Future-plans).

There is also a [FAQ](https://github.com/fge/throwing-lambdas/wiki/FAQ) available.

