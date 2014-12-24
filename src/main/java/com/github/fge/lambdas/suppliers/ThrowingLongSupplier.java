package com.github.fge.lambdas.suppliers;

public interface ThrowingLongSupplier
{
    long getAsLong()
        throws Throwable;
}
