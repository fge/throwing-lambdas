package com.github.fge.lambdas.functions;

import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.ToDoubleFunction;
import java.util.function.ToLongFunction;

/**
 * A throwing {@link ToDoubleFunction}
 *
 * @param <T> type parameter for the argument to that function
 */
@FunctionalInterface
public interface ThrowingToLongFunction<T>
    extends ToLongFunction<T>
{
    long doApplyAsLong(T value)
        throws Throwable;

    @Override
    default long applyAsLong(T value)
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
