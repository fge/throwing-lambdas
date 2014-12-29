package com.github.fge.lambdas.predicates;

public final class Predicates
{
    private Predicates()
    {
        throw new Error("nice try!");
    }

    public static <T> ThrowingPredicate<T> wrap(
        final ThrowingPredicate<T> p)
    {
        return p;
    }

    public static <T> ThrowingPredicate<T> tryWith(final ThrowingPredicate<T> p)
    {
        return wrap(p);
    }

    public static <T> ThrowingPredicate<T> rethrow(final ThrowingPredicate<T> p)
    {
        return wrap(p);
    }

    public static ThrowingIntPredicate wrap(final ThrowingIntPredicate p)
    {
        return p;
    }

    public static ThrowingIntPredicate tryWith(final ThrowingIntPredicate p)
    {
        return wrap(p);
    }

    public static ThrowingIntPredicate rethrow(final ThrowingIntPredicate p)
    {
        return wrap(p);
    }

    public static ThrowingLongPredicate wrap(final ThrowingLongPredicate p)
    {
        return p;
    }

    public static ThrowingLongPredicate tryWith(final ThrowingLongPredicate p)
    {
        return wrap(p);
    }

    public static ThrowingLongPredicate rethrow(final ThrowingLongPredicate p)
    {
        return wrap(p);
    }

    public static ThrowingDoublePredicate wrap(final ThrowingDoublePredicate p)
    {
        return p;
    }

    public static ThrowingDoublePredicate tryWith(
        final ThrowingDoublePredicate p)
    {
        return wrap(p);
    }

    public static ThrowingDoublePredicate rethrow(
        final ThrowingDoublePredicate p)
    {
        return wrap(p);
    }
}
