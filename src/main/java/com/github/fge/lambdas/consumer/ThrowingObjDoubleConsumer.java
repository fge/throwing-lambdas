package com.github.fge.lambdas.consumer;

import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.ObjDoubleConsumer;

/**
 * A throwing {@link ObjDoubleConsumer}
 *
 * @param <T> type parameter of the argument
 */
@FunctionalInterface
public interface ThrowingObjDoubleConsumer<T>
    extends ObjDoubleConsumer<T>
{
    void doAccept(T t, double value)
        throws Throwable;

    @Override
    default void accept(T t, double value)
    {
        try {
            doAccept(t, value);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable tooBad) {
            throw new ThrownByLambdaException(tooBad);
        }
    }
}
