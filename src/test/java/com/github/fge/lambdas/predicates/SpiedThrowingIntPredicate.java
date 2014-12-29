package com.github.fge.lambdas.predicates;

import org.mockito.Mockito;

public class SpiedThrowingIntPredicate
    implements ThrowingIntPredicate
{
    public static ThrowingIntPredicate newSpy()
    {
        return Mockito.spy(new SpiedThrowingIntPredicate());
    }

    @Override
    public boolean doTest(final int value)
        throws Throwable
    {
        return false;
    }
}
