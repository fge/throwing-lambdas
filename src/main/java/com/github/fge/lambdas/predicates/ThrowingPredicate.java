package com.github.fge.lambdas.predicates;

import com.github.fge.lambdas.ThrowablesFactory;
import com.github.fge.lambdas.ThrowingFunctionalInterface;
import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.Predicate;

/**
 * A throwing {@link Predicate}
 *
 * @param <T> type parameter of the argument to this predicate
 */
@FunctionalInterface
public interface ThrowingPredicate<T>
    extends Predicate<T>,
    ThrowingFunctionalInterface<ThrowingPredicate<T>, Predicate<T>>
{
    boolean doTest(T t)
        throws Throwable;
    
    @Override
    default boolean test(T t)
    {
        try {
            return doTest(t);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable tooBad) {
            throw new ThrownByLambdaException(tooBad);
        }
    }

    @Override
    default ThrowingPredicate<T> orTryWith(ThrowingPredicate<T> other)
    {
        return t -> {
            try {
                return doTest(t);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return other.test(t);
            }
        };
    }

    @Override
    default Predicate<T> fallbackTo(Predicate<T> fallback)
    {
        return t -> {
            try {
                return doTest(t);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return fallback.test(t);
            }
        };
    }

    @Override
    default <E extends RuntimeException> Predicate<T> orThrow(
        Class<E> exceptionClass)
    {
        return t -> {
            try {
                return doTest(t);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable tooBad) {
                throw ThrowablesFactory.INSTANCE.get(exceptionClass, tooBad);
            }
        };
    }

    default Predicate<T> orReturn(boolean value)
    {
        return t -> {
            try {
                return doTest(t);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return value;
            }
        };
    }
}
