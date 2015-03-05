package com.github.fge.lambdas.function.operators;

import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.BinaryOperator;

/**
 * A throwing {@link BinaryOperator}
 *
 * @param <T> parameter type of the two arguments, and return type, of this
 *            binary operator
 */
@FunctionalInterface
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
        } catch (Throwable throwable) {
            throw new ThrownByLambdaException(throwable);
        }
    }
}
