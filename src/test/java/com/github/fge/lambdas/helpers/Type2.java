package com.github.fge.lambdas.helpers;

import org.mockito.Matchers;
import org.mockito.Mockito;

public interface Type2
{
    static Type2 mock()
    {
        return Mockito.mock(Type2.class);
    }

    static Type2 any()
    {
        return Matchers.any(Type2.class);
    }
}
