package com.github.fge.lambdas.functions.operators;

import com.github.fge.lambdas.ThrowablesFactory;
import com.github.fge.lambdas.ThrowingFunctionalInterface;
import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.LongBinaryOperator;

@FunctionalInterface
public interface ThrowingLongBinaryOperator
    extends LongBinaryOperator,
    ThrowingFunctionalInterface<ThrowingLongBinaryOperator, LongBinaryOperator>
{
    long doApplyAsLong(long left, long right)
        throws Throwable;

    @Override
    default long applyAsLong(long left, long right)
    {
        try {
            return doApplyAsLong(left, right);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable tooBad) {
            throw new ThrownByLambdaException(tooBad);
        }
    }

    @Override
    default ThrowingLongBinaryOperator orTryWith(
        ThrowingLongBinaryOperator other)
    {
        return (left, right) -> {
            try {
                return doApplyAsLong(left, right);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return other.applyAsLong(left, right);
            }
        };
    }

    @Override
    default LongBinaryOperator fallbackTo(LongBinaryOperator byDefault)
    {
        return (left, right) -> {
            try {
                return doApplyAsLong(left, right);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return byDefault.applyAsLong(left, right);
            }
        };
    }

    @Override
    default <E extends RuntimeException> LongBinaryOperator orThrow(
        Class<E> exceptionClass)
    {
        return (left, right) -> {
            try {
                return doApplyAsLong(left, right);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable tooBad) {
                throw ThrowablesFactory.INSTANCE.get(exceptionClass, tooBad);
            }
        };
    }

    default LongBinaryOperator orReturn(long defaultValue)
    {
        return (left, right) -> {
            try {
                return doApplyAsLong(left, right);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return defaultValue;
            }
        };
    }

    default LongBinaryOperator orReturnLeft()
    {
        return (left, right) -> {
            try {
                return doApplyAsLong(left, right);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return left;
            }
        };
    }

    default LongBinaryOperator orReturnRight()
    {
        return (left, right) -> {
            try {
                return doApplyAsLong(left, right);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return right;
            }
        };
    }
}
