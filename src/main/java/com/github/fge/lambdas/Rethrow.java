package com.github.fge.lambdas;


import com.github.fge.lambdas.functions.ThrowingFunction;
import com.github.fge.lambdas.functions.ThrowingIntFunction;

import java.util.function.Function;
import java.util.function.IntFunction;

public class Rethrow
{
    public static <T, R> Function<T, R> rethrow(final ThrowingFunction<T, R> f)
    {
        return t -> {
            try {
                return f.apply(t);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable tooBad) {
                throw new ThrownFromLambdaException(tooBad);
            }
        };
    }

    public static <R> IntFunction<R> rethrow(final ThrowingIntFunction<R> f)
    {
        return t -> {
            try {
                return f.apply(t);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable tooBad) {
                throw new ThrownFromLambdaException(tooBad);
            }
        };
    }
}
