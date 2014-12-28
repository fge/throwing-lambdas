package com.github.fge.lambdas.functions;

import com.github.fge.lambdas.helpers.Type1;
import org.mockito.Mockito;

public class SpiedThrowingToIntFunction
    implements ThrowingToIntFunction<Type1>
{
    public static ThrowingToIntFunction<Type1> newSpy()
    {
        return Mockito.spy(new SpiedThrowingToIntFunction());
    }

    @Override
    public int doApplyAsInt(final Type1 value)
        throws Throwable
    {
        return 0;
    }
}
