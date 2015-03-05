package com.github.fge.lambdas.functions.doublefunctions;

import com.github.fge.lambdas.Chain;
import com.github.fge.lambdas.ThrowablesFactory;

import java.util.function.DoubleToLongFunction;

public class DoubleToLongFunctionChain
    extends Chain<DoubleToLongFunction, ThrowingDoubleToLongFunction, DoubleToLongFunctionChain>
    implements ThrowingDoubleToLongFunction
{
    public DoubleToLongFunctionChain(
        final ThrowingDoubleToLongFunction throwing)
    {
        super(throwing);
    }

    @Override
    public long doApplyAsLong(final double value)
        throws Throwable
    {
        return throwing.doApplyAsLong(value);
    }

    @Override
    public DoubleToLongFunctionChain orTryWith(
        final ThrowingDoubleToLongFunction other)
    {
        final ThrowingDoubleToLongFunction doubleToLongFunction = value -> {
            try {
                return throwing.doApplyAsLong(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return other.doApplyAsLong(value);
            }
        };

        return new DoubleToLongFunctionChain(doubleToLongFunction);
    }

    @Override
    public <E extends RuntimeException> ThrowingDoubleToLongFunction orThrow(
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
    public DoubleToLongFunction fallbackTo(final DoubleToLongFunction fallback)
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

    public DoubleToLongFunction orReturn(final long retval)
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
