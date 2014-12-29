package com.github.fge.lambdas.functions.operators;

import com.github.fge.lambdas.helpers.Type1;
import org.mockito.Mockito;

@SuppressWarnings("ProhibitedExceptionDeclared")
public class SpiedThrowingUnaryOperator
    implements ThrowingUnaryOperator<Type1>
{
    public static ThrowingUnaryOperator<Type1> newSpy()
    {
        return Mockito.spy(new SpiedThrowingUnaryOperator());
    }

    @Override
    public Type1 doApply(final Type1 t)
        throws Throwable
    {
        return null;
    }
}
