package com.github.fge.lambdas.functions.intfunctions;

public interface ThrowingIntToDoubleFunction
{
    double apply(int value)
        throws Throwable;
}
