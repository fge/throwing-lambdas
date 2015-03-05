package com.github.fge.lambdas.function.operators;

import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.DoubleBinaryOperator;

/**
 * A throwing {@link DoubleBinaryOperator}
 */
@FunctionalInterface
public interface ThrowingDoubleBinaryOperator
    extends DoubleBinaryOperator
{
    double doApplyAsDouble(double left, double right)
        throws Throwable;

    @Override
    default double applyAsDouble(double left, double right)
    {
        try {
            return doApplyAsDouble(left, right);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable throwable) {
            throw new ThrownByLambdaException(throwable);
        }
    }
}
