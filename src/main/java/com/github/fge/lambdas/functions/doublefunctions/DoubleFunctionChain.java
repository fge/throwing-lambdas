package com.github.fge.lambdas.functions.doublefunctions;

import com.github.fge.lambdas.Chain;
import com.github.fge.lambdas.ThrowablesFactory;

import java.util.function.DoubleFunction;

public class DoubleFunctionChain<R>
    extends Chain<DoubleFunction<R>, ThrowingDoubleFunction<R>, DoubleFunctionChain<R>>
    implements ThrowingDoubleFunction<R>
{
    public DoubleFunctionChain(
        final ThrowingDoubleFunction<R> throwing)
    {
        super(throwing);
    }

    @Override
    public R doApply(final double value)
        throws Throwable
    {
        return throwing.doApply(value);
    }

    @Override
    public DoubleFunctionChain<R> orTryWith(
        final ThrowingDoubleFunction<R> other)
    {
        final ThrowingDoubleFunction doubleFunction = value -> {
            try {
                return throwing.doApply(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return other.doApply(value);
            }
        };

        return new DoubleFunctionChain<>(doubleFunction);
    }

    @Override
    public <E extends RuntimeException> ThrowingDoubleFunction<R> orThrow(
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
    public DoubleFunction<R> fallbackTo(final DoubleFunction<R> fallback)
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

    public DoubleFunction<R> orReturn(final R retval)
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
