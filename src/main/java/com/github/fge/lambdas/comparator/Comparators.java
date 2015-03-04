package com.github.fge.lambdas.comparator;

import com.github.fge.lambdas.Throwing;

/**
 * @deprecated use {@link Throwing} instead
 */
@Deprecated
public final class Comparators
{
    private Comparators()
    {
        throw new Error("nice try!");
    }

    /**
     * Get a new throwing comparator chain
     *
     * @param comparator the throwing comparator
     * @param <T> the type argument of the comparator
     * @return a chain
     *
     * @deprecated use {@link Throwing#comparator(ThrowingComparator)} instead
     */
    @Deprecated
    public static <T> ComparatorChain<T> wrap(
        final ThrowingComparator<T> comparator)
    {
        return new ComparatorChain<>(comparator);
    }

    /**
     * Get a new throwing comparator chain
     *
     * @param comparator the throwing comparator
     * @param <T> the type argument of the comparator
     * @return a chain
     *
     * @deprecated use {@link Throwing#comparator(ThrowingComparator)} instead
     */
    @Deprecated
    public static <T> ComparatorChain<T> tryWith(
        final ThrowingComparator<T> comparator)
    {
        return wrap(comparator);
    }

    /**
     * Get a new throwing comparator chain
     *
     * @param comparator the throwing comparator
     * @param <T> the type argument of the comparator
     * @return a chain
     *
     * @deprecated use {@link Throwing#comparator(ThrowingComparator)} instead
     */
    @Deprecated
    public static <T> ComparatorChain<T> rethrow(
        final ThrowingComparator<T> comparator)
    {
        return wrap(comparator);
    }
}
