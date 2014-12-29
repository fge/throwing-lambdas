package com.github.fge.lambdas.suppliers;

import com.github.fge.lambdas.helpers.Type1;
import org.mockito.Mockito;

public class SpiedThrowingSupplier
        implements ThrowingSupplier<Type1>
{
    public static ThrowingSupplier<Type1> newSpy()
    {
        return Mockito.spy(new SpiedThrowingSupplier());
    }

    @Override
    public Type1 doGet()
            throws Throwable
    {
        return null;
    }
}
