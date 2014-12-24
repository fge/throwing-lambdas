package com.github.fge.lambdas.functions.longfunctions;

public interface ThrowingLongFunction<R>
{
    R apply(long value)
        throws Throwable;
}
