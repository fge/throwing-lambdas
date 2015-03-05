package com.github.fge.lambdas.functions.intfunctions;

import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.IntToDoubleFunction;
import java.util.function.IntToLongFunction;

/**
 * A throwing {@link IntToDoubleFunction}
 */
@FunctionalInterface
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
        } catch (Throwable throwable) {
            throw new ThrownByLambdaException(throwable);
        }
    }
}
