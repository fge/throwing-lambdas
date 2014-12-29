package com.github.fge.lambdas.consumers;

import com.github.fge.lambdas.ThrowablesFactory;
import com.github.fge.lambdas.ThrowingFunctionalInterface;
import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.LongConsumer;

@FunctionalInterface
public interface ThrowingLongConsumer
    extends LongConsumer,
    ThrowingFunctionalInterface<ThrowingLongConsumer, LongConsumer>
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

    @Override
    default ThrowingLongConsumer orTryWith(ThrowingLongConsumer other)
    {
        return value -> {
            try {
                doAccept(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                other.accept(value);
            }
        };
    }

    @Override
    default LongConsumer fallbackTo(LongConsumer byDefault)
    {
        return value -> {
            try {
                doAccept(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                byDefault.accept(value);
            }
        };
    }

    @Override
    default <E extends RuntimeException> LongConsumer orThrow(
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

    default LongConsumer orDoNothing()
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
}
