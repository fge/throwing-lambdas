package com.github.fge.lambdas.functions.longfunctions;

import com.github.fge.lambdas.ThrowablesFactory;
import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.LongToIntFunction;

@FunctionalInterface
public interface ThrowingLongToIntFunction
    extends LongToIntFunction
{
    int doApplyAsInt(long value)
        throws Throwable;

    @Override
    default int applyAsInt(long value)
    {
        try {
            return doApplyAsInt(value);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable tooBad) {
            throw new ThrownByLambdaException(tooBad);
        }
    }

    default LongToIntFunction orReturn(int defaultValue)
    {
        return value -> {
            try {
                return doApplyAsInt(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return defaultValue;
            }
        };
    }

    default <E extends RuntimeException> LongToIntFunction orThrow(
        Class<E> exceptionClass)
    {
        return value -> {
            try {
                return doApplyAsInt(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable tooBad) {
                throw ThrowablesFactory.INSTANCE.get(exceptionClass, tooBad);
            }
        };
    }
}
