package com.github.fge.lambdas.consumers;

public interface ThrowingDoubleConsumer
{
    void accept(double t)
        throws Throwable;
}
