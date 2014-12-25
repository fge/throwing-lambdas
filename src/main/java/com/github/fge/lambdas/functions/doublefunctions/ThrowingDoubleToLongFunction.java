package com.github.fge.lambdas.functions.doublefunctions;

import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.DoubleToLongFunction;

public interface ThrowingDoubleToLongFunction
    extends DoubleToLongFunction
{
    long doApplyAsLong(double value)
        throws Throwable;

    @Override
    default long applyAsLong(double value)
    {
        try {
            return doApplyAsLong(value);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable tooBad) {
            throw new ThrownByLambdaException(tooBad);
        }
    }
}
