package com.github.fge.lambdas.consumers;

import org.mockito.Mockito;

public class SpiedThrowingLongConsumer
    implements ThrowingLongConsumer
{
    public static ThrowingLongConsumer newSpy()
    {
        return Mockito.spy(new SpiedThrowingLongConsumer());
    }

    @Override
    public void doAccept(final long value)
        throws Throwable
    {
    }
}
