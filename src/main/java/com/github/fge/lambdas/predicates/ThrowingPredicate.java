package com.github.fge.lambdas.predicates;

import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.Predicate;

/**
 * A throwing {@link Predicate}
 *
 * @param <T> type parameter of the argument to this predicate
 */
@FunctionalInterface
public interface ThrowingPredicate<T>
    extends Predicate<T>
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
        } catch (Throwable throwable) {
            throw new ThrownByLambdaException(throwable);
        }
    }
}
