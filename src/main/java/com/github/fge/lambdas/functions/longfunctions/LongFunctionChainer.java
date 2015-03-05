package com.github.fge.lambdas.functions.longfunctions;

import com.github.fge.lambdas.Chainer;

import java.util.function.LongFunction;

public class LongFunctionChainer<R>
    extends Chainer<LongFunction<R>, ThrowingLongFunction<R>, LongFunctionChainer<R>>
    implements ThrowingLongFunction<R>
{
    public LongFunctionChainer(
        final ThrowingLongFunction<R> throwing)
    {
        super(throwing);
    }

    @Override
    public R doApply(final long value)
        throws Throwable
    {
        return throwing.doApply(value);
    }

    @Override
    public LongFunctionChainer<R> orTryWith(final ThrowingLongFunction<R> other)
    {
        final ThrowingLongFunction<R> longFunction = value -> {
            try {
                return throwing.doApply(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return other.doApply(value);
            }
        };

        return new LongFunctionChainer<>(longFunction);
    }

    @Override
    public <E extends RuntimeException> ThrowingLongFunction<R> orThrow(
        final Class<E> exclass)
    {
        return value -> {
            try {
                return throwing.doApply(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable throwable) {
                throw rethrow(exclass, throwable);
            }
        };
    }

    @Override
    public LongFunction<R> fallbackTo(final LongFunction<R> fallback)
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

    @Override
    public LongFunction<R> sneakyThrow()
    {
        return value -> {
            try {
                return throwing.doApply(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable throwable) {
                throw doSneakyThrow(throwable);
            }
        };
    }

    public LongFunction<R> orReturn(final R retval)
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
