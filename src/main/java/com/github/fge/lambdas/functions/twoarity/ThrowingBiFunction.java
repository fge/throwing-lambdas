package com.github.fge.lambdas.functions.twoarity;

public interface ThrowingBiFunction<T, U, R>
{
    R apply(T t, U u)
        throws Throwable;
}
