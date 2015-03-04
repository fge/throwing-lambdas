package com.github.fge.lambdas.consumers;

import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.ObjDoubleConsumer;
import java.util.function.ObjIntConsumer;

/**
 * A throwing {@link ObjDoubleConsumer}
 *
 * @param <T> type parameter of the argument
 */
@FunctionalInterface
public interface ThrowingObjIntConsumer<T>
    extends ObjIntConsumer<T>
{
    void doAccept(T t, int value)
        throws Throwable;

    @Override
    default void accept(T t, int value)
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
