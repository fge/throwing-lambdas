package com.github.fge.lambdas.predicates;

import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.LongPredicate;

/**
 * A throwing {@link LongPredicate}
 */
@FunctionalInterface
public interface ThrowingLongPredicate
    extends LongPredicate
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
}
