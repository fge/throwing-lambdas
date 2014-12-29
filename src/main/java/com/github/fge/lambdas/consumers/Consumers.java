package com.github.fge.lambdas.consumers;

import com.github.fge.lambdas.ThrowingFunctionalInterface;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Utility wrappers for throwing {@link Consumer}s and {@link BiConsumer}s
 *
 * <p>Note that all or {@code wrap()}, {@code tryWith()} or {@code rethrow()}
 * methods are in fact different names for the same thing. They are simply here
 * so that the intent is more obvious when you write. For instance:</p>
 *
 * <ul>
 *     // wrap...
 *     final ThrowingFoo f = wrap(someLambdaHere);
 *     // tryWith...
 *     final Foo f = tryWith(someLambdaHere).fallbackTo(someNonThrowingLambda);
 *     // rethrow...
 *     final Foo f = rethrow(someLambdaHere).as(MyCustomRuntimeException.class);
 * </ul>
 *
 * @see ThrowingFunctionalInterface
 */
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
