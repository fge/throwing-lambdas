package com.github.fge.lambdas.predicates;

import java.util.function.DoublePredicate;
import java.util.function.IntPredicate;
import java.util.function.LongPredicate;
import java.util.function.Predicate;

public final class Predicates
{
    private Predicates()
    {
        throw new Error("nice try!");
    }

    public static <T> Predicate<T> rethrow(final ThrowingPredicate<T> p)
    {
        return p;
    }

    public static <T> ThrowingPredicate<T> wrap(
        final ThrowingPredicate<T> p)
    {
        return p;
    }

    public static IntPredicate rethrow(final ThrowingIntPredicate p)
    {
        return p;
    }

    public static ThrowingIntPredicate wrap(final ThrowingIntPredicate p)
    {
        return p;
    }

    public static LongPredicate rethrow(final ThrowingLongPredicate p)
    {
        return p;
    }

    public static ThrowingLongPredicate wrap(final ThrowingLongPredicate p)
    {
        return p;
    }

    public static DoublePredicate rethrow(final ThrowingDoublePredicate p)
    {
        return p;
    }

    public static ThrowingDoublePredicate wrap(final ThrowingDoublePredicate p)
    {
        return p;
    }
}