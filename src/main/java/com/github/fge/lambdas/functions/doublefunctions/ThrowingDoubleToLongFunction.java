package com.github.fge.lambdas.functions.doublefunctions;

public interface ThrowingDoubleToLongFunction
{
    long apply(double value)
        throws Throwable;
}
