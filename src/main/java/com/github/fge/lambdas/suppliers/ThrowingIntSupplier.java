package com.github.fge.lambdas.suppliers;

public interface ThrowingIntSupplier
{
    int getAsInt()
        throws Throwable;
}
