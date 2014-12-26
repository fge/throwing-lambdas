package com.github.fge.lambdas.consumers;

import com.github.fge.lambdas.ThrowablesFactory;
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

    default IntConsumer orDoNothing()
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

    default <E extends RuntimeException> IntConsumer orThrow(
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
