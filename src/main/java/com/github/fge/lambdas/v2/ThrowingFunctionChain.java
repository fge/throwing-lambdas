package com.github.fge.lambdas.v2;

import com.github.fge.lambdas.ThrowablesFactory;

import java.util.function.Function;

public final class ThrowingFunctionChain<T, R>
    extends Chainer<Function<T, R>, ThrowingFunction<T, R>, ThrowingFunctionChain<T, R>>
    implements ThrowingFunction<T, R>
{
    public ThrowingFunctionChain(final ThrowingFunction<T, R> function)
    {
        super(function);
    }

    @Override
    public R doApply(final T t)
        throws Throwable
    {
        return throwing.doApply(t);
    }

    @Override
    public ThrowingFunctionChain<T, R> orTryWith(
        final ThrowingFunction<T, R> other)
    {
        final ThrowingFunction<T, R> function = t -> {
            try {
                return throwing.doApply(t);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return other.doApply(t);
            }
        };

        return new ThrowingFunctionChain<>(function);
    }

    @Override
    public <E extends RuntimeException> ThrowingFunction<T, R> orThrow(
        final Class<E> exclass)
    {

        return t -> {
            try {
                return throwing.doApply(t);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable throwable) {
                throw  ThrowablesFactory.INSTANCE.get(exclass, throwable);
            }
        };
    }

    @Override
    public Function<T, R> fallbackTo(final Function<T, R> fallback)
    {
        return t -> {
            try {
                return doApply(t);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return fallback.apply(t);
            }
        };
    }
}
