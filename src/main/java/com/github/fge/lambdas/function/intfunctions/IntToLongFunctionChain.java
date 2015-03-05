package com.github.fge.lambdas.function.intfunctions;

import com.github.fge.lambdas.Chain;
import com.github.fge.lambdas.ThrowablesFactory;

import java.util.function.IntToLongFunction;

public class IntToLongFunctionChain
    extends Chain<IntToLongFunction, ThrowingIntToLongFunction, IntToLongFunctionChain>
    implements ThrowingIntToLongFunction
{
    public IntToLongFunctionChain(
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
    public IntToLongFunctionChain orTryWith(
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

        return new IntToLongFunctionChain(intToLongFunction);
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
                throw ThrowablesFactory.INSTANCE.get(exclass, throwable);
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
