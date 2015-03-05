package com.github.fge.lambdas.function.intfunctions;

import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.IntToDoubleFunction;

/**
 * A throwing {@link IntToDoubleFunction}
 */
@FunctionalInterface
public interface ThrowingIntToDoubleFunction
    extends IntToDoubleFunction
{
    double doApplyAsDouble(int value)
        throws Throwable;

    @Override
    default double applyAsDouble(int value)
    {
        try {
            return doApplyAsDouble(value);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable throwable) {
            throw new ThrownByLambdaException(throwable);
        }
    }
}
