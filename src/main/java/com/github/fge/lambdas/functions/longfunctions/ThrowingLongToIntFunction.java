package com.github.fge.lambdas.functions.longfunctions;

import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.LongToIntFunction;

@FunctionalInterface
public interface ThrowingLongToIntFunction
    extends LongToIntFunction
{
    int doApplyAsInt(long value)
        throws Throwable;

    @Override
    default int applyAsInt(long value)
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
