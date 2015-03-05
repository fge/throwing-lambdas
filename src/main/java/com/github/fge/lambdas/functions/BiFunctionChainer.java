package com.github.fge.lambdas.functions;

import com.github.fge.lambdas.Chainer;

import java.util.function.BiFunction;

public class BiFunctionChainer<T, U, R>
    extends Chainer<BiFunction<T, U, R>, ThrowingBiFunction<T, U, R>, BiFunctionChainer<T, U, R>>
    implements ThrowingBiFunction<T, U, R>
{
    public BiFunctionChainer(final ThrowingBiFunction<T, U, R> throwing)
    {
        super(throwing);
    }

    @Override
    public R doApply(final T t, final U u)
        throws Throwable
    {
        return throwing.doApply(t, u);
    }

    @Override
    public BiFunctionChainer<T, U, R> orTryWith(
        final ThrowingBiFunction<T, U, R> other)
    {
        final ThrowingBiFunction<T, U, R> biFunction = (t, u) -> {
            try {
                return throwing.doApply(t, u);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return other.doApply(t, u);
            }
        };

        return new BiFunctionChainer<>(biFunction);
    }

    @Override
    public <E extends RuntimeException> ThrowingBiFunction<T, U, R> orThrow(
        final Class<E> exclass)
    {
        return (t, u) -> {
            try {
                return throwing.doApply(t, u);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable throwable) {
                throw rethrow(exclass, throwable);
            }
        };
    }

    @Override
    public BiFunction<T, U, R> fallbackTo(final BiFunction<T, U, R> fallback)
    {
        return (t, u) -> {
            try {
                return throwing.doApply(t, u);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return fallback.apply(t, u);
            }
        };
    }

    @Override
    public BiFunction<T, U, R> sneakyThrow()
    {
        return (t, u) -> {
            try {
                return throwing.doApply(t, u);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable throwable) {
                throw doSneakyThrow(throwable);
            }
        };
    }

    public BiFunction<T, U, R> orReturn(final R retval)
    {
        return (t, u) -> {
            try {
                return throwing.doApply(t, u);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return retval;
            }
        };
    }
}
