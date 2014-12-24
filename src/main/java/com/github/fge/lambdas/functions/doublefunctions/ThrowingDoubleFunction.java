package com.github.fge.lambdas.functions.doublefunctions;

public interface ThrowingDoubleFunction<R>
{
    R apply(double value)
        throws Throwable;
}
