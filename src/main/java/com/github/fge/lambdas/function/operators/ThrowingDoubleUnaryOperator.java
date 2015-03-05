package com.github.fge.lambdas.function.operators;

import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.DoubleUnaryOperator;

/**
 * A throwing {@link DoubleUnaryOperator}
 */
@FunctionalInterface
public interface ThrowingDoubleUnaryOperator
    extends DoubleUnaryOperator
{
    double doApplyAsDouble(double operand)
        throws Throwable;

    @Override
    default double applyAsDouble(double operand)
    {
        try {
            return doApplyAsDouble(operand);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable throwable) {
            throw new ThrownByLambdaException(throwable);
        }
    }
}
