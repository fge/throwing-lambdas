package com.github.fge.lambdas.predicates;

import com.github.fge.lambdas.ThrowablesFactory;
import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.IntPredicate;

@FunctionalInterface
public interface ThrowingIntPredicate
    extends IntPredicate
{
    boolean doTest(int value)
        throws Throwable;
        
    @Override
    default boolean test(int value)
    {
        try {
            return doTest(value);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable tooBad) {
            throw new ThrownByLambdaException(tooBad);
        }
    }

    default IntPredicate orReturn(boolean defaultValue)
    {
        return value -> {
            try {
                return doTest(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return defaultValue;
            }
        };
    }

    default <E extends RuntimeException> IntPredicate orThrow(
        Class<E> exceptionClass)
    {
        return value -> {
            try {
                return doTest(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable tooBad) {
                throw ThrowablesFactory.INSTANCE.get(exceptionClass, tooBad);
            }
        };
    }
}
