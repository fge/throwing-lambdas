package com.github.fge.lambdas.functions.doublefunctions;

import com.github.fge.lambdas.ThrowablesFactory;
import com.github.fge.lambdas.ThrowingFunctionalInterface;
import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.DoubleToIntFunction;

@FunctionalInterface
public interface ThrowingDoubleToIntFunction
    extends DoubleToIntFunction,
    ThrowingFunctionalInterface<ThrowingDoubleToIntFunction, DoubleToIntFunction>
{
    int doApplyAsInt(double value)
        throws Throwable;

    @Override
    default int applyAsInt(double value)
    {
        try {
            return doApplyAsInt(value);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable tooBad) {
            throw new ThrownByLambdaException(tooBad);
        }
    }

    @Override
    default ThrowingDoubleToIntFunction orTryWith(
        ThrowingDoubleToIntFunction other)
    {
        return value -> {
            try {
                return doApplyAsInt(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return other.applyAsInt(value);
            }
        };
    }

    @Override
    default DoubleToIntFunction fallbackTo(DoubleToIntFunction fallback)
    {
        return value -> {
            try {
                return doApplyAsInt(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return fallback.applyAsInt(value);
            }
        };
    }

    @Override
    default <E extends RuntimeException> DoubleToIntFunction orThrow(
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

    default DoubleToIntFunction orReturn(int defaultValue)
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
}
