package com.github.fge.lambdas.functions.intfunctions;

import com.github.fge.lambdas.ThrowablesFactory;
import com.github.fge.lambdas.ThrowingFunctionalInterface;
import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.IntToDoubleFunction;

@FunctionalInterface
public interface ThrowingIntToDoubleFunction
    extends IntToDoubleFunction,
    ThrowingFunctionalInterface<ThrowingIntToDoubleFunction, IntToDoubleFunction>
{
    double doApplyAsDouble(int value)
        throws Throwable;

    @Override
    default double applyAsDouble(int value)
    {
        try {
            return doApplyAsDouble(value);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable tooBad) {
            throw new ThrownByLambdaException(tooBad);
        }
    }

    @Override
    default ThrowingIntToDoubleFunction orTryWith(
        ThrowingIntToDoubleFunction other)
    {
        return value -> {
            try {
                return doApplyAsDouble(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return other.applyAsDouble(value);
            }
        };
    }

    @Override
    default IntToDoubleFunction fallbackTo(IntToDoubleFunction fallback)
    {
        return value -> {
            try {
                return doApplyAsDouble(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return fallback.applyAsDouble(value);
            }
        };
    }

    @Override
    default <E extends RuntimeException> IntToDoubleFunction orThrow(
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

    default IntToDoubleFunction orReturn(double defaultValue)
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
}
