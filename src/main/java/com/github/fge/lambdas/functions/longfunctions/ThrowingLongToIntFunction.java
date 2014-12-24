package com.github.fge.lambdas.functions.longfunctions;

public interface ThrowingLongToIntFunction
{
    int apply(long value)
        throws Throwable;
}
