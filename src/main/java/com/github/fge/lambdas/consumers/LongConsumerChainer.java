package com.github.fge.lambdas.consumers;

import com.github.fge.lambdas.Chainer;

import java.util.function.LongConsumer;

public class LongConsumerChainer
    extends Chainer<LongConsumer, ThrowingLongConsumer, LongConsumerChainer>
    implements ThrowingLongConsumer
{
    public LongConsumerChainer(final ThrowingLongConsumer throwing)
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
    public LongConsumerChainer orTryWith(
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

        return new LongConsumerChainer(consumer);
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
                throw rethrow(exclass, throwable);
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

    @Override
    public LongConsumer sneakyThrow()
    {
        return value -> {
            try {
                throwing.doAccept(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable throwable) {
                throw doSneakyThrow(throwable);
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
