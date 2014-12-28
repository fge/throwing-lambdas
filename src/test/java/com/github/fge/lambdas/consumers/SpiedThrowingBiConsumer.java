package com.github.fge.lambdas.consumers;

import com.github.fge.lambdas.helpers.Type1;
import com.github.fge.lambdas.helpers.Type2;
import org.mockito.Mockito;

@SuppressWarnings("ProhibitedExceptionDeclared")
public class SpiedThrowingBiConsumer
    implements ThrowingBiConsumer<Type1, Type2>
{
    public static ThrowingBiConsumer<Type1, Type2> newSpy()
    {
        return Mockito.spy(new SpiedThrowingBiConsumer());
    }

    @Override
    public void doAccept(final Type1 t, final Type2 u)
        throws Throwable
    {
    }
}
