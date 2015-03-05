package com.github.fge.lambdas.consumers;

import com.github.fge.lambdas.Chainer;

import java.util.function.ObjDoubleConsumer;

public class ObjDoubleConsumerChainer<T>
    extends Chainer<ObjDoubleConsumer<T>, ThrowingObjDoubleConsumer<T>, ObjDoubleConsumerChainer<T>>
    implements ThrowingObjDoubleConsumer<T>
{
    public ObjDoubleConsumerChainer(
        final ThrowingObjDoubleConsumer<T> throwing)
    {
        super(throwing);
    }

    @Override
    public void doAccept(final T t, final double value)
        throws Throwable
    {
        throwing.doAccept(t, value);
    }

    @Override
    public ObjDoubleConsumerChainer<T> orTryWith(
        final ThrowingObjDoubleConsumer<T> other)
    {
        final ThrowingObjDoubleConsumer<T> consumer = (t, value) -> {
            try {
                throwing.doAccept(t, value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                other.doAccept(t, value);
            }
        };

        return new ObjDoubleConsumerChainer<>(consumer);
    }

    @Override
    public <E extends RuntimeException> ThrowingObjDoubleConsumer<T> orThrow(
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
    public ObjDoubleConsumer<T> fallbackTo(final ObjDoubleConsumer<T> fallback)
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

    public ObjDoubleConsumer<T> orDoNothing()
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
