package com.github.fge.lambdas.functions.operators;

import com.github.fge.lambdas.ThrowablesFactory;
import com.github.fge.lambdas.ThrowingFunctionalInterface;
import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.LongUnaryOperator;

@FunctionalInterface
public interface ThrowingLongUnaryOperator
    extends LongUnaryOperator,
    ThrowingFunctionalInterface<ThrowingLongUnaryOperator, LongUnaryOperator>
{
    long doApplyAsLong(long operand)
        throws Throwable;

    @Override
    default long applyAsLong(long operand)
    {
        try {
            return doApplyAsLong(operand);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable tooBad) {
            throw new ThrownByLambdaException(tooBad);
        }
    }

    @Override
    default ThrowingLongUnaryOperator orTryWith(
        ThrowingLongUnaryOperator other)
    {
        return operand -> {
            try {
                return doApplyAsLong(operand);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable tooBad) {
                return other.applyAsLong(operand);
            }
        };
    }

    @Override
    default LongUnaryOperator fallbackTo(LongUnaryOperator byDefault)
    {
        return operand -> {
            try {
                return doApplyAsLong(operand);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable tooBad) {
                return byDefault.applyAsLong(operand);
            }
        };
    }

    @Override
    default <E extends RuntimeException> LongUnaryOperator orThrow(
        Class<E> exceptionClass)
    {
        return operand -> {
            try {
                return doApplyAsLong(operand);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable tooBad) {
                throw ThrowablesFactory.INSTANCE.get(exceptionClass, tooBad);
            }
        };
    }

    default LongUnaryOperator orReturn(long defaultValue)
    {
        return operand -> {
            try {
                return doApplyAsLong(operand);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return defaultValue;
            }
        };
    }

    default LongUnaryOperator orReturnSelf()
    {
        return operand -> {
            try {
                return doApplyAsLong(operand);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return operand;
            }
        };
    }
}
