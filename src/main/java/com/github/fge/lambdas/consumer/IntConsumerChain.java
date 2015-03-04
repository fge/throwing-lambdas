package com.github.fge.lambdas.consumer;

import com.github.fge.lambdas.Chain;
import com.github.fge.lambdas.ThrowablesFactory;

import java.util.function.IntConsumer;

public final class IntConsumerChain
    extends Chain<IntConsumer, ThrowingIntConsumer, IntConsumerChain>
    implements ThrowingIntConsumer
{
    public IntConsumerChain(final ThrowingIntConsumer throwing)
    {
        super(throwing);
    }

    @Override
    public void doAccept(final int value)
        throws Throwable
    {
        throwing.doAccept(value);
    }

    @Override
    public IntConsumerChain orTryWith(
        final ThrowingIntConsumer other)
    {
        final ThrowingIntConsumer consumer = value -> {
            try {
                throwing.doAccept(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                other.doAccept(value);
            }
        };

        return new IntConsumerChain(consumer);
    }

    @Override
    public <E extends RuntimeException> ThrowingIntConsumer orThrow(
        final Class<E> exclass)
    {
        return value -> {
            try {
                throwing.doAccept(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable throwable) {
                throw ThrowablesFactory.INSTANCE.get(exclass, throwable);
            }
        };
    }

    @Override
    public IntConsumer fallbackTo(final IntConsumer fallback)
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

    public IntConsumer orDoNothing()
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
