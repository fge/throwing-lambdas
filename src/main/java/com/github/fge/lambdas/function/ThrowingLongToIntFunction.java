package com.github.fge.lambdas.function;

public interface ThrowingLongToIntFunction
{
    int apply(long value)
        throws Throwable;
}
