package com.github.fge.lambdas.functions.operators;

import org.mockito.Mockito;

@SuppressWarnings("ProhibitedExceptionDeclared")
public class SpiedThrowingIntBinaryOperator
    implements ThrowingIntBinaryOperator
{
    public static ThrowingIntBinaryOperator newSpy()
    {
        return Mockito.spy(new SpiedThrowingIntBinaryOperator());
    }

    @Override
    public int doApplyAsInt(final int left, final int right)
        throws Throwable
    {
        return 0;
    }
}
