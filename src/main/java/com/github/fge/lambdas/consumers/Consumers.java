package com.github.fge.lambdas.consumers;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;
import java.util.function.ObjDoubleConsumer;
import java.util.function.ObjIntConsumer;
import java.util.function.ObjLongConsumer;

public final class Consumers
{
    private Consumers()
    {
        throw new Error("nice try!");
    }

    public static <T> Consumer<T> rethrow(final ThrowingConsumer<T> c)
    {
        return c;
    }

    public static <T> ThrowingConsumer<T> wrap(final ThrowingConsumer<T> c)
    {
        return c;
    }

    public static IntConsumer rethrow(final ThrowingIntConsumer c)
    {
        return c;
    }

    public static ThrowingIntConsumer wrap(final ThrowingIntConsumer c)
    {
        return c;
    }

    public static LongConsumer rethrow(final ThrowingLongConsumer c)
    {
        return c;
    }

    public static ThrowingLongConsumer wrap(final ThrowingLongConsumer c)
    {
        return c;
    }

    public static DoubleConsumer rethrow(final ThrowingDoubleConsumer c)
    {
        return c;
    }

    public static ThrowingDoubleConsumer wrap(final ThrowingDoubleConsumer c)
    {
        return c;
    }

    public static <T, U> BiConsumer<T, U> rethrow(
        final ThrowingBiConsumer<T, U> c)
    {
        return c;
    }

    public static <T, U> ThrowingBiConsumer<T, U> wrap(
        final ThrowingBiConsumer<T, U> c)
    {
        return c;
    }

    public static <T> ObjIntConsumer<T> rethrow(
        final ThrowingObjIntConsumer<T> c)
    {
        return c;
    }

    public static <T> ThrowingObjIntConsumer<T> wrap(
        final ThrowingObjIntConsumer<T> c)
    {
        return c;
    }

    public static <T> ObjLongConsumer<T> rethrow(
        final ThrowingObjLongConsumer<T> c)
    {
        return c;
    }

    public static <T> ThrowingObjLongConsumer<T> wrap(
        final ThrowingObjLongConsumer<T> c)
    {
        return c;
    }

    public static <T> ObjDoubleConsumer<T> rethrow(
        final ThrowingObjDoubleConsumer<T> c)
    {
        return c;
    }

    public static <T> ThrowingObjDoubleConsumer<T> wrap(
        final ThrowingObjDoubleConsumer<T> c)
    {
        return c;
    }
}
