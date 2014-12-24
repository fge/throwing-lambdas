package com.github.fge.lambdas.collectors;

public interface ThrowingBiConsumer<T, U>
{
    void accept(T t, U u)
        throws Throwable;
}
