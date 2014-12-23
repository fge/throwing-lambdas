package com.github.fge.lambdas.function;

public interface ThrowingDoubleFunction<R>
{
    R apply(double value)
        throws Throwable;
}
