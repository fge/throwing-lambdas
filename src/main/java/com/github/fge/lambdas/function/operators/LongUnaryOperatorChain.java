package com.github.fge.lambdas.function.operators;

import com.github.fge.lambdas.Chain;
import com.github.fge.lambdas.ThrowablesFactory;

import java.util.function.LongUnaryOperator;

public class LongUnaryOperatorChain
    extends Chain<LongUnaryOperator, ThrowingLongUnaryOperator, LongUnaryOperatorChain>
    implements ThrowingLongUnaryOperator
{
    public LongUnaryOperatorChain(
        final ThrowingLongUnaryOperator throwing)
    {
        super(throwing);
    }

    @Override
    public long doApplyAsLong(final long operand)
        throws Throwable
    {
        return throwing.doApplyAsLong(operand);
    }

    @Override
    public LongUnaryOperatorChain orTryWith(
        final ThrowingLongUnaryOperator other)
    {
        final ThrowingLongUnaryOperator longUnaryOperator = operand -> {
            try {
                return throwing.doApplyAsLong(operand);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return other.doApplyAsLong(operand);
            }
        };

        return new LongUnaryOperatorChain(longUnaryOperator);
    }

    @Override
    public <E extends RuntimeException> ThrowingLongUnaryOperator orThrow(
        final Class<E> exclass)
    {
        return operand -> {
            try {
                return throwing.doApplyAsLong(operand);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable throwable) {
                throw ThrowablesFactory.INSTANCE.get(exclass, throwable);
            }
        };
    }

    @Override
    public LongUnaryOperator fallbackTo(final LongUnaryOperator fallback)
    {
        return operand -> {
            try {
                return throwing.doApplyAsLong(operand);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return fallback.applyAsLong(operand);
            }
        };
    }

    public LongUnaryOperator orReturn(final long retval)
    {
        return operand -> {
            try {
                return throwing.doApplyAsLong(operand);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return retval;
            }
        };
    }

    public LongUnaryOperator orReturnSelf()
    {
        return operand -> {
            try {
                return throwing.doApplyAsLong(operand);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return operand;
            }
        };
    }
}
