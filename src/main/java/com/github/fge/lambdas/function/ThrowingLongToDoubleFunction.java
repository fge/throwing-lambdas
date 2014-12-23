package com.github.fge.lambdas.function;

public interface ThrowingLongToDoubleFunction
{
    double apply(long value)
        throws Throwable;
}
