package com.github.fge.lambdas.function;

import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;

/**
 * A throwing {@link ToDoubleFunction}
 *
 * @param <T> type parameter for the argument to that function
 */
@FunctionalInterface
public interface ThrowingToIntFunction<T>
    extends ToIntFunction<T>
{
    int doApplyAsInt(T value)
        throws Throwable;

    @Override
    default int applyAsInt(T value)
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
