package com.github.fge.lambdas.comparators;

import com.github.fge.lambdas.helpers.Type1;
import org.mockito.Mockito;

@SuppressWarnings({ "ProhibitedExceptionDeclared", "DesignForExtension" })
public class SpiedThrowingComparator
    implements ThrowingComparator<Type1>
{
    public static ThrowingComparator<Type1> newSpy()
    {
        return Mockito.spy(new SpiedThrowingComparator());
    }

    @Override
    public int doCompare(final Type1 o1, final Type1 o2)
        throws Throwable
    {
        return 0;
    }
}
