package com.github.fge.lambdas.function;

public interface ThrowingIntToLongFunction
{
    long apply(int value)
        throws Throwable;
}
