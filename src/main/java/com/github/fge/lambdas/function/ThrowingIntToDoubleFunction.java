package com.github.fge.lambdas.function;

public interface ThrowingIntToDoubleFunction
{
    double apply(int value)
        throws Throwable;
}
