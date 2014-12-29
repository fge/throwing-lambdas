package com.github.fge.lambdas.consumers;

import com.github.fge.lambdas.ThrowablesFactory;
import com.github.fge.lambdas.ThrowingFunctionalInterface;
import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.BiConsumer;

/**
 * A throwing {@link BiConsumer}
 *
 * @param <T> parameter type of the first argument of the biconsumer
 * @param <U> parameter type of the second argument of the biconsumer
 */
@FunctionalInterface
public interface ThrowingBiConsumer<T, U>
    extends BiConsumer<T, U>,
    ThrowingFunctionalInterface<ThrowingBiConsumer<T, U>, BiConsumer<T, U>>
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

    @Override
    default ThrowingBiConsumer<T, U> orTryWith(ThrowingBiConsumer<T, U> other)
    {
        return (t, u) -> {
            try {
                doAccept(t, u);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                other.accept(t, u);
            }
        };
    }

    @Override
    default BiConsumer<T, U> fallbackTo(BiConsumer<T, U> fallback)
    {
        return (t, u) -> {
            try {
                doAccept(t, u);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                fallback.accept(t, u);
            }
        };
    }


    @Override
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
}
