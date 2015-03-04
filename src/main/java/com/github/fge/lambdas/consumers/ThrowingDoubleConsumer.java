package com.github.fge.lambdas.consumers;

import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.DoubleConsumer;

/**
 * A throwing {@link DoubleConsumer}
 */
@FunctionalInterface
public interface ThrowingDoubleConsumer
    extends DoubleConsumer
{
    void doAccept(double value)
        throws Throwable;

    @Override
    default void accept(double value)
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
