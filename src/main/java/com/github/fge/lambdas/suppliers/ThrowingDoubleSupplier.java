package com.github.fge.lambdas.suppliers;

public interface ThrowingDoubleSupplier
{
    double getAsDouble()
        throws Throwable;
}
