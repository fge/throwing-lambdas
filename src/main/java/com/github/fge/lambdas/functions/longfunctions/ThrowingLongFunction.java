package com.github.fge.lambdas.functions.longfunctions;

import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.LongFunction;

/**
 * A throwing {@link LongFunction}
 *
 * @param <R> parameter type of the return value of this function
 */
@FunctionalInterface
public interface ThrowingLongFunction<R>
    extends LongFunction<R>
{
    R doApply(long value)
        throws Throwable;

    @Override
    default R apply(long value)
    {
        try {
            return doApply(value);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable throwable) {
            throw new ThrownByLambdaException(throwable);
        }
    }
}
