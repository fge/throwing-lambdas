package com.github.fge.lambdas.supplier;

import com.github.fge.lambdas.Chain;
import com.github.fge.lambdas.ThrowablesFactory;

import java.util.function.IntSupplier;

public class IntSupplierChain
    extends Chain<IntSupplier, ThrowingIntSupplier, IntSupplierChain>
    implements ThrowingIntSupplier
{
    public IntSupplierChain(final ThrowingIntSupplier throwing)
    {
        super(throwing);
    }

    @Override
    public int doGetAsInt()
        throws Throwable
    {
        return throwing.doGetAsInt();
    }

    @Override
    public IntSupplierChain orTryWith(final ThrowingIntSupplier other)
    {
        final ThrowingIntSupplier intSupplier = () -> {
            try {
                return throwing.doGetAsInt();
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return other.doGetAsInt();
            }
        };

        return new IntSupplierChain(intSupplier);
    }

    @Override
    public <E extends RuntimeException> ThrowingIntSupplier orThrow(
        final Class<E> exclass)
    {
        return () -> {
            try {
                return throwing.doGetAsInt();
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable throwable) {
                throw rethrow(exclass, throwable);
            }
        };
    }

    @Override
    public IntSupplier fallbackTo(final IntSupplier fallback)
    {
        return () -> {
            try {
                return throwing.doGetAsInt();
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return fallback.getAsInt();
            }
        };
    }

    public IntSupplier orReturn(final int retval)
    {
        return () -> {
            try {
                return throwing.doGetAsInt();
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return retval;
            }
        };
    }
}
