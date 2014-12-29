package com.github.fge.lambdas.functions.operators;

import com.github.fge.lambdas.ThrowablesFactory;
import com.github.fge.lambdas.ThrowingFunctionalInterface;
import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.IntBinaryOperator;

@FunctionalInterface
public interface ThrowingIntBinaryOperator
    extends IntBinaryOperator,
    ThrowingFunctionalInterface<ThrowingIntBinaryOperator, IntBinaryOperator>
{
    int doApplyAsInt(int left, int right)
        throws Throwable;

    @Override
    default int applyAsInt(int left, int right)
    {
        try {
            return doApplyAsInt(left, right);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable tooBad) {
            throw new ThrownByLambdaException(tooBad);
        }
    }

    @Override
    default ThrowingIntBinaryOperator orTryWith(
        ThrowingIntBinaryOperator other)
    {
        return (left, right) -> {
            try {
                return doApplyAsInt(left, right);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return other.applyAsInt(left, right);
            }
        };
    }

    @Override
    default IntBinaryOperator or(IntBinaryOperator byDefault)
    {
        return (left, right) -> {
            try {
                return doApplyAsInt(left, right);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return byDefault.applyAsInt(left, right);
            }
        };
    }

    @Override
    default <E extends RuntimeException> IntBinaryOperator orThrow(
        Class<E> exceptionClass)
    {
        return (left, right) -> {
            try {
                return doApplyAsInt(left, right);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable tooBad) {
                throw ThrowablesFactory.INSTANCE.get(exceptionClass, tooBad);
            }
        };
    }

    default IntBinaryOperator orReturn(int defaultValue)
    {
        return (left, right) -> {
            try {
                return doApplyAsInt(left, right);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return defaultValue;
            }
        };
    }

    default IntBinaryOperator orReturnLeft()
    {
        return (left, right) -> {
            try {
                return doApplyAsInt(left, right);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return left;
            }
        };
    }

    default IntBinaryOperator orReturnRight()
    {
        return (left, right) -> {
            try {
                return doApplyAsInt(left, right);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return right;
            }
        };
    }
}
