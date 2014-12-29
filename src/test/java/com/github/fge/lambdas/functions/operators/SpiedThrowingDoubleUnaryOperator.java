package com.github.fge.lambdas.functions.operators;

import org.mockito.Mockito;

public class SpiedThrowingDoubleUnaryOperator
    implements ThrowingDoubleUnaryOperator
{
    public static ThrowingDoubleUnaryOperator newSpy()
    {
        return Mockito.spy(new SpiedThrowingDoubleUnaryOperator());
    }

    @Override
    public double doApplyAsDouble(final double operand)
        throws Throwable
    {
        return 0.0;
    }
}
