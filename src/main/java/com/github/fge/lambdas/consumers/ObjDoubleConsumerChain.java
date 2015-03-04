package com.github.fge.lambdas.consumers;

import com.github.fge.lambdas.Chain;
import com.github.fge.lambdas.ThrowablesFactory;

import java.util.function.ObjDoubleConsumer;

public final class ObjDoubleConsumerChain<T>
    extends Chain<ObjDoubleConsumer<T>, ThrowingObjDoubleConsumer<T>, ObjDoubleConsumerChain<T>>
    implements ThrowingObjDoubleConsumer<T>
{
    public ObjDoubleConsumerChain(
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
    public ObjDoubleConsumerChain<T> orTryWith(
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

        return new ObjDoubleConsumerChain<>(consumer);
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
                throw ThrowablesFactory.INSTANCE.get(exclass, throwable);
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
