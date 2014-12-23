package com.github.fge.lambdas.functions;

public interface ThrowingFunction<T, R>
{
    R apply(T t)
        throws Throwable;
}
