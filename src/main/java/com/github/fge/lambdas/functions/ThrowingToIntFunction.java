package com.github.fge.lambdas.functions;

import com.github.fge.lambdas.ThrowablesFactory;
import com.github.fge.lambdas.ThrowingFunctionalInterface;
import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.ToIntFunction;

@FunctionalInterface
public interface ThrowingToIntFunction<T>
    extends ToIntFunction<T>,
    ThrowingFunctionalInterface<ThrowingToIntFunction<T>, ToIntFunction<T>>
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
        } catch (Throwable tooBad) {
            throw new ThrownByLambdaException(tooBad);
        }
    }

    @Override
    default ThrowingToIntFunction<T> orTryWith(ThrowingToIntFunction<T> other)
    {
        return value -> {
            try {
                return doApplyAsInt(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return other.applyAsInt(value);
            }
        };
    }

    @Override
    default ToIntFunction<T> or(ToIntFunction<T> byDefault)
    {
        return value -> {
            try {
                return doApplyAsInt(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return byDefault.applyAsInt(value);
            }
        };
    }

    @Override
    default <E extends RuntimeException> ToIntFunction<T> orThrow(
        Class<E> exceptionClass)
    {
        return value -> {
            try {
                return doApplyAsInt(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable tooBad) {
                throw ThrowablesFactory.INSTANCE.get(exceptionClass, tooBad);
            }
        };
    }

    default ToIntFunction<T> orReturn(int defaultValue)
    {
        return value -> {
            try {
                return doApplyAsInt(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return defaultValue;
            }
        };
    }
}
