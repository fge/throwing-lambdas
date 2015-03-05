package com.github.fge.lambdas.supplier;

import com.github.fge.lambdas.Chainer;

import java.util.function.DoubleSupplier;

public class DoubleSupplierChainer
    extends Chainer<DoubleSupplier, ThrowingDoubleSupplier, DoubleSupplierChainer>
    implements ThrowingDoubleSupplier
{
    public DoubleSupplierChainer(final ThrowingDoubleSupplier throwing)
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
    public DoubleSupplierChainer orTryWith(final ThrowingDoubleSupplier other)
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

        return new DoubleSupplierChainer(doubleSupplier);
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
