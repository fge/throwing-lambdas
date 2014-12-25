package com.github.fge.lambdas.functions;

import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.Function;

@FunctionalInterface
public interface ThrowingFunction<T, R>
    extends Function<T, R>
{
    R doApply(T t)
        throws Throwable;

    @Override
    default R apply(T t)
    {
        try {
            return doApply(t);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable tooBad) {
            throw new ThrownByLambdaException(tooBad);
        }
    }
}

