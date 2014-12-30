package com.github.fge.lambdas.comparators;

import com.github.fge.lambdas.ThrowingFunctionalInterface;

import java.util.Comparator;

/**
 * Utility wrappers for throwing {@link Comparator}s
 *
 * <p>Note that all or {@code wrap()}, {@code tryWith()} or {@code rethrow()}
 * methods are in fact different names for the same thing. They are simply here
 * so that the intent is more obvious when you write. For instance:</p>
 *
 * <pre>
 *     // wrap...
 *     final ThrowingFoo f = wrap(someLambdaHere);
 *     // tryWith...
 *     final Foo f = tryWith(someLambdaHere).fallbackTo(someNonThrowingLambda);
 *     // rethrow...
 *     final Foo f = rethrow(someLambdaHere).as(MyCustomRuntimeException.class);
 * </pre>
 *
 * @see ThrowingFunctionalInterface
 */
public final class Comparators
{
    private Comparators()
    {
        throw new Error("nice try!");
    }

    public static <T> ThrowingComparator<T> wrap(final ThrowingComparator<T> c)
    {
        return c;
    }

    public static <T> ThrowingComparator<T> tryWith(
        final ThrowingComparator<T> c)
    {
        return wrap(c);
    }

    public static <T> ThrowingComparator<T> rethrow(
        final ThrowingComparator<T> c)
    {
        return wrap(c);
    }
}
