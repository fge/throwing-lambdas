package com.github.fge.lambdas.comparators;

import java.util.Comparator;

public final class Comparators
{
    private Comparators()
    {
        throw new Error("nice try!");
    }

    public static <T> Comparator<T> rethrow(final ThrowingComparator<T> c)
    {
        return c;
    }
}
