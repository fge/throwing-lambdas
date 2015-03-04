package com.github.fge.lambdas.function;

import com.github.fge.lambdas.ThrowablesFactory;
import com.github.fge.lambdas.Chain;

import java.util.function.Function;

public final class FunctionChain<T, R>
    extends Chain<Function<T, R>, ThrowingFunction<T, R>, FunctionChain<T, R>>
    implements ThrowingFunction<T, R>
{
    public FunctionChain(final ThrowingFunction<T, R> function)
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
    public FunctionChain<T, R> orTryWith(
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

        return new FunctionChain<>(function);
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
                throw ThrowablesFactory.INSTANCE.get(exclass, throwable);
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

    public Function<T, R> orReturn(final R retval)
    {
        return t -> {
            try {
                return doApply(t);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return retval;
            }
        };
    }
}
