package com.github.fge.lambdas.functions;

import com.github.fge.lambdas.Chainer;

import java.util.function.Function;

public class FunctionChainer<T, R>
    extends Chainer<Function<T, R>, ThrowingFunction<T, R>, FunctionChainer<T, R>>
    implements ThrowingFunction<T, R>
{
    public FunctionChainer(final ThrowingFunction<T, R> function)
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
    public FunctionChainer<T, R> orTryWith(
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

        return new FunctionChainer<>(function);
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
                throw rethrow(exclass, throwable);
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
