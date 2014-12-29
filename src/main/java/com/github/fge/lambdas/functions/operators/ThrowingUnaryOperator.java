package com.github.fge.lambdas.functions.operators;

import com.github.fge.lambdas.ThrowablesFactory;
import com.github.fge.lambdas.ThrowingFunctionalInterface;
import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.UnaryOperator;

/**
 * A throwing {@link UnaryOperator}
 *
 * @param <T> type parameter of the argument and returning type of this unary
 *           operator
 */
@FunctionalInterface
public interface ThrowingUnaryOperator<T>
    extends UnaryOperator<T>,
    ThrowingFunctionalInterface<ThrowingUnaryOperator<T>, UnaryOperator<T>>
{
    T doApply(T t)
        throws Throwable;

    @Override
    default T apply(T t)
    {
        try {
            return doApply(t);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable tooBad) {
            throw new ThrownByLambdaException(tooBad);
        }
    }

    @Override
    default ThrowingUnaryOperator<T> orTryWith(
        ThrowingUnaryOperator<T> other)
    {
        return t -> {
            try {
                return doApply(t);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return other.apply(t);
            }
        };
    }

    @Override
    default UnaryOperator<T> fallbackTo(UnaryOperator<T> fallback)
    {
        return t -> {
            try {
                return doApply(t);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return fallback.apply(t);
            }
        };
    }

    @Override
    default <E extends RuntimeException> UnaryOperator<T> orThrow(
        Class<E> exceptionClass)
    {
        return t -> {
            try {
                return doApply(t);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable tooBad) {
                throw ThrowablesFactory.INSTANCE.get(exceptionClass, tooBad);
            }
        };
    }

    default UnaryOperator<T> orReturn(T defaultValue)
    {
        return t -> {
            try {
                return doApply(t);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return defaultValue;
            }
        };
    }

    default UnaryOperator<T> orReturnSelf()
    {
        return t -> {
            try {
                return doApply(t);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return t;
            }
        };
    }
}
