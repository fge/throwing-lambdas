package com.github.fge.lambdas.consumers;

import com.github.fge.lambdas.ThrowablesFactory;
import com.github.fge.lambdas.ThrowingFunctionalInterface;
import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.ObjLongConsumer;

/**
 * A throwing {@link ObjLongConsumer}
 *
 * @param <T> parameter type of the first argument of this consumer
 */
@FunctionalInterface
public interface ThrowingObjLongConsumer<T>
    extends ObjLongConsumer<T>,
    ThrowingFunctionalInterface<ThrowingObjLongConsumer<T>, ObjLongConsumer<T>>
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

    @Override
    default ThrowingObjLongConsumer<T> orTryWith(
        ThrowingObjLongConsumer<T> other)
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
    default ObjLongConsumer<T> fallbackTo(ObjLongConsumer<T> fallback)
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
    default <E extends RuntimeException> ObjLongConsumer<T> orThrow(
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

    default ObjLongConsumer<T> orDoNothing()
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
