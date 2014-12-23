package com.github.fge.lambdas;


import com.github.fge.lambdas.functions.ThrowingFunction;
import com.github.fge.lambdas.functions.ThrowingIntFunction;
import com.github.fge.lambdas.functions.ThrowingIntToDoubleFunction;
import com.github.fge.lambdas.functions.ThrowingIntToLongFunction;
import com.github.fge.lambdas.functions.ThrowingLongFunction;

import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntToLongFunction;
import java.util.function.LongFunction;

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

    public static <R> LongFunction<R> rethrow(final ThrowingLongFunction<R> f)
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

    public static IntToDoubleFunction rethrow(
        final ThrowingIntToDoubleFunction f)
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

    public static IntToLongFunction rethrow(final ThrowingIntToLongFunction f)
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
