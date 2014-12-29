package com.github.fge.lambdas.functions.intfunctions;

import org.mockito.Mockito;

@SuppressWarnings("ProhibitedExceptionDeclared")
public class SpiedThrowingIntToLongFunction
    implements ThrowingIntToLongFunction
{
    public static ThrowingIntToLongFunction newSpy()
    {
        return Mockito.spy(new SpiedThrowingIntToLongFunction());
    }

    @Override
    public long doApplyAsLong(final int value)
        throws Throwable
    {
        return 0L;
    }
}
