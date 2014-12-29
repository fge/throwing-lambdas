package com.github.fge.lambdas.consumers;

import com.github.fge.lambdas.ThrowablesFactory;
import com.github.fge.lambdas.ThrowingFunctionalInterface;
import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.ObjDoubleConsumer;

/**
 * A throwing {@link ObjDoubleConsumer}
 *
 * @param <T> parameter type of the first argument of this consumer
 */
@FunctionalInterface
public interface ThrowingObjDoubleConsumer<T>
    extends ObjDoubleConsumer<T>,
    ThrowingFunctionalInterface<ThrowingObjDoubleConsumer<T>, ObjDoubleConsumer<T>>
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

    @Override
    default ThrowingObjDoubleConsumer<T> orTryWith(
        ThrowingObjDoubleConsumer<T> other)
    {
        return (t, value) -> {
            try {
                doAccept(t, value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                other.accept(t, value);
            }
        };
    }

    @Override
    default ObjDoubleConsumer<T> fallbackTo(ObjDoubleConsumer<T> fallback)
    {
        return (t, value) -> {
            try {
                doAccept(t, value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                fallback.accept(t, value);
            }
        };
    }

    @Override
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
}
