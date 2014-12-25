package com.github.fge.lambdas.functions;

import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.BiFunction;

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
        } catch (Throwable tooBad) {
            throw new ThrownByLambdaException(tooBad);
        }
    }
}
