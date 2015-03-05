package com.github.fge.lambdas.function.longfunctions;

import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.LongToDoubleFunction;

/**
 * A throwing {@link LongToDoubleFunction}
 */
@FunctionalInterface
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
        } catch (Throwable throwable) {
            throw new ThrownByLambdaException(throwable);
        }
    }
}
