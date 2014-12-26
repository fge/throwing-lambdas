package com.github.fge.lambdas.functions.intfunctions;

import com.github.fge.lambdas.ThrowablesFactory;
import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.IntToDoubleFunction;

@FunctionalInterface
public interface ThrowingIntToDoubleFunction
    extends IntToDoubleFunction
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
    };

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
}
