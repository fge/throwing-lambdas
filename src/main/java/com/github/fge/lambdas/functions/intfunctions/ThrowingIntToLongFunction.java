package com.github.fge.lambdas.functions.intfunctions;

import com.github.fge.lambdas.ThrowablesFactory;
import com.github.fge.lambdas.ThrowingFunctionalInterface;
import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.IntToLongFunction;

/**
 * A throwing {@link IntToLongFunction}
 */
@FunctionalInterface
public interface ThrowingIntToLongFunction
    extends IntToLongFunction,
    ThrowingFunctionalInterface<ThrowingIntToLongFunction, IntToLongFunction>
{
    long doApplyAsLong(int value)
        throws Throwable;

    @Override
    default long applyAsLong(int value)
    {
        try {
            return doApplyAsLong(value);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable tooBad) {
            throw new ThrownByLambdaException(tooBad);
        }
    }

    @Override
    default ThrowingIntToLongFunction orTryWith(
        ThrowingIntToLongFunction other)
    {
        return value -> {
            try {
                return doApplyAsLong(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return other.applyAsLong(value);
            }
        };
    }

    @Override
    default IntToLongFunction fallbackTo(IntToLongFunction fallback)
    {
        return value -> {
            try {
                return doApplyAsLong(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return fallback.applyAsLong(value);
            }
        };
    }

    @Override
    default <E extends RuntimeException> IntToLongFunction orThrow(
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

    default IntToLongFunction orReturn(long defaultValue)
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
}
