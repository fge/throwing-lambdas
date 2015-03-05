package com.github.fge.lambdas.functions.doublefunctions;

import com.github.fge.lambdas.Chain;
import com.github.fge.lambdas.ThrowablesFactory;

import java.util.function.DoubleToIntFunction;

public class DoubleToIntFunctionChain
    extends Chain<DoubleToIntFunction, ThrowingDoubleToIntFunction, DoubleToIntFunctionChain>
    implements ThrowingDoubleToIntFunction
{
    public DoubleToIntFunctionChain(final ThrowingDoubleToIntFunction throwing)
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
    public DoubleToIntFunctionChain orTryWith(
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

        return new DoubleToIntFunctionChain(doubleToIntFunction);
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
                throw ThrowablesFactory.INSTANCE.get(exclass, throwable);
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
