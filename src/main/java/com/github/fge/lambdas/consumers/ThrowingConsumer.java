package com.github.fge.lambdas.consumers;

import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.Consumer;

/**
 * A throwing {@link Consumer}
 *
 * @param <T> type parameter of the argument
 */
@FunctionalInterface
public interface ThrowingConsumer<T>
    extends Consumer<T>
{
    void doAccept(T t)
        throws Throwable;

    default void accept(T t)
    {
        try {
            doAccept(t);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable throwable) {
            throw new ThrownByLambdaException(throwable);
        }
    }
}
