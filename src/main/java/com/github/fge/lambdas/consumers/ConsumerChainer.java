package com.github.fge.lambdas.consumers;

import com.github.fge.lambdas.Chainer;

import java.util.function.Consumer;

public class ConsumerChainer<T>
    extends Chainer<Consumer<T>, ThrowingConsumer<T>, ConsumerChainer<T>>
    implements ThrowingConsumer<T>
{
    public ConsumerChainer(final ThrowingConsumer<T> throwing)
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
    public ConsumerChainer<T> orTryWith(final ThrowingConsumer<T> other)
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

        return new ConsumerChainer<>(consumer);
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
                throw rethrow(exclass, throwable);
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

    @Override
    public Consumer<T> sneakyThrow()
    {
        return t -> {
            try {
                throwing.doAccept(t);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable throwable) {
                throw doSneakyThrow(throwable);
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
