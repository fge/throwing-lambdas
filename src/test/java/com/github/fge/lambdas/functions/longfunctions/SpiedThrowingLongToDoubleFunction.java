package com.github.fge.lambdas.functions.longfunctions;

import org.mockito.Mockito;

@SuppressWarnings("ProhibitedExceptionDeclared")
public class SpiedThrowingLongToDoubleFunction
    implements ThrowingLongToDoubleFunction
{
    public static ThrowingLongToDoubleFunction newSpy()
    {
        return Mockito.spy(new SpiedThrowingLongToDoubleFunction());
    }

    @Override
    public double doApplyAsDouble(final long value)
        throws Throwable
    {
        return 0.0;
    }
}
