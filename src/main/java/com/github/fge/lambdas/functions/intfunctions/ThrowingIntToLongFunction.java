package com.github.fge.lambdas.functions.intfunctions;

import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.IntToLongFunction;

public interface ThrowingIntToLongFunction
    extends IntToLongFunction
{
    long doApplyAsLong(int value)
        throws Throwable;

    @Override
    default long applyAsLong(int value)
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
