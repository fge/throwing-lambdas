package com.github.fge.lambdas.function.operators;

import com.github.fge.lambdas.Chain;
import com.github.fge.lambdas.ThrowablesFactory;

import java.util.function.IntBinaryOperator;

public class IntBinaryOperatorChain
    extends Chain<IntBinaryOperator, ThrowingIntBinaryOperator, IntBinaryOperatorChain>
    implements ThrowingIntBinaryOperator
{
    public IntBinaryOperatorChain(
        final ThrowingIntBinaryOperator throwing)
    {
        super(throwing);
    }

    @Override
    public int doApplyAsInt(final int left, final int right)
        throws Throwable
    {
        return throwing.doApplyAsInt(left, right);
    }

    @Override
    public IntBinaryOperatorChain orTryWith(
        final ThrowingIntBinaryOperator other)
    {
        final ThrowingIntBinaryOperator intBinaryOperator = (left, right) -> {
            try {
                return throwing.doApplyAsInt(left, right);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return other.doApplyAsInt(left, right);
            }
        };

        return new IntBinaryOperatorChain(intBinaryOperator);
    }

    @Override
    public <E extends RuntimeException> ThrowingIntBinaryOperator orThrow(
        final Class<E> exclass)
    {
        return (left, right) -> {
            try {
                return throwing.doApplyAsInt(left, right);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable throwable) {
                throw ThrowablesFactory.INSTANCE.get(exclass, throwable);
            }
        };
    }

    @Override
    public IntBinaryOperator fallbackTo(final IntBinaryOperator fallback)
    {
        return (left, right) -> {
            try {
                return throwing.doApplyAsInt(left, right);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return fallback.applyAsInt(left, right);
            }
        };
    }

    public IntBinaryOperator orReturn(final int retval)
    {
        return (left, right) -> {
            try {
                return throwing.doApplyAsInt(left, right);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return retval;
            }
        };
    }

    public IntBinaryOperator orReturnLeft()
    {
        return (left, right) -> {
            try {
                return throwing.doApplyAsInt(left, right);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return left;
            }
        };
    }

    public IntBinaryOperator orReturnRight()
    {
        return (left, right) -> {
            try {
                return throwing.doApplyAsInt(left, right);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return right;
            }
        };
    }
}
