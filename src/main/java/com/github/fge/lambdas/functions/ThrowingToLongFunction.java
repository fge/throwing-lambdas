package com.github.fge.lambdas.functions;

import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.ToLongFunction;

public interface ThrowingToLongFunction<T>
    extends ToLongFunction<T>
{
    long doApplyAsLong(T value)
        throws Throwable;

    @Override
    default long applyAsLong(T value)
    {
        try {
            return doApplyAsLong(value);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable tooBad) {
            throw new ThrownByLambdaException(tooBad);
        }
    }
}
