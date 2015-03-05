package com.github.fge.lambdas.functions.operators;

import com.github.fge.lambdas.Chainer;

import java.util.function.LongBinaryOperator;

public class LongBinaryOperatorChainer
    extends Chainer<LongBinaryOperator, ThrowingLongBinaryOperator, LongBinaryOperatorChainer>
    implements ThrowingLongBinaryOperator
{
    public LongBinaryOperatorChainer(
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
    public LongBinaryOperatorChainer orTryWith(
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

        return new LongBinaryOperatorChainer(longBinaryOperator);
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
                throw rethrow(exclass, throwable);
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
