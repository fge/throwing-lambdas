package com.github.fge.lambdas.consumers;

import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.IntConsumer;

@FunctionalInterface
public interface ThrowingIntConsumer
    extends IntConsumer
{
    void doAccept(int value)
        throws Throwable;

    @Override
    default void accept(int value)
    {
        try {
            doAccept(value);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable tooBad) {
            throw new ThrownByLambdaException(tooBad);
        }
    }
}
