package com.github.fge.lambdas.functions;

import com.github.fge.lambdas.helpers.Type1;
import com.github.fge.lambdas.helpers.Type2;
import com.github.fge.lambdas.helpers.Type3;
import org.mockito.Mockito;

@SuppressWarnings("ProhibitedExceptionDeclared")
public class SpiedThrowingBiFunction
    implements ThrowingBiFunction<Type1, Type2, Type3>
{
    public static ThrowingBiFunction<Type1, Type2, Type3> newSpy()
    {
        return Mockito.spy(new SpiedThrowingBiFunction());
    }

    @Override
    public Type3 doApply(final Type1 t, final Type2 u)
        throws Throwable
    {
        return null;
    }
}
