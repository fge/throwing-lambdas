package com.github.fge.lambdas.functions;

public interface ThrowingIntToLongFunction
{
    long apply(int value)
        throws Throwable;
}
