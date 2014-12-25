package com.github.fge.lambdas.consumers;

import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.BiConsumer;

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
}
