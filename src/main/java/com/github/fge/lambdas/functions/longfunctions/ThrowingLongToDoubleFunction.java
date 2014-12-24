package com.github.fge.lambdas.functions.longfunctions;

public interface ThrowingLongToDoubleFunction
{
    double apply(long value)
        throws Throwable;
}
