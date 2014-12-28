package com.github.fge.lambdas.consumers;

import com.github.fge.lambdas.helpers.Type1;
import org.mockito.Mockito;

@SuppressWarnings("ProhibitedExceptionDeclared")
public class SpiedThrowingConsumer
    implements ThrowingConsumer<Type1>
{
    public static ThrowingConsumer<Type1> newSpy()
    {
        return Mockito.spy(new SpiedThrowingConsumer());
    }

    @Override
    public void doAccept(final Type1 t)
        throws Throwable
    {
    }
}
