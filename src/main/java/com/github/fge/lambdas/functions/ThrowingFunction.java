package com.github.fge.lambdas.functions;

import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.Function;

@FunctionalInterface
public interface ThrowingFunction<T, R>
    extends Function<T, R>
{
    R doApply(T t)
        throws Throwable;

    default R apply(T t)
    {
        try {
            return doApply(t);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable throwable) {
            throw new ThrownByLambdaException(throwable);
        }
    }
}
