package com.github.fge.lambdas.functions.operators;

import com.github.fge.lambdas.ThrowablesFactory;
import com.github.fge.lambdas.ThrowingFunctionalInterface;
import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.DoubleUnaryOperator;

@FunctionalInterface
public interface ThrowingDoubleUnaryOperator
    extends DoubleUnaryOperator,
    ThrowingFunctionalInterface<ThrowingDoubleUnaryOperator, DoubleUnaryOperator>
{
    double doApplyAsDouble(double operand)
        throws Throwable;

    @Override
    default double applyAsDouble(double operand)
    {
        try {
            return doApplyAsDouble(operand);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable tooBad) {
            throw new ThrownByLambdaException(tooBad);
        }
    }

    @Override
    default ThrowingDoubleUnaryOperator orTryWith(
        ThrowingDoubleUnaryOperator other)
    {
        return operand -> {
            try {
                return doApplyAsDouble(operand);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return other.applyAsDouble(operand);
            }
        };
    }

    @Override
    default DoubleUnaryOperator fallbackTo(DoubleUnaryOperator byDefault)
    {
        return operand -> {
            try {
                return doApplyAsDouble(operand);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return byDefault.applyAsDouble(operand);
            }
        };
    }

    @Override
    default <E extends RuntimeException> DoubleUnaryOperator orThrow(
        Class<E> exceptionClass)
    {
        return operand -> {
            try {
                return doApplyAsDouble(operand);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable tooBad) {
                throw ThrowablesFactory.INSTANCE.get(exceptionClass, tooBad);
            }
        };
    }

    default DoubleUnaryOperator orReturn(double defaultValue)
    {
        return operand -> {
            try {
                return doApplyAsDouble(operand);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return defaultValue;
            }
        };
    }

    default DoubleUnaryOperator orReturnSelf()
    {
        return operand -> {
            try {
                return doApplyAsDouble(operand);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return operand;
            }
        };
    }
}
