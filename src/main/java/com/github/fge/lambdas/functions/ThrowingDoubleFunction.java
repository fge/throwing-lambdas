package com.github.fge.lambdas.functions;

public interface ThrowingDoubleFunction<R>
{
    R apply(double value)
        throws Throwable;
}
