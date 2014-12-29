package com.github.fge.lambdas.predicates;

import org.mockito.Mockito;

public class SpiedThrowingDoublePredicate
    implements ThrowingDoublePredicate
{
    public static ThrowingDoublePredicate newSpy()
    {
        return Mockito.spy(new SpiedThrowingDoublePredicate());
    }

    @Override
    public boolean doTest(final double value)
        throws Throwable
    {
        return false;
    }
}
