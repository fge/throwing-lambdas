package com.github.fge.lambdas.functions.intfunctions;

import com.github.fge.lambdas.Chain;
import com.github.fge.lambdas.ThrowablesFactory;

import java.util.function.IntFunction;

public class IntFunctionChain<R>
    extends Chain<IntFunction<R>, ThrowingIntFunction<R>, IntFunctionChain<R>>
    implements ThrowingIntFunction<R>
{
    public IntFunctionChain(
        final ThrowingIntFunction<R> throwing)
    {
        super(throwing);
    }

    @Override
    public R doApply(final int value)
        throws Throwable
    {
        return throwing.doApply(value);
    }

    @Override
    public IntFunctionChain<R> orTryWith(
        final ThrowingIntFunction<R> other)
    {
        final ThrowingIntFunction doubleFunction = value -> {
            try {
                return throwing.doApply(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return other.doApply(value);
            }
        };

        return new IntFunctionChain<>(doubleFunction);
    }

    @Override
    public <E extends RuntimeException> ThrowingIntFunction<R> orThrow(
        final Class<E> exclass)
    {
        return value -> {
            try {
                return throwing.doApply(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable throwable) {
                throw ThrowablesFactory.INSTANCE.get(exclass, throwable);
            }
        };
    }

    @Override
    public IntFunction<R> fallbackTo(final IntFunction<R> fallback)
    {
        return value -> {
            try {
                return throwing.doApply(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return fallback.apply(value);
            }
        };
    }

    public IntFunction<R> orReturn(final R retval)
    {
        return value -> {
            try {
                return throwing.doApply(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return retval;
            }
        };
    }
}
