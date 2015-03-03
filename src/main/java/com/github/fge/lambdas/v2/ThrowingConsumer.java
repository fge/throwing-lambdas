package com.github.fge.lambdas.v2;

import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.Consumer;

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
