package com.github.fge.lambdas.functions;

import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.BiFunction;

/**
 * A throwing {@link BiFunction}
 *
 * @param <T> parameter type of the first argument of this bifunction
 * @param <U> parameter type of the second argument of this bifunction
 * @param <R> parameter type of the return value of this bifunction
 */
@FunctionalInterface
public interface ThrowingBiFunction<T, U, R>
    extends BiFunction<T, U, R>
{
    R doApply(T t, U u)
        throws Throwable;

    @Override
    default R apply(T t, U u)
    {
        try {
            return doApply(t, u);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable throwable) {
            throw new ThrownByLambdaException(throwable);
        }
    }
}
