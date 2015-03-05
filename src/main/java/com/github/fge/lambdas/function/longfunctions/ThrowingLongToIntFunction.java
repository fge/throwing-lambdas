package com.github.fge.lambdas.function.longfunctions;

import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.LongToIntFunction;

/**
 * A throwing {@link LongToIntFunction}
 */
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
        } catch (Throwable throwable) {
            throw new ThrownByLambdaException(throwable);
        }
    }
}
