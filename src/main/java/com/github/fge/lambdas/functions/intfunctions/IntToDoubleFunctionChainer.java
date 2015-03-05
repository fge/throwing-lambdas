package com.github.fge.lambdas.functions.intfunctions;

import com.github.fge.lambdas.Chainer;

import java.util.function.IntToDoubleFunction;

public class IntToDoubleFunctionChainer
    extends Chainer<IntToDoubleFunction, ThrowingIntToDoubleFunction, IntToDoubleFunctionChainer>
    implements ThrowingIntToDoubleFunction
{
    public IntToDoubleFunctionChainer(
        final ThrowingIntToDoubleFunction throwing)
    {
        super(throwing);
    }

    @Override
    public double doApplyAsDouble(final int value)
        throws Throwable
    {
        return throwing.doApplyAsDouble(value);
    }

    @Override
    public IntToDoubleFunctionChainer orTryWith(
        final ThrowingIntToDoubleFunction other)
    {
        final ThrowingIntToDoubleFunction intToDoubleFunction = value -> {
            try {
                return throwing.doApplyAsDouble(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return other.doApplyAsDouble(value);
            }
        };

        return new IntToDoubleFunctionChainer(intToDoubleFunction);
    }

    @Override
    public <E extends RuntimeException> ThrowingIntToDoubleFunction orThrow(
        final Class<E> exclass)
    {
        return value -> {
            try {
                return throwing.doApplyAsDouble(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable throwable) {
                throw rethrow(exclass, throwable);
            }
        };
    }

    @Override
    public IntToDoubleFunction fallbackTo(final IntToDoubleFunction fallback)
    {
        return value -> {
            try {
                return throwing.doApplyAsDouble(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return fallback.applyAsDouble(value);
            }
        };
    }

    @Override
    public IntToDoubleFunction sneakyThrow()
    {
        return value -> {
            try {
                return throwing.doApplyAsDouble(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable throwable) {
                throw doSneakyThrow(throwable);
            }
        };
    }

    public IntToDoubleFunction orReturn(final double retval)
    {
        return value -> {
            try {
                return throwing.doApplyAsDouble(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return retval;
            }
        };
    }
}
