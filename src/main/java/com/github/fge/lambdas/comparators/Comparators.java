package com.github.fge.lambdas.comparators;

public final class Comparators
{
    private Comparators()
    {
        throw new Error("nice try!");
    }

    public static <T> ThrowingComparator<T> rethrow(
        final ThrowingComparator<T> c)
    {
        return wrap(c);
    }

    public static <T> ThrowingComparator<T> wrap(final ThrowingComparator<T> c)
    {
        return c;
    }
}
