package com.github.fge.lambdas.predicates;

import com.github.fge.lambdas.ThrowablesFactory;
import com.github.fge.lambdas.ThrowingFunctionalInterface;
import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.LongPredicate;

/**
 * A throwing {@link LongPredicate}
 */
@FunctionalInterface
public interface ThrowingLongPredicate
    extends LongPredicate,
    ThrowingFunctionalInterface<ThrowingLongPredicate, LongPredicate>
{
    boolean doTest(long value)
        throws Throwable;

    @Override
    default boolean test(long value)
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
    default ThrowingLongPredicate orTryWith(
        ThrowingLongPredicate other)
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
    default LongPredicate fallbackTo(LongPredicate fallback)
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
    default <E extends RuntimeException> LongPredicate orThrow(
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

    default LongPredicate orReturn(boolean defaultValue)
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
