package com.github.fge.lambdas.functions.operators;

import org.mockito.Mockito;

@SuppressWarnings("ProhibitedExceptionDeclared")
public class SpiedThrowingLongBinaryOperator
    implements ThrowingLongBinaryOperator
{
    public static ThrowingLongBinaryOperator newSpy()
    {
        return Mockito.spy(new SpiedThrowingLongBinaryOperator());
    }

    @Override
    public long doApplyAsLong(final long left, final long right)
        throws Throwable
    {
        return 0L;
    }
}
