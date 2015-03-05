package com.github.fge.lambdas.supplier;

import com.github.fge.lambdas.Chain;
import com.github.fge.lambdas.ThrowablesFactory;

import java.util.function.DoubleSupplier;

public class DoubleSupplierChain
    extends Chain<DoubleSupplier, ThrowingDoubleSupplier, DoubleSupplierChain>
    implements ThrowingDoubleSupplier
{
    public DoubleSupplierChain(final ThrowingDoubleSupplier throwing)
    {
        super(throwing);
    }

    @Override
    public double doGetAsDouble()
        throws Throwable
    {
        return throwing.doGetAsDouble();
    }

    @Override
    public DoubleSupplierChain orTryWith(final ThrowingDoubleSupplier other)
    {
        final ThrowingDoubleSupplier doubleSupplier = () -> {
            try {
                return throwing.doGetAsDouble();
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return other.doGetAsDouble();
            }
        };

        return new DoubleSupplierChain(doubleSupplier);
    }

    @Override
    public <E extends RuntimeException> ThrowingDoubleSupplier orThrow(
        final Class<E> exclass)
    {
        return () -> {
            try {
                return throwing.doGetAsDouble();
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable throwable) {
                throw rethrow(exclass, throwable);
            }
        };
    }

    @Override
    public DoubleSupplier fallbackTo(final DoubleSupplier fallback)
    {
        return () -> {
            try {
                return throwing.doGetAsDouble();
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return fallback.getAsDouble();
            }
        };
    }

    public DoubleSupplier orReturn(final double retval)
    {
        return () -> {
            try {
                return throwing.doGetAsDouble();
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return retval;
            }
        };
    }
}
