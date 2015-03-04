package com.github.fge.lambdas.consumers;

import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.LongConsumer;

/**
 * A throwing {@link LongConsumer}
 */
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
        } catch (Throwable throwable) {
            throw new ThrownByLambdaException(throwable);
        }
    }
}
