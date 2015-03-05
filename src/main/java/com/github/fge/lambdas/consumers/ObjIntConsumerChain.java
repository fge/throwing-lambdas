package com.github.fge.lambdas.consumers;

import com.github.fge.lambdas.Chain;

import java.util.function.ObjIntConsumer;

public class ObjIntConsumerChain<T>
    extends Chain<ObjIntConsumer<T>, ThrowingObjIntConsumer<T>, ObjIntConsumerChain<T>>
    implements ThrowingObjIntConsumer<T>
{
    public ObjIntConsumerChain(
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
    public ObjIntConsumerChain<T> orTryWith(
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

        return new ObjIntConsumerChain<>(consumer);
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
