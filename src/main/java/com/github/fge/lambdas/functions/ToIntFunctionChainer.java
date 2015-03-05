package com.github.fge.lambdas.functions;

import com.github.fge.lambdas.Chainer;
import com.github.fge.lambdas.ThrowablesFactory;

import java.util.function.ToIntFunction;

public class ToIntFunctionChainer<T>
    extends Chainer<ToIntFunction<T>, ThrowingToIntFunction<T>, ToIntFunctionChain<T>>
    implements ThrowingToIntFunction<T>
{
    public ToIntFunctionChainer(
        final ThrowingToIntFunction<T> throwing)
    {
        super(throwing);
    }

    @Override
    public int doApplyAsInt(final T value)
        throws Throwable
    {
        return throwing.doApplyAsInt(value);
    }

    @Override
    public ToIntFunctionChainer<T> orTryWith(
        final ThrowingToIntFunction<T> other)
    {
        final ThrowingToIntFunction<T> toIntFunction = value -> {
            try {
                return throwing.doApplyAsInt(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return other.doApplyAsInt(value);
            }
        };

        return new ToIntFunctionChainer<>(toIntFunction);
    }

    @Override
    public <E extends RuntimeException> ThrowingToIntFunction<T> orThrow(
        final Class<E> exclass)
    {
        return value -> {
            try {
                return throwing.doApplyAsInt(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable throwable) {
                throw rethrow(exclass, throwable);
            }
        };
    }

    @Override
    public ToIntFunction<T> fallbackTo(final ToIntFunction<T> fallback)
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

    public ToIntFunction<T> orReturn(final int retval)
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
