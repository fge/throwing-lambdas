package com.github.fge.lambdas.consumers.twoarity;

public interface ThrowingBiConsumer<T, U>
{
    void accept(T t, U u)
        throws Throwable;
}
