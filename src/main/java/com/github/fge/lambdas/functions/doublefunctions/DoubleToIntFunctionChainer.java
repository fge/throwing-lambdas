package com.github.fge.lambdas.functions.doublefunctions;

import com.github.fge.lambdas.Chainer;

import java.util.function.DoubleToIntFunction;

public class DoubleToIntFunctionChainer
    extends Chainer<DoubleToIntFunction, ThrowingDoubleToIntFunction, DoubleToIntFunctionChainer>
    implements ThrowingDoubleToIntFunction
{
    public DoubleToIntFunctionChainer(
        final ThrowingDoubleToIntFunction throwing)
    {
        super(throwing);
    }

    @Override
    public int doApplyAsInt(final double value)
        throws Throwable
    {
        return throwing.doApplyAsInt(value);
    }

    @Override
    public DoubleToIntFunctionChainer orTryWith(
        final ThrowingDoubleToIntFunction other)
    {
        final ThrowingDoubleToIntFunction doubleToIntFunction = value -> {
            try {
                return throwing.doApplyAsInt(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return other.doApplyAsInt(value);
            }
        };

        return new DoubleToIntFunctionChainer(doubleToIntFunction);
    }

    @Override
    public <E extends RuntimeException> ThrowingDoubleToIntFunction orThrow(
        final Class<E> exclass)
    {
        return value -> {
            try {
                return throwing.doApplyAsInt(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable throwable) {
                throw rethrow(exclass, throwable);
            }
        };
    }

    @Override
    public DoubleToIntFunction fallbackTo(final DoubleToIntFunction fallback)
    {
        return value -> {
            try {
                return throwing.doApplyAsInt(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return fallback.applyAsInt(value);
            }
        };
    }

    @Override
    public DoubleToIntFunction sneakyThrow()
    {
        return value -> {
            try {
                return throwing.doApplyAsInt(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable throwable) {
                throw doSneakyThrow(throwable);
            }
        };
    }

    public DoubleToIntFunction orReturn(final int retval)
    {
        return value -> {
            try {
                return throwing.doApplyAsInt(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return retval;
            }
        };
    }
}
