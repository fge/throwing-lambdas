package com.github.fge.lambdas.supplier;

import com.github.fge.lambdas.Chainer;
import com.github.fge.lambdas.ThrowablesFactory;

import java.util.function.Supplier;

public class SupplierChainer<T>
    extends Chainer<Supplier<T>, ThrowingSupplier<T>, SupplierChainer<T>>
    implements ThrowingSupplier<T>
{
    public SupplierChainer(
        final ThrowingSupplier<T> throwing)
    {
        super(throwing);
    }

    @Override
    public T doGet()
        throws Throwable
    {
        return throwing.doGet();
    }

    @Override
    public SupplierChainer<T> orTryWith(final ThrowingSupplier<T> other)
    {
        final ThrowingSupplier<T> supplier = () -> {
            try {
                return throwing.doGet();
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return other.doGet();
            }
        };

        return new SupplierChainer<>(supplier);
    }

    @Override
    public <E extends RuntimeException> ThrowingSupplier<T> orThrow(
        final Class<E> exclass)
    {
        return () -> {
            try {
                return throwing.doGet();
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable throwable) {
                throw rethrow(exclass, throwable);
            }
        };
    }

    @Override
    public Supplier<T> fallbackTo(final Supplier<T> fallback)
    {
        return () -> {
            try {
                return throwing.doGet();
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return fallback.get();
            }
        };
    }

    public Supplier<T> orReturn(final T retval)
    {
        return () -> {
            try {
                return throwing.doGet();
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return retval;
            }
        };
    }
}
