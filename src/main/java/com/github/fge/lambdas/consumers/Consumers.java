package com.github.fge.lambdas.consumers;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;

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

    public static IntConsumer rethrow(final ThrowingIntConsumer c)
    {
        return c;
    }

    public static LongConsumer rethrow(final ThrowingLongConsumer c)
    {
        return c;
    }

    public static DoubleConsumer rethrow(final ThrowingDoubleConsumer c)
    {
        return c;
    }

    public static <T, U> BiConsumer<T, U> rethrow(
        final ThrowingBiConsumer<T, U> c)
    {
        return c;
    }
}
