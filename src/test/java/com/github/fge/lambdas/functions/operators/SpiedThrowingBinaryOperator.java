package com.github.fge.lambdas.functions.operators;

import com.github.fge.lambdas.helpers.Type1;
import org.mockito.Mockito;

@SuppressWarnings("ProhibitedExceptionDeclared")
public class SpiedThrowingBinaryOperator
    implements ThrowingBinaryOperator<Type1>
{
    public static ThrowingBinaryOperator<Type1> newSpy()
    {
        return Mockito.spy(new SpiedThrowingBinaryOperator());
    }

    @Override
    public Type1 doApply(final Type1 t, final Type1 u)
        throws Throwable
    {
        return null;
    }
}
