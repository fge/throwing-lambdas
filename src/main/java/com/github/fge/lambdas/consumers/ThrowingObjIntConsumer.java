package com.github.fge.lambdas.consumers;

import com.github.fge.lambdas.ThrowablesFactory;
import com.github.fge.lambdas.ThrowingFunctionalInterface;
import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.ObjIntConsumer;

/**
 * A throwing {@link ObjIntConsumer}
 *
 * @param <T> parameter type of the first argument of this consumer
 */
@FunctionalInterface
public interface ThrowingObjIntConsumer<T>
    extends ObjIntConsumer<T>,
    ThrowingFunctionalInterface<ThrowingObjIntConsumer<T>, ObjIntConsumer<T>>
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

    @Override
    default ThrowingObjIntConsumer<T> orTryWith(ThrowingObjIntConsumer<T> other)
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
    default ObjIntConsumer<T> fallbackTo(ObjIntConsumer<T> fallback)
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
    default <E extends RuntimeException> ObjIntConsumer<T> orThrow(
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

    default ObjIntConsumer<T> orDoNothing()
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
