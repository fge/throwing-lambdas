package com.github.fge.lambdas.predicates;

public interface ThrowingDoublePredicate
{
    boolean test(double value)
        throws Throwable;
}
