package com.github.fge.lambdas.functions.operators;

import com.github.fge.lambdas.Chain;
import com.github.fge.lambdas.ThrowablesFactory;

import java.util.function.LongBinaryOperator;

public class LongBinaryOperatorChain
    extends Chain<LongBinaryOperator, ThrowingLongBinaryOperator, LongBinaryOperatorChain>
    implements ThrowingLongBinaryOperator
{
    public LongBinaryOperatorChain(
        final ThrowingLongBinaryOperator throwing)
    {
        super(throwing);
    }

    @Override
    public long doApplyAsLong(final long left, final long right)
        throws Throwable
    {
        return throwing.doApplyAsLong(left, right);
    }

    @Override
    public LongBinaryOperatorChain orTryWith(
        final ThrowingLongBinaryOperator other)
    {
        final ThrowingLongBinaryOperator longBinaryOperator = (left, right) -> {
            try {
                return throwing.doApplyAsLong(left, right);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return other.doApplyAsLong(left, right);
            }
        };

        return new LongBinaryOperatorChain(longBinaryOperator);
    }

    @Override
    public <E extends RuntimeException> ThrowingLongBinaryOperator orThrow(
        final Class<E> exclass)
    {
        return (left, right) -> {
            try {
                return throwing.doApplyAsLong(left, right);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable throwable) {
                throw ThrowablesFactory.INSTANCE.get(exclass, throwable);
            }
        };
    }

    @Override
    public LongBinaryOperator fallbackTo(final LongBinaryOperator fallback)
    {
        return (left, right) -> {
            try {
                return throwing.doApplyAsLong(left, right);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return fallback.applyAsLong(left, right);
            }
        };
    }

    public LongBinaryOperator orReturn(final long retval)
    {
        return (left, right) -> {
            try {
                return throwing.doApplyAsLong(left, right);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return retval;
            }
        };
    }

    public LongBinaryOperator orReturnLeft()
    {
        return (left, right) -> {
            try {
                return throwing.doApplyAsLong(left, right);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return left;
            }
        };
    }

    public LongBinaryOperator orReturnRight()
    {
        return (left, right) -> {
            try {
                return throwing.doApplyAsLong(left, right);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return right;
            }
        };
    }
}
