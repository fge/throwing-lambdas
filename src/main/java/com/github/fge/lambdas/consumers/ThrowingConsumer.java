package com.github.fge.lambdas.consumers;

import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.Consumer;

public interface ThrowingConsumer<T>
    extends Consumer<T>
{
    void doAccept(T t)
        throws Throwable;

    @Override
    default void accept(T t)
    {
        try {
            doAccept(t);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable tooBad) {
            throw new ThrownByLambdaException(tooBad);
        }
    }
}
