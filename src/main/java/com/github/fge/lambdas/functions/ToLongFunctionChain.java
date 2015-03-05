package com.github.fge.lambdas.functions;

import com.github.fge.lambdas.Chain;
import com.github.fge.lambdas.ThrowablesFactory;

import java.util.function.ToLongFunction;

public class ToLongFunctionChain<T>
    extends Chain<ToLongFunction<T>, ThrowingToLongFunction<T>, ToLongFunctionChain<T>>
    implements ThrowingToLongFunction<T>
{
    public ToLongFunctionChain(
        final ThrowingToLongFunction<T> throwing)
    {
        super(throwing);
    }

    @Override
    public long doApplyAsLong(final T value)
        throws Throwable
    {
        return throwing.doApplyAsLong(value);
    }

    @Override
    public ToLongFunctionChain<T> orTryWith(
        final ThrowingToLongFunction<T> other)
    {
        final ThrowingToLongFunction<T> toLongFunction = value -> {
            try {
                return throwing.doApplyAsLong(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return other.doApplyAsLong(value);
            }
        };

        return new ToLongFunctionChain<>(toLongFunction);
    }

    @Override
    public <E extends RuntimeException> ThrowingToLongFunction<T> orThrow(
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
    public ToLongFunction<T> fallbackTo(final ToLongFunction<T> fallback)
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

    public ToLongFunction<T> orReturn(final long retval)
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
