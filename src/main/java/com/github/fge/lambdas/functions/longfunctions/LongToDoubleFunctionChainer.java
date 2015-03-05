package com.github.fge.lambdas.functions.longfunctions;

import com.github.fge.lambdas.Chainer;
import com.github.fge.lambdas.ThrowablesFactory;

import java.util.function.LongToDoubleFunction;

public class LongToDoubleFunctionChainer
    extends Chainer<LongToDoubleFunction, ThrowingLongToDoubleFunction, LongToDoubleFunctionChain>
    implements ThrowingLongToDoubleFunction
{
    public LongToDoubleFunctionChainer(
        final ThrowingLongToDoubleFunction throwing)
    {
        super(throwing);
    }

    @Override
    public double doApplyAsDouble(final long value)
        throws Throwable
    {
        return throwing.doApplyAsDouble(value);
    }

    @Override
    public LongToDoubleFunctionChainer orTryWith(
        final ThrowingLongToDoubleFunction other)
    {
        final ThrowingLongToDoubleFunction longToDoubleFunction = value -> {
            try {
                return throwing.doApplyAsDouble(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return other.doApplyAsDouble(value);
            }
        };

        return new LongToDoubleFunctionChainer(longToDoubleFunction);
    }

    @Override
    public <E extends RuntimeException> ThrowingLongToDoubleFunction orThrow(
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
    public LongToDoubleFunction fallbackTo(final LongToDoubleFunction fallback)
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

    public LongToDoubleFunction orReturn(final double retval) {
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
