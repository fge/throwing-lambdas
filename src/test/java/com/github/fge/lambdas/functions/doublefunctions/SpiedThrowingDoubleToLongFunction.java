package com.github.fge.lambdas.functions.doublefunctions;

import org.mockito.Mockito;

@SuppressWarnings("ProhibitedExceptionDeclared")
public class SpiedThrowingDoubleToLongFunction
    implements ThrowingDoubleToLongFunction
{
    public static ThrowingDoubleToLongFunction newSpy()
    {
        return Mockito.spy(new SpiedThrowingDoubleToLongFunction());
    };

    @Override
    public long doApplyAsLong(final double value)
        throws Throwable
    {
        return 0;
    }
}
