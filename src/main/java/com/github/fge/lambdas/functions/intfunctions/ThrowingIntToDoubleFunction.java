package com.github.fge.lambdas.functions.intfunctions;

import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.IntToDoubleFunction;

public interface ThrowingIntToDoubleFunction
    extends IntToDoubleFunction
{
    double doApplyAsDouble(int value)
        throws Throwable;

    @Override
    default double applyAsDouble(int value)
    {
        try {
            return doApplyAsDouble(value);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable tooBad) {
            throw new ThrownByLambdaException(tooBad);
        }
    };
}
