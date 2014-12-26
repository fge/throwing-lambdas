package com.github.fge.lambdas.consumers;

import com.github.fge.lambdas.ThrowablesFactory;
import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.DoubleConsumer;

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
        } catch (Throwable tooBad) {
            throw new ThrownByLambdaException(tooBad);
        }
    }

    default DoubleConsumer orDoNothing()
    {
        return value -> {
            try {
                doAccept(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                // Does nothing.
            }
        };
    }

    default <E extends RuntimeException> DoubleConsumer orThrow(
        Class<E> exceptionClass)
    {
        return value -> {
            try {
                doAccept(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable tooBad) {
                throw ThrowablesFactory.INSTANCE.get(exceptionClass, tooBad);
            }
        };
    }
}
