package com.github.fge.lambdas.consumers;

import org.mockito.Mockito;

public class SpiedThrowingIntConsumer
    implements ThrowingIntConsumer
{
    public static ThrowingIntConsumer newSpy()
    {
        return Mockito.spy(new SpiedThrowingIntConsumer());
    }

    @Override
    public void doAccept(final int value)
        throws Throwable
    {
    }
}
