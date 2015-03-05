package com.github.fge.lambdas.functions.operators;

import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.UnaryOperator;

/**
 * A throwing {@link UnaryOperator}
 *
 * @param <T> type parameter of the argument and returning type of this unary
 *           operator
 */
@FunctionalInterface
public interface ThrowingUnaryOperator<T>
    extends UnaryOperator<T>
{
    T doApply(T t)
        throws Throwable;

    @Override
    default T apply(T t)
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
