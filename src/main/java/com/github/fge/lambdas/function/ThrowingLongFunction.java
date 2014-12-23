package com.github.fge.lambdas.function;

public interface ThrowingLongFunction<R>
{
    R apply(long value)
        throws Throwable;
}
