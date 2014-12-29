package com.github.fge.lambdas.consumers;

public final class Consumers
{
    private Consumers()
    {
        throw new Error("nice try!");
    }

    public static <T> ThrowingConsumer<T> wrap(final ThrowingConsumer<T> c)
    {
        return c;
    }

    public static <T> ThrowingConsumer<T> tryWith(final ThrowingConsumer<T> c)
    {
        return wrap(c);
    }

    public static <T> ThrowingConsumer<T> rethrow(final ThrowingConsumer<T> c)
    {
        return wrap(c);
    }

    public static ThrowingIntConsumer wrap(final ThrowingIntConsumer c)
    {
        return c;
    }

    public static ThrowingIntConsumer tryWith(final ThrowingIntConsumer c)
    {
        return wrap(c);
    }

    public static ThrowingIntConsumer rethrow(final ThrowingIntConsumer c)
    {
        return wrap(c);
    }

    public static ThrowingLongConsumer wrap(final ThrowingLongConsumer c)
    {
        return c;
    }

    public static ThrowingLongConsumer tryWith(final ThrowingLongConsumer c)
    {
        return wrap(c);
    }

    public static ThrowingLongConsumer rethrow(final ThrowingLongConsumer c)
    {
        return wrap(c);
    }

    public static ThrowingDoubleConsumer wrap(final ThrowingDoubleConsumer c)
    {
        return c;
    }

    public static ThrowingDoubleConsumer tryWith(final ThrowingDoubleConsumer c)
    {
        return wrap(c);
    }

    public static ThrowingDoubleConsumer rethrow(final ThrowingDoubleConsumer c)
    {
        return wrap(c);
    }

    public static <T, U> ThrowingBiConsumer<T, U> wrap(
        final ThrowingBiConsumer<T, U> c)
    {
        return c;
    }

    public static <T, U> ThrowingBiConsumer<T, U> tryWith(
        final ThrowingBiConsumer<T, U> c)
    {
        return wrap(c);
    }

    public static <T, U> ThrowingBiConsumer<T, U> rethrow(
        final ThrowingBiConsumer<T, U> c)
    {
        return wrap(c);
    }

    public static <T> ThrowingObjIntConsumer<T> wrap(
        final ThrowingObjIntConsumer<T> c)
    {
        return c;
    }

    public static <T> ThrowingObjIntConsumer<T> tryWith(
        final ThrowingObjIntConsumer<T> c)
    {
        return wrap(c);
    }

    public static <T> ThrowingObjIntConsumer<T> rethrow(
        final ThrowingObjIntConsumer<T> c)
    {
        return wrap(c);
    }

    public static <T> ThrowingObjLongConsumer<T> wrap(
        final ThrowingObjLongConsumer<T> c)
    {
        return c;
    }

    public static <T> ThrowingObjLongConsumer<T> tryWith(
        final ThrowingObjLongConsumer<T> c)
    {
        return wrap(c);
    }

    public static <T> ThrowingObjLongConsumer<T> rethrow(
        final ThrowingObjLongConsumer<T> c)
    {
        return wrap(c);
    }

    public static <T> ThrowingObjDoubleConsumer<T> wrap(
        final ThrowingObjDoubleConsumer<T> c)
    {
        return c;
    }

    public static <T> ThrowingObjDoubleConsumer<T> tryWith(
        final ThrowingObjDoubleConsumer<T> c)
    {
        return wrap(c);
    }

    public static <T> ThrowingObjDoubleConsumer<T> rethrow(
        final ThrowingObjDoubleConsumer<T> c)
    {
        return wrap(c);
    }
}
