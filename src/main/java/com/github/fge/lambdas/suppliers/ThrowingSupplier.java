package com.github.fge.lambdas.suppliers;

public interface ThrowingSupplier<T>
{
    T get()
        throws Throwable;
}
