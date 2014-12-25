package com.github.fge.lambdas.predicates;

import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.Predicate;

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
        } catch (Throwable tooBad) {
            throw new ThrownByLambdaException(tooBad);
        }
    }
}
