package com.github.fge.lambdas.functions.operators;

import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.LongUnaryOperator;

/**
 * A throwing {@link LongUnaryOperator}
 */
@FunctionalInterface
public interface ThrowingLongUnaryOperator
    extends LongUnaryOperator
{
    long doApplyAsLong(long operand)
        throws Throwable;

    @Override
    default long applyAsLong(long operand)
    {
        try {
            return doApplyAsLong(operand);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable tooBad) {
            throw new ThrownByLambdaException(tooBad);
        }
    }
}
