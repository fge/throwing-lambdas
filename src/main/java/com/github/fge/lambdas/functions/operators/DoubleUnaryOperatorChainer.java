package com.github.fge.lambdas.functions.operators;

import com.github.fge.lambdas.Chainer;
import com.github.fge.lambdas.ThrowablesFactory;

import java.util.function.DoubleUnaryOperator;

public class DoubleUnaryOperatorChainer
    extends Chainer<DoubleUnaryOperator, ThrowingDoubleUnaryOperator, DoubleUnaryOperatorChainer>
    implements ThrowingDoubleUnaryOperator
{
    public DoubleUnaryOperatorChainer(
        final ThrowingDoubleUnaryOperator throwing)
    {
        super(throwing);
    }

    @Override
    public double doApplyAsDouble(final double operand)
        throws Throwable
    {
        return throwing.doApplyAsDouble(operand);
    }

    @Override
    public DoubleUnaryOperatorChainer orTryWith(
        final ThrowingDoubleUnaryOperator other)
    {
        final ThrowingDoubleUnaryOperator doubleUnaryOperator = operand -> {
            try {
                return throwing.doApplyAsDouble(operand);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return other.doApplyAsDouble(operand);
            }
        };

        return new DoubleUnaryOperatorChainer(doubleUnaryOperator);
    }

    @Override
    public <E extends RuntimeException> ThrowingDoubleUnaryOperator orThrow(
        final Class<E> exclass)
    {
        return operand -> {
            try {
                return throwing.doApplyAsDouble(operand);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable throwable) {
                throw rethrow(exclass, throwable);
            }
        };
    }

    @Override
    public DoubleUnaryOperator fallbackTo(final DoubleUnaryOperator fallback)
    {
        return operand -> {
            try {
                return throwing.doApplyAsDouble(operand);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return fallback.applyAsDouble(operand);
            }
        };
    }

    public DoubleUnaryOperator orReturn(final double retval)
    {
        return operand -> {
            try {
                return throwing.doApplyAsDouble(operand);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return retval;
            }
        };
    }

    public DoubleUnaryOperator orReturnSelf()
    {
        return operand -> {
            try {
                return throwing.doApplyAsDouble(operand);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return operand;
            }
        };
    }
}
