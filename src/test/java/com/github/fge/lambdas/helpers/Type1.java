package com.github.fge.lambdas.helpers;

import org.mockito.Matchers;
import org.mockito.Mockito;

public interface Type1
{
    static Type1 mock()
    {
        return Mockito.mock(Type1.class);
    }

    static Type1 any()
    {
        return Matchers.any(Type1.class);
    }
}
