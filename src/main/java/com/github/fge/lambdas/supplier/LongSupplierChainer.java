package com.github.fge.lambdas.supplier;

import com.github.fge.lambdas.Chainer;

import java.util.function.LongSupplier;

public class LongSupplierChainer
    extends Chainer<LongSupplier, ThrowingLongSupplier, LongSupplierChainer>
    implements ThrowingLongSupplier
{
    public LongSupplierChainer(final ThrowingLongSupplier throwing)
    {
        super(throwing);
    }

    @Override
    public long doGetAsLong()
        throws Throwable
    {
        return throwing.doGetAsLong();
    }

    @Override
    public LongSupplierChainer orTryWith(final ThrowingLongSupplier other)
    {
        final ThrowingLongSupplier longSupplier = () -> {
            try {
                return throwing.doGetAsLong();
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return other.doGetAsLong();
            }
        };

        return new LongSupplierChainer(longSupplier);
    }

    @Override
    public <E extends RuntimeException> ThrowingLongSupplier orThrow(
        final Class<E> exclass)
    {
        return () -> {
            try {
                return throwing.doGetAsLong();
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable throwable) {
                throw rethrow(exclass, throwable);
            }
        };
    }

    @Override
    public LongSupplier fallbackTo(final LongSupplier fallback)
    {
        return () -> {
            try {
                return throwing.doGetAsLong();
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return fallback.getAsLong();
            }
        };
    }

    public LongSupplier orReturn(final long retval)
    {
        return () -> {
            try {
                return throwing.doGetAsLong();
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return retval;
            }
        };
    }
}
