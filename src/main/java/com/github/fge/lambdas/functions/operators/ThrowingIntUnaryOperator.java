package com.github.fge.lambdas.functions.operators;

import com.github.fge.lambdas.ThrowablesFactory;
import com.github.fge.lambdas.ThrowingFunctionalInterface;
import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.IntUnaryOperator;

@FunctionalInterface
public interface ThrowingIntUnaryOperator
    extends IntUnaryOperator,
    ThrowingFunctionalInterface<ThrowingIntUnaryOperator, IntUnaryOperator>
{
    int doApplyAsInt(int operand)
        throws Throwable;

    @Override
    default int applyAsInt(int operand)
    {
        try {
            return doApplyAsInt(operand);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable tooBad) {
            throw new ThrownByLambdaException(tooBad);
        }
    }

    @Override
    default ThrowingIntUnaryOperator orTryWith(
        ThrowingIntUnaryOperator other)
    {
        return operand -> {
            try {
                return doApplyAsInt(operand);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return other.applyAsInt(operand);
            }
        };
    }

    @Override
    default IntUnaryOperator fallbackTo(IntUnaryOperator fallback)
    {
        return operand -> {
            try {
                return doApplyAsInt(operand);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return fallback.applyAsInt(operand);
            }
        };
    }

    @Override
    default <E extends RuntimeException> IntUnaryOperator orThrow(
        Class<E> exceptionClass)
    {
        return operand -> {
            try {
                return doApplyAsInt(operand);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable tooBad) {
                throw ThrowablesFactory.INSTANCE.get(exceptionClass, tooBad);
            }
        };
    }

    default IntUnaryOperator orReturn(int defaultValue)
    {
        return operand -> {
            try {
                return doApplyAsInt(operand);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return defaultValue;
            }
        };
    }

    default IntUnaryOperator orReturnSelf()
    {
        return operand -> {
            try {
                return doApplyAsInt(operand);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return operand;
            }
        };
    }
}
