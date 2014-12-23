package com.github.fge.lambdas.predicates;

public interface ThrowingLongPredicate
{
    boolean test(long value)
        throws Throwable;
}
