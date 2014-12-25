package com.github.fge.lambdas.consumers;

import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.ObjLongConsumer;

@FunctionalInterface
public interface ThrowingObjLongConsumer<T>
    extends ObjLongConsumer<T>
{
    void doAccept(T t, long value)
        throws Throwable;

    @Override
    default void accept(T t, long value)
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
