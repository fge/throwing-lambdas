package com.github.fge.lambdas.functions.doublefunctions;

import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.DoubleToIntFunction;

/**
 * A throwing {@link DoubleToIntFunction}
 */
@FunctionalInterface
public interface ThrowingDoubleToIntFunction
    extends DoubleToIntFunction
{
    int doApplyAsInt(double value)
        throws Throwable;

    @Override
    default int applyAsInt(double value)
    {
        try {
            return doApplyAsInt(value);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable throwable) {
            throw new ThrownByLambdaException(throwable);
        }
    }
}
