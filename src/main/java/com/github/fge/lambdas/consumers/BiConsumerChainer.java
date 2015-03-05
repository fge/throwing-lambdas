package com.github.fge.lambdas.consumers;

import com.github.fge.lambdas.Chainer;

import java.util.function.BiConsumer;

public class BiConsumerChainer<T, U>
    extends Chainer<BiConsumer<T, U>, ThrowingBiConsumer<T, U>, BiConsumerChainer<T, U>>
    implements ThrowingBiConsumer<T, U>
{
    public BiConsumerChainer(final ThrowingBiConsumer<T, U> throwing)
    {
        super(throwing);
    }

    @Override
    public void doAccept(final T t, final U u)
        throws Throwable
    {
        throwing.doAccept(t, u);
    }

    @Override
    public BiConsumerChainer<T, U> orTryWith(final ThrowingBiConsumer<T, U> other)
    {
        final ThrowingBiConsumer<T, U> consumer = (t, u) -> {
            try {
                throwing.doAccept(t, u);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                other.doAccept(t, u);
            }
        };

        return new BiConsumerChainer<>(consumer);
    }

    @Override
    public <E extends RuntimeException> ThrowingBiConsumer<T, U> orThrow(
        final Class<E> exclass)
    {
        return (t, u) -> {
            try {
                throwing.doAccept(t, u);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable throwable) {
                throw rethrow(exclass, throwable);
            }
        };
    }

    @Override
    public BiConsumer<T, U> fallbackTo(final BiConsumer<T, U> fallback)
    {
        return (t, u) -> {
            try {
                throwing.doAccept(t, u);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                fallback.accept(t, u);
            }
        };
    }

    public BiConsumer<T, U> orDoNothing()
    {
        return (t, u) -> {
            try {
                throwing.doAccept(t, u);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                // nothing
            }
        };
    }
}
