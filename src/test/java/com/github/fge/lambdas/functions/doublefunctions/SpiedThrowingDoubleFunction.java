package com.github.fge.lambdas.functions.doublefunctions;

import com.github.fge.lambdas.helpers.Type1;
import org.mockito.Mockito;

@SuppressWarnings("ProhibitedExceptionDeclared")
public class SpiedThrowingDoubleFunction
    implements ThrowingDoubleFunction<Type1>
{
    public static ThrowingDoubleFunction<Type1> newSpy()
    {
        return Mockito.spy(new SpiedThrowingDoubleFunction());
    }

    @Override
    public Type1 doApply(final double value)
        throws Throwable
    {
        return null;
    }
}
