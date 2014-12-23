package com.github.fge.lambdas.predicates;

public interface ThrowingIntPredicate
{
    boolean test(int value)
        throws Throwable;
}
