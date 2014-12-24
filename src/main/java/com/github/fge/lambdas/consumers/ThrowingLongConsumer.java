package com.github.fge.lambdas.consumers;

public interface ThrowingLongConsumer
{
    void accept(long t)
        throws Throwable;
}
