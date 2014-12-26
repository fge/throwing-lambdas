package com.github.fge.lambdas.functions.doublefunctions;

import com.github.fge.lambdas.ThrowablesFactory;
import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.DoubleFunction;

@FunctionalInterface
public interface ThrowingDoubleFunction<R>
    extends DoubleFunction<R>
{
    R doApply(double value)
        throws Throwable;

    @Override
    default R apply(double value)
    {
        try {
            return doApply(value);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable tooBad) {
            throw new ThrownByLambdaException(tooBad);
        }
    }

    default DoubleFunction<R> orReturn(R defaultValue)
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

    default <E extends RuntimeException> DoubleFunction<R> orThrow(
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
}
