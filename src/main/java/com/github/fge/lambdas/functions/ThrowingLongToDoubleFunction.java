package com.github.fge.lambdas.functions;

public interface ThrowingLongToDoubleFunction
{
    double apply(long value)
        throws Throwable;
}
