package com.github.fge.lambdas.functions;

import com.github.fge.lambdas.helpers.Type1;
import org.mockito.Mockito;

public class SpiedThrowingToLongFunction
    implements ThrowingToLongFunction<Type1>
{
    public static ThrowingToLongFunction<Type1> newSpy()
    {
        return Mockito.spy(new SpiedThrowingToLongFunction());
    }

    @Override
    public long doApplyAsLong(final Type1 value)
        throws Throwable
    {
        return 0L;
    }
}
