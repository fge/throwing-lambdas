package com.github.fge.lambdas.consumers;

import com.github.fge.lambdas.helpers.Type1;
import org.mockito.Mockito;

@SuppressWarnings("ProhibitedExceptionDeclared")
public class SpiedThrowingObjLongConsumer
    implements ThrowingObjLongConsumer<Type1>
{
    public static ThrowingObjLongConsumer<Type1> newSpy()
    {
        return Mockito.spy(new SpiedThrowingObjLongConsumer());
    }

    @Override
    public void doAccept(final Type1 t, final long value)
        throws Throwable
    {
    }
}
