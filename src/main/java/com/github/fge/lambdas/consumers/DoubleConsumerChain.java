package com.github.fge.lambdas.consumers;

import com.github.fge.lambdas.Chain;

import java.util.function.DoubleConsumer;

public class DoubleConsumerChain
    extends Chain<DoubleConsumer, ThrowingDoubleConsumer, DoubleConsumerChain>
    implements ThrowingDoubleConsumer
{
    public DoubleConsumerChain(final ThrowingDoubleConsumer throwing)
    {
        super(throwing);
    }

    @Override
    public void doAccept(final double value)
        throws Throwable
    {
        throwing.doAccept(value);
    }

    @Override
    public DoubleConsumerChain orTryWith(
        final ThrowingDoubleConsumer other)
    {
        final ThrowingDoubleConsumer consumer = value -> {
            try {
                throwing.doAccept(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                other.doAccept(value);
            }
        };

        return new DoubleConsumerChain(consumer);
    }

    @Override
    public <E extends RuntimeException> ThrowingDoubleConsumer orThrow(
        final Class<E> exclass)
    {
        return value -> {
            try {
                throwing.doAccept(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable throwable) {
                throw rethrow(exclass, throwable);
            }
        };
    }

    @Override
    public DoubleConsumer fallbackTo(final DoubleConsumer fallback)
    {
        return value -> {
            try {
                throwing.doAccept(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                fallback.accept(value);
            }
        };
    }

    public DoubleConsumer orDoNothing()
    {
        return value -> {
            try {
                throwing.doAccept(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                // nothing
            }
        };
    }
}
