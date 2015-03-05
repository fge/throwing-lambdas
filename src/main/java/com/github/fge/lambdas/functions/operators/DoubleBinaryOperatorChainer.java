package com.github.fge.lambdas.functions.operators;

import com.github.fge.lambdas.Chainer;

import java.util.function.DoubleBinaryOperator;

public class DoubleBinaryOperatorChainer
    extends Chainer<DoubleBinaryOperator, ThrowingDoubleBinaryOperator, DoubleBinaryOperatorChainer>
    implements ThrowingDoubleBinaryOperator
{
    public DoubleBinaryOperatorChainer(
        final ThrowingDoubleBinaryOperator throwing)
    {
        super(throwing);
    }

    @Override
    public double doApplyAsDouble(final double left, final double right)
        throws Throwable
    {
        return throwing.doApplyAsDouble(left, right);
    }

    @Override
    public DoubleBinaryOperatorChainer orTryWith(
        final ThrowingDoubleBinaryOperator other)
    {
        final ThrowingDoubleBinaryOperator doubleBinaryOperator
            = (left, right) -> {
                try {
                    return throwing.doApplyAsDouble(left, right);
                } catch (Error | RuntimeException e) {
                    throw e;
                } catch (Throwable ignored) {
                    return other.doApplyAsDouble(left, right);
                }
            };

        return new DoubleBinaryOperatorChainer(doubleBinaryOperator);
    }

    @Override
    public <E extends RuntimeException> ThrowingDoubleBinaryOperator orThrow(
        final Class<E> exclass)
    {
        return (left, right) -> {
            try {
                return throwing.doApplyAsDouble(left, right);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable throwable) {
                throw rethrow(exclass, throwable);
            }
        };
    }

    @Override
    public DoubleBinaryOperator fallbackTo(final DoubleBinaryOperator fallback)
    {
        return (left, right) -> {
            try {
                return throwing.doApplyAsDouble(left, right);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return fallback.applyAsDouble(left, right);
            }
        };
    }

    public DoubleBinaryOperator orReturn(final double retval)
    {
        return (left, right) -> {
            try {
                return throwing.doApplyAsDouble(left, right);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return retval;
            }
        };
    }

    public DoubleBinaryOperator orReturnLeft()
    {
        return (left, right) -> {
            try {
                return throwing.doApplyAsDouble(left, right);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return left;
            }
        };
    }

    public DoubleBinaryOperator orReturnRight()
    {
        return (left, right) -> {
            try {
                return throwing.doApplyAsDouble(left, right);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return right;
            }
        };
    }
}
