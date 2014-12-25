package com.github.fge.lambdas.functions;

import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.ToIntFunction;

public interface ThrowingToIntFunction<T>
    extends ToIntFunction<T>
{
    int doApplyAsInt(T value)
        throws Throwable;

    @Override
    default int applyAsInt(T value)
    {
        try {
            return doApplyAsInt(value);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable tooBad) {
            throw new ThrownByLambdaException(tooBad);
        }
    }
}
