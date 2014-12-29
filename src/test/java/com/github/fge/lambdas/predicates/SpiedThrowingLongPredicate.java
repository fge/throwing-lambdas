package com.github.fge.lambdas.predicates;

import org.mockito.Mockito;

public class SpiedThrowingLongPredicate
    implements ThrowingLongPredicate
{
    public static ThrowingLongPredicate newSpy()
    {
        return Mockito.spy(new SpiedThrowingLongPredicate());
    }

    @Override
    public boolean doTest(final long value)
        throws Throwable
    {
        return false;
    }
}
