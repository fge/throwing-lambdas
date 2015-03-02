package com.github.fge.lambdas.v2;

import java.nio.file.Path;
import java.util.function.Function;

public final class Throwing
{
    private Throwing()
    {
        throw new Error("nice try!");
    }

    public static <T, R> ThrowingFunctionChain<T, R> function(
        final ThrowingFunction<T, R> function)
    {
        return new ThrowingFunctionChain<>(function);
    }

    public static void main(final String... args)
    {
        final Function<Path, Path> f = function(Path::toRealPath);
    }
}
