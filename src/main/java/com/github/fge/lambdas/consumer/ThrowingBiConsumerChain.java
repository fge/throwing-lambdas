package com.github.fge.lambdas.consumer;

import com.github.fge.lambdas.Chain;
import com.github.fge.lambdas.ThrowablesFactory;

import java.util.function.BiConsumer;

public final class ThrowingBiConsumerChain<T, U>
    extends Chain<BiConsumer<T, U>, ThrowingBiConsumer<T, U>, ThrowingBiConsumerChain<T, U>>
    implements ThrowingBiConsumer<T, U>
{
    public ThrowingBiConsumerChain(final ThrowingBiConsumer<T, U> throwing)
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
    public ThrowingBiConsumerChain<T, U> orTryWith(
        final ThrowingBiConsumer<T, U> other)
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

        return new ThrowingBiConsumerChain<>(consumer);
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
                throw ThrowablesFactory.INSTANCE.get(exclass, throwable);
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
