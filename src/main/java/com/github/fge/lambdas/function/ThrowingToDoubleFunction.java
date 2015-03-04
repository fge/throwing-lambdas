package com.github.fge.lambdas.function;

import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.ToDoubleFunction;

/**
 * A throwing {@link ToDoubleFunction}
 *
 * @param <T> type parameter for the argument to that function
 */
@FunctionalInterface
public interface ThrowingToDoubleFunction<T>
    extends ToDoubleFunction<T>
{
    double doApplyAsDouble(T value)
        throws Throwable;

    @Override
    default double applyAsDouble(T value)
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
