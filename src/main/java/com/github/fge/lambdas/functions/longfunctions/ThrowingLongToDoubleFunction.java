package com.github.fge.lambdas.functions.longfunctions;

import com.github.fge.lambdas.ThrowablesFactory;
import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.LongToDoubleFunction;

@FunctionalInterface
public interface ThrowingLongToDoubleFunction
    extends LongToDoubleFunction
{
    double doApplyAsDouble(long value)
        throws Throwable;

    @Override
    default double applyAsDouble(long value)
    {
        try {
            return doApplyAsDouble(value);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable tooBad) {
            throw new ThrownByLambdaException(tooBad);
        }
    }

    default LongToDoubleFunction orReturn(double defaultValue)
    {
        return value -> {
            try {
                return doApplyAsDouble(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return defaultValue;
            }
        };
    }

    default <E extends RuntimeException> LongToDoubleFunction orThrow(
        Class<E> exceptionClass)
    {
        return value -> {
            try {
                return doApplyAsDouble(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable tooBad) {
                throw ThrowablesFactory.INSTANCE.get(exceptionClass, tooBad);
            }
        };
    }
}
