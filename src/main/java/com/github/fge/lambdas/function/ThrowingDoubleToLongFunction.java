package com.github.fge.lambdas.function;

public interface ThrowingDoubleToLongFunction
{
    long apply(double value)
        throws Throwable;
}
