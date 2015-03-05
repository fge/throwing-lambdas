package com.github.fge.lambdas.function.intfunctions;

import com.github.fge.lambdas.Chain;
import com.github.fge.lambdas.ThrowablesFactory;

import java.util.function.IntToDoubleFunction;

public class IntToDoubleFunctionChain
    extends Chain<IntToDoubleFunction, ThrowingIntToDoubleFunction, IntToDoubleFunctionChain>
    implements ThrowingIntToDoubleFunction
{
    public IntToDoubleFunctionChain(
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
    public IntToDoubleFunctionChain orTryWith(
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

        return new IntToDoubleFunctionChain(intToDoubleFunction);
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
                throw ThrowablesFactory.INSTANCE.get(exclass, throwable);
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
