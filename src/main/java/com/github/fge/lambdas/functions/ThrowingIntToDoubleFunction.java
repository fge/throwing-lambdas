package com.github.fge.lambdas.functions;

public interface ThrowingIntToDoubleFunction
{
    double apply(int value)
        throws Throwable;
}
