package com.github.fge.lambdas.functions.longfunctions;

import org.mockito.Mockito;

@SuppressWarnings("ProhibitedExceptionDeclared")
public class SpiedThrowingLongToIntFunction
    implements ThrowingLongToIntFunction
{
    public static ThrowingLongToIntFunction newSpy()
    {
        return Mockito.spy(new SpiedThrowingLongToIntFunction());
    };

    @Override
    public int doApplyAsInt(final long value)
        throws Throwable
    {
        return 0;
    }
}
