package com.github.fge.lambdas.consumers;

import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.LongConsumer;

@FunctionalInterface
public interface ThrowingLongConsumer
    extends LongConsumer
{
    void doAccept(long value)
        throws Throwable;

    @Override
    default void accept(long value)
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
