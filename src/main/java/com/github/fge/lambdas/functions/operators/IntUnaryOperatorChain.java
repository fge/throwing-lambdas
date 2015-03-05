package com.github.fge.lambdas.functions.operators;

import com.github.fge.lambdas.Chain;
import com.github.fge.lambdas.ThrowablesFactory;

import java.util.function.IntUnaryOperator;

public class IntUnaryOperatorChain
    extends Chain<IntUnaryOperator, ThrowingIntUnaryOperator, IntUnaryOperatorChain>
    implements ThrowingIntUnaryOperator
{
    public IntUnaryOperatorChain(
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
    public IntUnaryOperatorChain orTryWith(
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

        return new IntUnaryOperatorChain(intUnaryOperator);
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
                throw ThrowablesFactory.INSTANCE.get(exclass, throwable);
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
