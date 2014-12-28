package com.github.fge.lambdas.functions.longfunctions;

import com.github.fge.lambdas.helpers.Type1;
import org.mockito.Mockito;

@SuppressWarnings("ProhibitedExceptionDeclared")
public class SpiedThrowingLongFunction
    implements ThrowingLongFunction<Type1>
{
    public static ThrowingLongFunction<Type1> newSpy()
    {
        return Mockito.spy(new SpiedThrowingLongFunction());
    }

    @Override
    public Type1 doApply(final long value)
        throws Throwable
    {
        return null;
    }
}
