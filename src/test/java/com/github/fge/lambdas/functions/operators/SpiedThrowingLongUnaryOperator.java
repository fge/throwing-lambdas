package com.github.fge.lambdas.functions.operators;

import org.mockito.Mockito;

public class SpiedThrowingLongUnaryOperator
    implements ThrowingLongUnaryOperator
{
    public static ThrowingLongUnaryOperator newSpy()
    {
        return Mockito.spy(new SpiedThrowingLongUnaryOperator());
    }

    @Override
    public long doApplyAsLong(final long operand)
        throws Throwable
    {
        return 0L;
    }
}
