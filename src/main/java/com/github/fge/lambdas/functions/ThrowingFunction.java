package com.github.fge.lambdas.functions;

import com.github.fge.lambdas.ThrowablesFactory;
import com.github.fge.lambdas.ThrowingFunctionalInterface;
import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.Function;

@FunctionalInterface
public interface ThrowingFunction<T, R>
    extends Function<T, R>,
    ThrowingFunctionalInterface<ThrowingFunction<T, R>, Function<T, R>>
{
    R doApply(T t)
        throws Throwable;

    @Override
    default R apply(T t)
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
    default ThrowingFunction<T, R> orTryWith(ThrowingFunction<T, R> other)
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
    default Function<T, R> fallbackTo(Function<T, R> fallback)
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
    default <E extends RuntimeException> Function<T, R> orThrow(
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

    default Function<T, R> orReturn(R defaultValue)
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
}

