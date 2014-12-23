package com.github.fge.lambdas.functions;

public interface ThrowingLongToIntFunction
{
    int apply(long value)
        throws Throwable;
}
