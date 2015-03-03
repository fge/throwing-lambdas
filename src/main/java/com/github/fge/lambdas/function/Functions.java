package com.github.fge.lambdas.function;

import com.github.fge.lambdas.Throwing;

/**
 * @deprecated use {@link Throwing} instead
 */
@Deprecated
public final class Functions
{
    private Functions()
    {
        throw new Error("nice try!");
    }

    /**
     * Return a new throwing function chain
     *
     * @param function the throwing function
     * @param <T> argument type parameter
     * @param <R> return type parameter
     * @return a new chain
     *
     * @deprecated use {@link Throwing#function(ThrowingFunction)} instead
     */
    @Deprecated
    public static <T, R> ThrowingFunctionChain<T, R> wrap(
        final ThrowingFunction<T, R> function)
    {
        return new ThrowingFunctionChain<>(function);
    }

    /**
     * Return a new throwing function chain
     *
     * @param function the throwing function
     * @param <T> argument type parameter
     * @param <R> return type parameter
     * @return a new chain
     *
     * @deprecated use {@link Throwing#function(ThrowingFunction)} instead
     */
    @Deprecated
    public static <T, R> ThrowingFunctionChain<T, R> rethrow(
        final ThrowingFunction<T, R> function)
    {
        return wrap(function);
    }

    /**
     * Return a new throwing function chain
     *
     * @param function the throwing function
     * @param <T> argument type parameter
     * @param <R> return type parameter
     * @return a new chain
     *
     * @deprecated use {@link Throwing#function(ThrowingFunction)} instead
     */
    @Deprecated
    public static <T, R> ThrowingFunctionChain<T, R> tryWith(
        final ThrowingFunction<T, R> function)
    {
        return wrap(function);
    }
}
