package com.github.fge.lambdas.functions.intfunctions;

public interface ThrowingIntFunction<R>
{
    R apply(int value)
        throws Throwable;
}
