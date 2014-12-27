package com.github.fge.lambdas.functions.operators;

import com.github.fge.lambdas.ThrowablesFactory;
import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.DoubleUnaryOperator;

@FunctionalInterface
public interface ThrowingDoubleUnaryOperator
    extends DoubleUnaryOperator
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
}
