package com.github.fge.lambdas.function.operators;

import com.github.fge.lambdas.Chain;
import com.github.fge.lambdas.ThrowablesFactory;

import java.util.function.BinaryOperator;

public class BinaryOperatorChain<T>
    extends Chain<BinaryOperator<T>, ThrowingBinaryOperator<T>, BinaryOperatorChain<T>>
    implements ThrowingBinaryOperator<T>
{
    public BinaryOperatorChain(
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
    public BinaryOperatorChain<T> orTryWith(
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

        return new BinaryOperatorChain<>(binaryOperator);
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
                throw ThrowablesFactory.INSTANCE.get(exclass, throwable);
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
