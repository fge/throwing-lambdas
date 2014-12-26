package com.github.fge.lambdas.consumers;

import com.github.fge.lambdas.ThrowablesFactory;
import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.ObjDoubleConsumer;

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

    default ObjDoubleConsumer<T> orDoNothing()
    {
        return (t, value) -> {
            try {
                doAccept(t, value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                // Does nothing.
            }
        };
    }

    default <E extends RuntimeException> ObjDoubleConsumer<T> orThrow(
        Class<E> exceptionClass)
    {
        return (t, value) -> {
            try {
                doAccept(t, value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable tooBad) {
                throw ThrowablesFactory.INSTANCE.get(exceptionClass, tooBad);
            }
        };
    }
}
