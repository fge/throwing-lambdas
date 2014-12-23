package com.github.fge.lambdas.function;

public interface ThrowingIntFunction<R>
{
    R apply(int value)
        throws Throwable;
}
