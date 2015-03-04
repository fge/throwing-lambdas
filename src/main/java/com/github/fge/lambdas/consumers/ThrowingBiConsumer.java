package com.github.fge.lambdas.consumers;

import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.BiConsumer;

/**
 * A throwing {@link BiConsumer}
 *
 * @param <T> type parameter of the first argument
 * @param <U> type parameter of the second argument
 */
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
        } catch (Throwable throwable) {
            throw new ThrownByLambdaException(throwable);
        }
    }
}
