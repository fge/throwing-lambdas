package com.github.fge.lambdas.functions.longfunctions;

import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.LongToDoubleFunction;

public interface ThrowingLongToDoubleFunction
    extends LongToDoubleFunction
{
    double doApplyAsDouble(long value)
        throws Throwable;

    @Override
    default double applyAsDouble(long value)
    {
        try {
            return doApplyAsDouble(value);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable tooBad) {
            throw new ThrownByLambdaException(tooBad);
        }
    }
}
