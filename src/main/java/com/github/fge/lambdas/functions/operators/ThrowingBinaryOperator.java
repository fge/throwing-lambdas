package com.github.fge.lambdas.functions.operators;

import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.BinaryOperator;

public interface ThrowingBinaryOperator<T>
    extends BinaryOperator<T>
{
    T doApply(T t, T u)
        throws Throwable;

    @Override
    default T apply(T t, T u)
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
