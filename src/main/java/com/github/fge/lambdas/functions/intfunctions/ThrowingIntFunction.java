package com.github.fge.lambdas.functions.intfunctions;

import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.IntFunction;

/**
 * A throwing {@link IntFunction}
 *
 * @param <R> parameter type of the return value of this function
 */
@FunctionalInterface
public interface ThrowingIntFunction<R>
    extends IntFunction<R>
{
    R doApply(int value)
        throws Throwable;

    @Override
    default R apply(int value)
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
