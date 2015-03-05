package com.github.fge.lambdas.consumers;

import com.github.fge.lambdas.Chainer;

import java.util.function.IntConsumer;

public class IntConsumerChainer
    extends Chainer<IntConsumer, ThrowingIntConsumer, IntConsumerChainer>
    implements ThrowingIntConsumer
{
    public IntConsumerChainer(final ThrowingIntConsumer throwing)
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
    public IntConsumerChainer orTryWith(
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

        return new IntConsumerChainer(consumer);
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
                throw rethrow(exclass, throwable);
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

    @Override
    public IntConsumer sneakyThrow()
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
