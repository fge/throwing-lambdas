package com.github.fge.lambdas.consumers;

import com.github.fge.lambdas.ThrowablesFactory;
import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.BiConsumer;

@FunctionalInterface
public interface ThrowingBiConsumer<T, U>
    extends BiConsumer<T, U>
{
    void doAccept(T t, U u)
        throws Throwable;

    @Override
    default void accept(T t, U u)
    {
        try {
            doAccept(t, u);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable tooBad) {
            throw new ThrownByLambdaException(tooBad);
        }
    }

    default BiConsumer<T, U> orDoNothing()
    {
        return (t, u) -> {
            try {
                doAccept(t, u);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                // Does nothing.
            }
        };
    }

    default <E extends RuntimeException> BiConsumer<T, U> orThrow(
        Class<E> exceptionClass)
    {
        return (t, u) -> {
            try {
                doAccept(t, u);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable tooBad) {
                throw ThrowablesFactory.INSTANCE.get(exceptionClass, tooBad);
            }
        };
    }
}
