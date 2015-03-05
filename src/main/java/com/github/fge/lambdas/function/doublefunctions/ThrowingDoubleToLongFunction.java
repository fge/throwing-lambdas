package com.github.fge.lambdas.function.doublefunctions;

import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.DoubleToIntFunction;
import java.util.function.DoubleToLongFunction;

/**
 * A throwing {@link DoubleToIntFunction}
 */
@FunctionalInterface
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
        } catch (Throwable throwable) {
            throw new ThrownByLambdaException(throwable);
        }
    }
}
