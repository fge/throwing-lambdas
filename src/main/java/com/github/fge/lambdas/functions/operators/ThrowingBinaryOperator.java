package com.github.fge.lambdas.functions.operators;

import com.github.fge.lambdas.ThrowablesFactory;
import com.github.fge.lambdas.ThrowingFunctionalInterface;
import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.BinaryOperator;

/**
 * A throwing {@link BinaryOperator}
 *
 * @param <T> parameter type of the two arguments, and return type, of this
 *            binary operator
 */
@FunctionalInterface
public interface ThrowingBinaryOperator<T>
    extends BinaryOperator<T>,
    ThrowingFunctionalInterface<ThrowingBinaryOperator<T>, BinaryOperator<T>>
{
    T doApply(T t, T u)
        throws Throwable;

    @Override
    default T apply(T t, T u)
    {
        try {
            return doApply(t, u);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable tooBad) {
            throw new ThrownByLambdaException(tooBad);
        }
    }

    @Override
    default ThrowingBinaryOperator<T> orTryWith(
        ThrowingBinaryOperator<T> other)
    {
        return (t, u) -> {
            try {
                return doApply(t, u);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return other.apply(t, u);
            }
        };
    }

    @Override
    default BinaryOperator<T> fallbackTo(BinaryOperator<T> fallback)
    {
        return (t, u) -> {
            try {
                return doApply(t, u);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return fallback.apply(t, u);
            }
        };
    }

    @Override
    default <E extends RuntimeException> BinaryOperator<T> orThrow(
        Class<E> exceptionClass)
    {
        return (t, u) -> {
            try {
                return doApply(t, u);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable tooBad) {
                throw ThrowablesFactory.INSTANCE.get(exceptionClass, tooBad);
            }
        };
    }

    default BinaryOperator<T> orReturn(T defaultValue)
    {
        return (t, u) -> {
            try {
                return doApply(t, u);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return defaultValue;
            }
        };
    }

    default BinaryOperator<T> orReturnLeft()
    {
        return (t, u) -> {
            try {
                return doApply(t, u);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return t;
            }
        };
    }

    default BinaryOperator<T> orReturnRight()
    {
        return (t, u) -> {
            try {
                return doApply(t, u);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return u;
            }
        };
    }
}
