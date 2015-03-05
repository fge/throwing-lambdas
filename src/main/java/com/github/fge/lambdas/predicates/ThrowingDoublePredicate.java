package com.github.fge.lambdas.predicates;

import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.DoublePredicate;

/**
 * A throwing {@link DoublePredicate}
 */
@FunctionalInterface
public interface ThrowingDoublePredicate
    extends DoublePredicate
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
        } catch (Throwable throwable) {
            throw new ThrownByLambdaException(throwable);
        }
    }
}
