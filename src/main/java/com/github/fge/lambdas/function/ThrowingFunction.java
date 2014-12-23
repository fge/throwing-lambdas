package com.github.fge.lambdas.function;

public interface ThrowingFunction<T, R>
{
    R apply(T t)
        throws Throwable;
}
