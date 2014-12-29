package com.github.fge.lambdas.functions.operators;

import org.mockito.Mockito;

public class SpiedThrowingIntUnaryOperator
    implements ThrowingIntUnaryOperator
{
    public static ThrowingIntUnaryOperator newSpy()
    {
        return Mockito.spy(new SpiedThrowingIntUnaryOperator());
    }

    @Override
    public int doApplyAsInt(final int operand)
        throws Throwable
    {
        return 0;
    }
}
