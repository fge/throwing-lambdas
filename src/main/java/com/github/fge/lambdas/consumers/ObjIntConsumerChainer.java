package com.github.fge.lambdas.consumers;

import com.github.fge.lambdas.Chainer;

import java.util.function.ObjIntConsumer;

public class ObjIntConsumerChainer<T>
    extends Chainer<ObjIntConsumer<T>, ThrowingObjIntConsumer<T>, ObjIntConsumerChainer<T>>
    implements ThrowingObjIntConsumer<T>
{
    public ObjIntConsumerChainer(
        final ThrowingObjIntConsumer<T> throwing)
    {
        super(throwing);
    }

    @Override
    public void doAccept(final T t, final int value)
        throws Throwable
    {
        throwing.doAccept(t, value);
    }

    @Override
    public ObjIntConsumerChainer<T> orTryWith(
        final ThrowingObjIntConsumer<T> other)
    {
        final ThrowingObjIntConsumer<T> consumer = (t, value) -> {
            try {
                throwing.doAccept(t, value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                other.doAccept(t, value);
            }
        };

        return new ObjIntConsumerChainer<>(consumer);
    }

    @Override
    public <E extends RuntimeException> ThrowingObjIntConsumer<T> orThrow(
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
    public ObjIntConsumer<T> fallbackTo(final ObjIntConsumer<T> fallback)
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

    public ObjIntConsumer<T> orDoNothing()
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
