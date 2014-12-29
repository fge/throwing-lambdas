package com.github.fge.lambdas.functions.operators;

import org.mockito.Mockito;

@SuppressWarnings("ProhibitedExceptionDeclared")
public class SpiedThrowingDoubleBinaryOperator
    implements ThrowingDoubleBinaryOperator
{
    public static ThrowingDoubleBinaryOperator newSpy()
    {
        return Mockito.spy(new SpiedThrowingDoubleBinaryOperator());
    }

    @Override
    public double doApplyAsDouble(final double left, final double right)
        throws Throwable
    {
        return 0.0;
    }
}
