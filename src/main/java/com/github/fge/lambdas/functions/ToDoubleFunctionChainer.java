package com.github.fge.lambdas.functions;

import com.github.fge.lambdas.Chainer;

import java.util.function.ToDoubleFunction;

public class ToDoubleFunctionChainer<T>
    extends Chainer<ToDoubleFunction<T>, ThrowingToDoubleFunction<T>, ToDoubleFunctionChainer<T>>
    implements ThrowingToDoubleFunction<T>
{
    public ToDoubleFunctionChainer(
        final ThrowingToDoubleFunction<T> throwing)
    {
        super(throwing);
    }

    @Override
    public double doApplyAsDouble(final T value)
        throws Throwable
    {
        return throwing.doApplyAsDouble(value);
    }

    @Override
    public ToDoubleFunctionChainer<T> orTryWith(
        final ThrowingToDoubleFunction<T> other)
    {
        final ThrowingToDoubleFunction<T> toDoubleFunction = value -> {
            try {
                return throwing.doApplyAsDouble(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return other.doApplyAsDouble(value);
            }
        };

        return new ToDoubleFunctionChainer<>(toDoubleFunction);
    }

    @Override
    public <E extends RuntimeException> ThrowingToDoubleFunction<T> orThrow(
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
    public ToDoubleFunction<T> fallbackTo(final ToDoubleFunction<T> fallback)
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

    @Override
    public ToDoubleFunction<T> sneakyThrow()
    {
        return value -> {
            try {
                return throwing.doApplyAsDouble(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable throwable) {
                throw doSneakyThrow(throwable);
            }
        };
    }

    public ToDoubleFunction<T> orReturn(final double retval)
    {
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
