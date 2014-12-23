package com.github.fge.lambdas.functions;

public interface ThrowingDoubleToIntFunction
{
    int apply(double value)
        throws Throwable;
}
