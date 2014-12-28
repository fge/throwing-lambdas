package com.github.fge.lambdas.functions.doublefunctions;

import org.mockito.Mockito;

@SuppressWarnings("ProhibitedExceptionDeclared")
public class SpiedThrowingDoubleToIntFunction
    implements ThrowingDoubleToIntFunction
{
    public static ThrowingDoubleToIntFunction newSpy()
    {
        return Mockito.spy(new SpiedThrowingDoubleToIntFunction());
    };

    @Override
    public int doApplyAsInt(final double value)
        throws Throwable
    {
        return 0;
    }
}
