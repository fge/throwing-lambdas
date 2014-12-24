package com.github.fge.lambdas.consumers;

public interface ThrowingIntConsumer
{
    void accept(int t)
        throws Throwable;
}
