package com.github.fge.lambdas.consumers;

public interface ThrowingConsumer<T>
{
    void accept(T t)
        throws Throwable;
}
