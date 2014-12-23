package com.github.fge.lambdas.functions;

public interface ThrowingLongFunction<R>
{
    R apply(long value)
        throws Throwable;
}
