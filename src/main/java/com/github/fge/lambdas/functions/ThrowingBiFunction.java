package com.github.fge.lambdas.functions;

import com.github.fge.lambdas.ThrowablesFactory;
import com.github.fge.lambdas.ThrowingFunctionalInterface;
import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.BiFunction;

@FunctionalInterface
public interface ThrowingBiFunction<T, U, R>
    extends BiFunction<T, U, R>,
    ThrowingFunctionalInterface<ThrowingBiFunction<T, U, R>, BiFunction<T, U, R>>
{
    R doApply(T t, U u)
        throws Throwable;

    @Override
    default R apply(T t, U u)
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
    default ThrowingBiFunction<T, U, R> orTryWith(
        ThrowingBiFunction<T, U, R> other)
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
    default BiFunction<T, U, R> fallbackTo(BiFunction<T, U, R> byDefault)
    {
        return (t, u) -> {
            try {
                return doApply(t, u);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return byDefault.apply(t, u);
            }
        };
    }

    @Override
    default <E extends RuntimeException> BiFunction<T, U, R> orThrow(
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

    default BiFunction<T, U, R> orReturn(R defaultValue)
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
}
