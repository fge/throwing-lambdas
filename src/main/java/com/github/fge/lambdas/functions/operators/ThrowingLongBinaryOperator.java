package com.github.fge.lambdas.functions.operators;

import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.LongBinaryOperator;

/**
 * A throwing {@link LongBinaryOperator}
 */
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
        } catch (Throwable throwable) {
            throw new ThrownByLambdaException(throwable);
        }
    }
}
