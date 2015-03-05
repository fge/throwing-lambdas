package com.github.fge.lambdas.functions.operators;

import com.github.fge.lambdas.Chainer;

import java.util.function.IntUnaryOperator;

public class IntUnaryOperatorChainer
    extends Chainer<IntUnaryOperator, ThrowingIntUnaryOperator, IntUnaryOperatorChain>
    implements ThrowingIntUnaryOperator
{
    public IntUnaryOperatorChainer(
        final ThrowingIntUnaryOperator throwing)
    {
        super(throwing);
    }

    @Override
    public int doApplyAsInt(final int operand)
        throws Throwable
    {
        return throwing.doApplyAsInt(operand);
    }

    @Override
    public IntUnaryOperatorChainer orTryWith(
        final ThrowingIntUnaryOperator other)
    {
        final ThrowingIntUnaryOperator intUnaryOperator = operand -> {
            try {
                return throwing.doApplyAsInt(operand);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return other.doApplyAsInt(operand);
            }
        };

        return new IntUnaryOperatorChainer(intUnaryOperator);
    }

    @Override
    public <E extends RuntimeException> ThrowingIntUnaryOperator orThrow(
        final Class<E> exclass)
    {
        return operand -> {
            try {
                return throwing.doApplyAsInt(operand);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable throwable) {
                throw rethrow(exclass, throwable);
            }
        };
    }

    @Override
    public IntUnaryOperator fallbackTo(final IntUnaryOperator fallback)
    {
        return operand -> {
            try {
                return throwing.doApplyAsInt(operand);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return fallback.applyAsInt(operand);
            }
        };
    }

    public IntUnaryOperator orReturn(final int retval)
    {
        return operand -> {
            try {
                return throwing.doApplyAsInt(operand);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return retval;
            }
        };
    }

    public IntUnaryOperator orReturnSelf()
    {
        return operand -> {
            try {
                return throwing.doApplyAsInt(operand);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return operand;
            }
        };
    }
}
