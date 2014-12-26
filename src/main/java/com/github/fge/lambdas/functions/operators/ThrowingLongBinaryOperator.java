package com.github.fge.lambdas.functions.operators;

import com.github.fge.lambdas.ThrowablesFactory;
import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.LongBinaryOperator;

@FunctionalInterface
public interface ThrowingLongBinaryOperator
    extends LongBinaryOperator
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
        return (left, right) -> left;
    }

    default LongBinaryOperator orReturnRight()
    {
        return (left, right) -> right;
    }

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
}
