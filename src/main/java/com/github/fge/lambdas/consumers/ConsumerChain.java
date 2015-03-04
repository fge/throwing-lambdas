package com.github.fge.lambdas.consumers;

import com.github.fge.lambdas.Chain;
import com.github.fge.lambdas.ThrowablesFactory;

import java.util.function.Consumer;

public final class ConsumerChain<T>
    extends Chain<Consumer<T>, ThrowingConsumer<T>, ConsumerChain<T>>
    implements ThrowingConsumer<T>
{
    public ConsumerChain(final ThrowingConsumer<T> throwing)
    {
        super(throwing);
    }

    @Override
    public void doAccept(final T t)
        throws Throwable
    {
        throwing.doAccept(t);
    }

    @Override
    public ConsumerChain<T> orTryWith(final ThrowingConsumer<T> other)
    {
        final ThrowingConsumer<T> consumer = t -> {
            try {
                throwing.doAccept(t);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                other.doAccept(t);
            }
        };

        return new ConsumerChain<>(consumer);
    }

    @Override
    public <E extends RuntimeException> ThrowingConsumer<T> orThrow(
        final Class<E> exclass)
    {
        return t -> {
            try {
                throwing.doAccept(t);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable throwable) {
                throw ThrowablesFactory.INSTANCE.get(exclass, throwable);
            }
        };
    }

    @Override
    public Consumer<T> fallbackTo(final Consumer<T> fallback)
    {
        return t -> {
            try {
                throwing.doAccept(t);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                fallback.accept(t);
            }
        };
    }

    public Consumer<T> orDoNothing()
    {
        return t -> {
            try {
                throwing.doAccept(t);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                // nothing
            }
        };
    }
}
