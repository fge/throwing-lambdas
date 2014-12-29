package com.github.fge.lambdas.suppliers;

import org.mockito.Mockito;

public class SpiedThrowingLongSupplier
    implements ThrowingLongSupplier
{
    public static ThrowingLongSupplier newSpy()
    {
        return Mockito.spy(new SpiedThrowingLongSupplier());
    }

    @Override
    public long doGetAsLong()
        throws Throwable
    {
        return 0L; // Not null, but good enough, hopefully.
    }
}
