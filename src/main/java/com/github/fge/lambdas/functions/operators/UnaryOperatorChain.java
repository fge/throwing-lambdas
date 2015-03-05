package com.github.fge.lambdas.functions.operators;

import com.github.fge.lambdas.Chain;
import com.github.fge.lambdas.ThrowablesFactory;

import java.util.function.UnaryOperator;

public class UnaryOperatorChain<T>
    extends Chain<UnaryOperator<T>, ThrowingUnaryOperator<T>, UnaryOperatorChain<T>>
    implements ThrowingUnaryOperator<T>
{
    public UnaryOperatorChain(
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
    public UnaryOperatorChain<T> orTryWith(final ThrowingUnaryOperator<T> other)
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

        return new UnaryOperatorChain<>(unaryOperator);
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
