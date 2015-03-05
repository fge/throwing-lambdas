package com.github.fge.lambdas.functions;

import com.github.fge.lambdas.Chain;
import com.github.fge.lambdas.ThrowablesFactory;

import java.util.function.ToDoubleFunction;

public class ToDoubleFunctionChain<T>
    extends Chain<ToDoubleFunction<T>, ThrowingToDoubleFunction<T>, ToDoubleFunctionChain<T>>
    implements ThrowingToDoubleFunction<T>
{
    public ToDoubleFunctionChain(
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
    public ToDoubleFunctionChain<T> orTryWith(
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

        return new ToDoubleFunctionChain<>(toDoubleFunction);
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
                throw ThrowablesFactory.INSTANCE.get(exclass, throwable);
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
