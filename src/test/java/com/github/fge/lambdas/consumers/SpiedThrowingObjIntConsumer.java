package com.github.fge.lambdas.consumers;

import com.github.fge.lambdas.helpers.Type1;
import org.mockito.Mockito;

@SuppressWarnings("ProhibitedExceptionDeclared")
public class SpiedThrowingObjIntConsumer
    implements ThrowingObjIntConsumer<Type1>
{
    public static ThrowingObjIntConsumer<Type1> newSpy()
    {
        return Mockito.spy(new SpiedThrowingObjIntConsumer());
    }

    @Override
    public void doAccept(final Type1 t, final int value)
        throws Throwable
    {
    }
}
