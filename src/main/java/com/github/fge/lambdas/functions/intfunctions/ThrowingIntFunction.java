package com.github.fge.lambdas.functions.intfunctions;

import com.github.fge.lambdas.ThrowablesFactory;
import com.github.fge.lambdas.ThrowingFunctionalInterface;
import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.IntFunction;

@FunctionalInterface
public interface ThrowingIntFunction<R>
    extends IntFunction<R>,
    ThrowingFunctionalInterface<ThrowingIntFunction<R>, IntFunction<R>>
{
    R doApply(int value)
        throws Throwable;

    @Override
    default R apply(int value)
    {
        try {
            return doApply(value);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable tooBad) {
            throw new ThrownByLambdaException(tooBad);
        }
    };

    @Override
    default ThrowingIntFunction<R> orTryWith(
        ThrowingIntFunction<R> other)
    {
        return value -> {
            try {
                return doApply(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return other.apply(value);
            }
        };
    }

    @Override
    default IntFunction<R> fallbackTo(IntFunction<R> byDefault)
    {
        return value -> {
            try {
                return doApply(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return byDefault.apply(value);
            }
        };
    }

    @Override
    default <E extends RuntimeException> IntFunction<R> orThrow(
        Class<E> exceptionClass)
    {
        return value -> {
            try {
                return doApply(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable tooBad) {
                throw ThrowablesFactory.INSTANCE.get(exceptionClass, tooBad);
            }
        };
    }

    default IntFunction<R> orReturn(R defaultValue)
    {
        return value -> {
            try {
                return doApply(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return defaultValue;
            }
        };
    }
}
