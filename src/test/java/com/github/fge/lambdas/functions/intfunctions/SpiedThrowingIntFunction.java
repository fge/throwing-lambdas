package com.github.fge.lambdas.functions.intfunctions;

import com.github.fge.lambdas.helpers.Type1;
import org.mockito.Mockito;

@SuppressWarnings("ProhibitedExceptionDeclared")
public class SpiedThrowingIntFunction
    implements ThrowingIntFunction<Type1>
{
    public static ThrowingIntFunction<Type1> newSpy()
    {
        return Mockito.spy(new SpiedThrowingIntFunction());
    }

    @Override
    public Type1 doApply(final int value)
        throws Throwable
    {
        return null;
    }
}
