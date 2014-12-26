package com.github.fge.lambdas.functions;

import com.github.fge.lambdas.ThrowablesFactory;
import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.Function;

@FunctionalInterface
public interface ThrowingFunction<T, R>
    extends Function<T, R>
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
}

