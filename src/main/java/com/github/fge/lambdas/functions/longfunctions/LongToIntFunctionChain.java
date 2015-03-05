package com.github.fge.lambdas.functions.longfunctions;

import com.github.fge.lambdas.Chain;
import com.github.fge.lambdas.ThrowablesFactory;

import java.util.function.LongToIntFunction;

public class LongToIntFunctionChain
    extends Chain<LongToIntFunction, ThrowingLongToIntFunction, LongToIntFunctionChain>
    implements ThrowingLongToIntFunction
{
    public LongToIntFunctionChain(
        final ThrowingLongToIntFunction throwing)
    {
        super(throwing);
    }

    @Override
    public int doApplyAsInt(final long value)
        throws Throwable
    {
        return throwing.doApplyAsInt(value);
    }

    @Override
    public LongToIntFunctionChain orTryWith(
        final ThrowingLongToIntFunction other)
    {
        final ThrowingLongToIntFunction longToIntFunction = value -> {
            try {
                return throwing.doApplyAsInt(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return other.doApplyAsInt(value);
            }
        };

        return new LongToIntFunctionChain(longToIntFunction);
    }

    @Override
    public <E extends RuntimeException> ThrowingLongToIntFunction orThrow(
        final Class<E> exclass)
    {
        return value -> {
            try {
                return throwing.doApplyAsInt(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable throwable) {
                throw ThrowablesFactory.INSTANCE.get(exclass, throwable);
            }
        };
    }

    @Override
    public LongToIntFunction fallbackTo(final LongToIntFunction fallback)
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

    public LongToIntFunction orReturn(final int retval) {
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
