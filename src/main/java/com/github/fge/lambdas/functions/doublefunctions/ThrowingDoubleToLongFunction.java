package com.github.fge.lambdas.functions.doublefunctions;

import com.github.fge.lambdas.ThrowablesFactory;
import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.DoubleToLongFunction;

@FunctionalInterface
public interface ThrowingDoubleToLongFunction
    extends DoubleToLongFunction
{
    long doApplyAsLong(double value)
        throws Throwable;

    @Override
    default long applyAsLong(double value)
    {
        try {
            return doApplyAsLong(value);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable tooBad) {
            throw new ThrownByLambdaException(tooBad);
        }
    }

    default DoubleToLongFunction orReturn(long defaultValue)
    {
        return value -> {
            try {
                return doApplyAsLong(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return defaultValue;
            }
        };
    }

    default <E extends RuntimeException> DoubleToLongFunction orThrow(
        Class<E> exceptionClass)
    {
        return value -> {
            try {
                return doApplyAsLong(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable tooBad) {
                throw ThrowablesFactory.INSTANCE.get(exceptionClass, tooBad);
            }
        };
    }
}
