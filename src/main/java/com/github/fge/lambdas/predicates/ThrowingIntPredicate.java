package com.github.fge.lambdas.predicates;

import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.IntPredicate;

/**
 * A throwing {@link IntPredicate}
 */
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
        } catch (Throwable throwable) {
            throw new ThrownByLambdaException(throwable);
        }
    }
}
