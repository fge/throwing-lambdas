package com.github.fge.lambdas.functions.intfunctions;

import com.github.fge.lambdas.Chainer;

import java.util.function.IntToLongFunction;

public class IntToLongFunctionChainer
    extends Chainer<IntToLongFunction, ThrowingIntToLongFunction, IntToLongFunctionChainer>
    implements ThrowingIntToLongFunction
{
    public IntToLongFunctionChainer(
        final ThrowingIntToLongFunction throwing)
    {
        super(throwing);
    }

    @Override
    public long doApplyAsLong(final int value)
        throws Throwable
    {
        return throwing.doApplyAsLong(value);
    }

    @Override
    public IntToLongFunctionChainer orTryWith(
        final ThrowingIntToLongFunction other)
    {
        final ThrowingIntToLongFunction intToLongFunction = value -> {
            try {
                return throwing.doApplyAsLong(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return other.doApplyAsLong(value);
            }
        };

        return new IntToLongFunctionChainer(intToLongFunction);
    }

    @Override
    public <E extends RuntimeException> ThrowingIntToLongFunction orThrow(
        final Class<E> exclass)
    {
        return value -> {
            try {
                return throwing.doApplyAsLong(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable throwable) {
                throw rethrow(exclass, throwable);
            }
        };
    }

    @Override
    public IntToLongFunction fallbackTo(final IntToLongFunction fallback)
    {
        return value -> {
            try {
                return throwing.doApplyAsLong(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return fallback.applyAsLong(value);
            }
        };
    }

    public IntToLongFunction orReturn(final long retval)
    {
        return value -> {
            try {
                return throwing.doApplyAsLong(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return retval;
            }
        };
    }
}
