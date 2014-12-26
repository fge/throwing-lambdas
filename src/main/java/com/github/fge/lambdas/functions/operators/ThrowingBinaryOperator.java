package com.github.fge.lambdas.functions.operators;

import com.github.fge.lambdas.ThrowablesFactory;
import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.BinaryOperator;

@FunctionalInterface
public interface ThrowingBinaryOperator<T>
    extends BinaryOperator<T>
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
        return (t, u) -> t;
    }

    default BinaryOperator<T> orReturnRight()
    {
        return (t, u) -> u;
    }

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
}
