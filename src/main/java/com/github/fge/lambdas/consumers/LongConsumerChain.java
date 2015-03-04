package com.github.fge.lambdas.consumers;

import com.github.fge.lambdas.Chain;
import com.github.fge.lambdas.ThrowablesFactory;

import java.util.function.LongConsumer;

public final class LongConsumerChain
    extends Chain<LongConsumer, ThrowingLongConsumer, LongConsumerChain>
    implements ThrowingLongConsumer
{
    public LongConsumerChain(final ThrowingLongConsumer throwing)
    {
        super(throwing);
    }

    @Override
    public void doAccept(final long value)
        throws Throwable
    {
        throwing.doAccept(value);
    }

    @Override
    public LongConsumerChain orTryWith(
        final ThrowingLongConsumer other)
    {
        final ThrowingLongConsumer consumer = value -> {
            try {
                throwing.doAccept(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                other.doAccept(value);
            }
        };

        return new LongConsumerChain(consumer);
    }

    @Override
    public <E extends RuntimeException> ThrowingLongConsumer orThrow(
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
    public LongConsumer fallbackTo(final LongConsumer fallback)
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

    public LongConsumer orDoNothing()
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
