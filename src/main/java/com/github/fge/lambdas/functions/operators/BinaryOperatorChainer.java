package com.github.fge.lambdas.functions.operators;

import com.github.fge.lambdas.Chainer;

import java.util.function.BinaryOperator;

public class BinaryOperatorChainer<T>
    extends Chainer<BinaryOperator<T>, ThrowingBinaryOperator<T>, BinaryOperatorChainer<T>>
    implements ThrowingBinaryOperator<T>
{
    public BinaryOperatorChainer(
        final ThrowingBinaryOperator<T> throwing)
    {
        super(throwing);
    }

    @Override
    public T doApply(final T t, final T u)
        throws Throwable
    {
        return throwing.doApply(t, u);
    }

    @Override
    public BinaryOperatorChainer<T> orTryWith(
        final ThrowingBinaryOperator<T> other)
    {
        final ThrowingBinaryOperator<T> binaryOperator = (t, u) -> {
            try {
                return throwing.doApply(t, u);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return other.doApply(t, u);
            }
        };

        return new BinaryOperatorChainer<>(binaryOperator);
    }

    @Override
    public <E extends RuntimeException> ThrowingBinaryOperator<T> orThrow(
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
    public BinaryOperator<T> fallbackTo(final BinaryOperator<T> fallback)
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
    public BinaryOperator<T> sneakyThrow()
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

    public BinaryOperator<T> orReturn(final T retval)
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

    public BinaryOperator<T> orReturnLeft()
    {
        return (t, u) -> {
            try {
                return throwing.doApply(t, u);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return t;
            }
        };
    }

    public BinaryOperator<T> orReturnRight()
    {
        return (t, u) -> {
            try {
                return throwing.doApply(t, u);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return u;
            }
        };
    }
}
