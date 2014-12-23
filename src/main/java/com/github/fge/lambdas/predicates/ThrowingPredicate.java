package com.github.fge.lambdas.predicates;

public interface ThrowingPredicate<T>
{
    boolean test(T t)
        throws Throwable;
}
