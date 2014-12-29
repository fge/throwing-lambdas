package com.github.fge.lambdas.suppliers;

import org.mockito.Mockito;

public class SpiedThrowingIntSupplier
    implements ThrowingIntSupplier
{
    public static ThrowingIntSupplier newSpy()
    {
        return Mockito.spy(new SpiedThrowingIntSupplier());
    }

    @Override
    public int doGetAsInt()
        throws Throwable
    {
        return 0; // Not null, but good enough, hopefully.
    }
}
