package com.github.fge.lambdas.supplier;

import com.github.fge.lambdas.Chain;
import com.github.fge.lambdas.ThrowablesFactory;

import java.util.function.LongSupplier;

public class LongSupplierChain
    extends Chain<LongSupplier, ThrowingLongSupplier, LongSupplierChain>
    implements ThrowingLongSupplier
{
    public LongSupplierChain(final ThrowingLongSupplier throwing)
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
    public LongSupplierChain orTryWith(final ThrowingLongSupplier other)
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

        return new LongSupplierChain(longSupplier);
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
                throw ThrowablesFactory.INSTANCE.get(exclass, throwable);
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
