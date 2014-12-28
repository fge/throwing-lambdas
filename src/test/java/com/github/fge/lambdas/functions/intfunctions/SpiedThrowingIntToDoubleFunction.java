package com.github.fge.lambdas.functions.intfunctions;

import org.mockito.Mockito;

@SuppressWarnings("ProhibitedExceptionDeclared")
public class SpiedThrowingIntToDoubleFunction
    implements ThrowingIntToDoubleFunction
{
    public static ThrowingIntToDoubleFunction newSpy()
    {
        return Mockito.spy(new SpiedThrowingIntToDoubleFunction());
    };

    @Override
    public double doApplyAsDouble(final int value)
        throws Throwable
    {
        return 0.0;
    }
}
