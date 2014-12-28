package com.github.fge.lambdas.functions;

import com.github.fge.lambdas.helpers.Type1;
import com.github.fge.lambdas.helpers.Type2;
import org.mockito.Mockito;

@SuppressWarnings("ProhibitedExceptionDeclared")
public class SpiedThrowingFunction
    implements ThrowingFunction<Type1, Type2>
{
    public static ThrowingFunction<Type1, Type2> newSpy()
    {
        return Mockito.spy(new SpiedThrowingFunction());
    }

    @Override
    public Type2 doApply(final Type1 t)
        throws Throwable
    {
        return null;
    }
}
