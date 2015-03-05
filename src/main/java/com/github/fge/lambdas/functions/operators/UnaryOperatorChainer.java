package com.github.fge.lambdas.functions.operators;

import com.github.fge.lambdas.Chainer;
import com.github.fge.lambdas.ThrowablesFactory;

import java.util.function.UnaryOperator;

public class UnaryOperatorChainer<T>
    extends Chainer<UnaryOperator<T>, ThrowingUnaryOperator<T>, UnaryOperatorChainer<T>>
    implements ThrowingUnaryOperator<T>
{
    public UnaryOperatorChainer(
        final ThrowingUnaryOperator<T> throwing)
    {
        super(throwing);
    }

    @Override
    public T doApply(final T t)
        throws Throwable
    {
        return throwing.doApply(t);
    }

    @Override
    public UnaryOperatorChainer<T> orTryWith(final ThrowingUnaryOperator<T> other)
    {
        final ThrowingUnaryOperator<T> unaryOperator = t -> {
            try {
                return throwing.doApply(t);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return other.doApply(t);
            }
        };

        return new UnaryOperatorChainer<>(unaryOperator);
    }

    @Override
    public <E extends RuntimeException> ThrowingUnaryOperator<T> orThrow(
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
    public UnaryOperator<T> fallbackTo(final UnaryOperator<T> fallback)
    {
        return t -> {
            try {
                return throwing.doApply(t);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return fallback.apply(t);
            }
        };
    }

    public UnaryOperator<T> orReturn(final T retval)
    {
        return t -> {
            try {
                return throwing.doApply(t);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return retval;
            }
        };
    }

    public UnaryOperator<T> orReturnSelf()
    {
        return t -> {
            try {
                return throwing.doApply(t);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return t;
            }
        };
    }
}
