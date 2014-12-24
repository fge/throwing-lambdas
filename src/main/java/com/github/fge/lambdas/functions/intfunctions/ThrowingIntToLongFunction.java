package com.github.fge.lambdas.functions.intfunctions;

public interface ThrowingIntToLongFunction
{
    long apply(int value)
        throws Throwable;
}
