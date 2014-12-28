package com.github.fge.lambdas.consumers;

import com.github.fge.lambdas.helpers.Type1;
import org.mockito.Mockito;

@SuppressWarnings("ProhibitedExceptionDeclared")
public class SpiedThrowingObjDoubleConsumer
    implements ThrowingObjDoubleConsumer<Type1>
{
    public static ThrowingObjDoubleConsumer<Type1> newSpy()
    {
        return Mockito.spy(new SpiedThrowingObjDoubleConsumer());
    }

    @Override
    public void doAccept(final Type1 t, final double value)
        throws Throwable
    {
    }
}
