package com.github.fge.lambdas.functions;

import com.github.fge.lambdas.helpers.Type1;
import org.mockito.Mockito;

public class SpiedThrowingToDoubleFunction
    implements ThrowingToDoubleFunction<Type1>
{
    public static ThrowingToDoubleFunction<Type1> newSpy()
    {
        return Mockito.spy(new SpiedThrowingToDoubleFunction());
    }

    @Override
    public double doApplyAsDouble(final Type1 value)
        throws Throwable
    {
        return 0.0;
    }
}
