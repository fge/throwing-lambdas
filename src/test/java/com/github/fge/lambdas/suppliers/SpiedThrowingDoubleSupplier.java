package com.github.fge.lambdas.suppliers;

import org.mockito.Mockito;

public class SpiedThrowingDoubleSupplier
    implements ThrowingDoubleSupplier
{
    public static ThrowingDoubleSupplier newSpy()
    {
        return Mockito.spy(new SpiedThrowingDoubleSupplier());
    }

    @Override
    public double doGetAsDouble()
            throws Throwable
    {
        return 0.0D; // Not null, but good enough, hopefully.
    }
}
