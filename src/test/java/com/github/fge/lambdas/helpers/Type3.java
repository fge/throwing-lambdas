package com.github.fge.lambdas.helpers;

import org.mockito.Matchers;
import org.mockito.Mockito;

public interface Type3
{
    static Type3 mock()
    {
        return Mockito.mock(Type3.class);
    }

    static Type3 any()
    {
        return Matchers.any(Type3.class);
    }
}
