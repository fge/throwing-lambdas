package com.github.fge.lambdas.consumers;

import org.mockito.Mockito;

public class SpiedThrowingDoubleConsumer
    implements ThrowingDoubleConsumer
{
    public static ThrowingDoubleConsumer newSpy()
    {
        return Mockito.spy(new SpiedThrowingDoubleConsumer());
    }

    @Override
    public void doAccept(final double value)
        throws Throwable
    {
    }
}
