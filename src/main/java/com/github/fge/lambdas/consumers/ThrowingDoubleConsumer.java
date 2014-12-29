package com.github.fge.lambdas.consumers;

import com.github.fge.lambdas.ThrowablesFactory;
import com.github.fge.lambdas.ThrowingFunctionalInterface;
import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.DoubleConsumer;

@FunctionalInterface
public interface ThrowingDoubleConsumer
    extends DoubleConsumer,
    ThrowingFunctionalInterface<ThrowingDoubleConsumer, DoubleConsumer>
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

    @Override
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

    @Override
    default ThrowingDoubleConsumer orTryWith(ThrowingDoubleConsumer other)
    {
        return value -> {
            try {
                doAccept(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                other.doAccept(value);
            }
        };
    }

    @Override
    default DoubleConsumer fallbackTo(DoubleConsumer byDefault)
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
}
