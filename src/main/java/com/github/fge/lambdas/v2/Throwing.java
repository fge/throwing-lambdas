package com.github.fge.lambdas.v2;

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
}
