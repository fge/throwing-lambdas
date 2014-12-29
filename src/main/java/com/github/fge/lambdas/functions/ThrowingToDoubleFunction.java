package com.github.fge.lambdas.functions;

import com.github.fge.lambdas.ThrowablesFactory;
import com.github.fge.lambdas.ThrowingFunctionalInterface;
import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.ToDoubleFunction;

/**
 * A throwing {@link ToDoubleFunction}
 *
 * @param <T> type parameter for the argument to that function
 */
@FunctionalInterface
public interface ThrowingToDoubleFunction<T>
    extends ToDoubleFunction<T>,
    ThrowingFunctionalInterface<ThrowingToDoubleFunction<T>, ToDoubleFunction<T>>
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
        } catch (Throwable tooBad) {
            throw new ThrownByLambdaException(tooBad);
        }
    }

    @Override
    default ThrowingToDoubleFunction<T> orTryWith(
        ThrowingToDoubleFunction<T> other)
    {
        return value -> {
            try {
                return doApplyAsDouble(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return other.applyAsDouble(value);
            }
        };
    }

    @Override
    default ToDoubleFunction<T> fallbackTo(ToDoubleFunction<T> fallback)
    {
        return value -> {
            try {
                return doApplyAsDouble(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return fallback.applyAsDouble(value);
            }
        };
    }

    @Override
    default <E extends RuntimeException> ToDoubleFunction<T> orThrow(
        Class<E> exceptionClass)
    {
        return value -> {
            try {
                return doApplyAsDouble(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable tooBad) {
                throw ThrowablesFactory.INSTANCE.get(exceptionClass, tooBad);
            }
        };
    }

    default ToDoubleFunction<T> orReturn(double defaultValue)
    {
        return value -> {
            try {
                return doApplyAsDouble(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return defaultValue;
            }
        };
    }
}
