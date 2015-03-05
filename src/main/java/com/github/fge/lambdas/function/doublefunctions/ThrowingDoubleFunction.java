package com.github.fge.lambdas.function.doublefunctions;

import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.DoubleFunction;

/**
 * A throwing {@link DoubleFunction}
 *
 * @param <R> parameter type of the return value of this function
 */
@FunctionalInterface
public interface ThrowingDoubleFunction<R>
    extends DoubleFunction<R>
{
    R doApply(double value)
        throws Throwable;

    @Override
    default R apply(double value)
    {
        try {
            return doApply(value);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable throwable) {
            throw new ThrownByLambdaException(throwable);
        }
    }
}
