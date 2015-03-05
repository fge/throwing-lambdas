package com.github.fge.lambdas.consumers;

import com.github.fge.lambdas.Chainer;

import java.util.function.ObjLongConsumer;

public class ObjLongConsumerChainer<T>
    extends Chainer<ObjLongConsumer<T>, ThrowingObjLongConsumer<T>, ObjLongConsumerChainer<T>>
    implements ThrowingObjLongConsumer<T>
{
    public ObjLongConsumerChainer(
        final ThrowingObjLongConsumer<T> throwing)
    {
        super(throwing);
    }

    @Override
    public void doAccept(final T t, final long value)
        throws Throwable
    {
        throwing.doAccept(t, value);
    }

    @Override
    public ObjLongConsumerChainer<T> orTryWith(
        final ThrowingObjLongConsumer<T> other)
    {
        final ThrowingObjLongConsumer<T> consumer = (t, value) -> {
            try {
                throwing.doAccept(t, value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                other.doAccept(t, value);
            }
        };

        return new ObjLongConsumerChainer<>(consumer);
    }

    @Override
    public <E extends RuntimeException> ThrowingObjLongConsumer<T> orThrow(
        final Class<E> exclass)
    {
        return (t, value) -> {
            try {
                throwing.doAccept(t, value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable throwable) {
                throw rethrow(exclass, throwable);
            }
        };
    }

    @Override
    public ObjLongConsumer<T> fallbackTo(final ObjLongConsumer<T> fallback)
    {
        return (t, value) -> {
            try {
                throwing.doAccept(t, value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                fallback.accept(t, value);
            }
        };
    }

    @Override
    public ObjLongConsumer<T> sneakyThrow()
    {
        return (t, value) -> {
            try {
                throwing.doAccept(t, value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable throwable) {
                throw doSneakyThrow(throwable);
            }
        };
    }

    public ObjLongConsumer<T> orDoNothing()
    {
        return (t, value) -> {
            try {
                throwing.doAccept(t, value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                // nothing
            }
        };
    }
}
