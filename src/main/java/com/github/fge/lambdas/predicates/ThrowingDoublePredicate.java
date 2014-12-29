package com.github.fge.lambdas.predicates;

import com.github.fge.lambdas.ThrowablesFactory;
import com.github.fge.lambdas.ThrowingFunctionalInterface;
import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.DoublePredicate;

/**
 * A throwing {@link DoublePredicate}
 */
@FunctionalInterface
public interface ThrowingDoublePredicate
    extends DoublePredicate,
    ThrowingFunctionalInterface<ThrowingDoublePredicate, DoublePredicate>
{
    boolean doTest(double value)
        throws Throwable;

    @Override
    default boolean test(double value)
    {
        try {
            return doTest(value);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable tooBad) {
            throw new ThrownByLambdaException(tooBad);
        }
    }

    @Override
    default ThrowingDoublePredicate orTryWith(ThrowingDoublePredicate other)
    {
        return value -> {
            try {
                return doTest(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return other.test(value);
            }
        };
    }

    @Override
    default DoublePredicate fallbackTo(DoublePredicate fallback)
    {
        return value -> {
            try {
                return doTest(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return fallback.test(value);
            }
        };
    }

    @Override
    default <E extends RuntimeException> DoublePredicate orThrow(
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

    default DoublePredicate orReturn(boolean defaultValue)
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
}
