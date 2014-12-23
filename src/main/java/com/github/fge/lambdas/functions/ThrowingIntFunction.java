package com.github.fge.lambdas.functions;

public interface ThrowingIntFunction<R>
{
    R apply(int value)
        throws Throwable;
}
