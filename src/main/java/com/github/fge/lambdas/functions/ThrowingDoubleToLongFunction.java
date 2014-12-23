package com.github.fge.lambdas.functions;

public interface ThrowingDoubleToLongFunction
{
    long apply(double value)
        throws Throwable;
}
