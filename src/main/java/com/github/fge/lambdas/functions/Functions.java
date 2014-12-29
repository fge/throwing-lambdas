package com.github.fge.lambdas.functions;

import com.github.fge.lambdas.ThrowingFunctionalInterface;
import com.github.fge.lambdas.functions.doublefunctions.ThrowingDoubleFunction;
import com.github.fge.lambdas.functions.doublefunctions
    .ThrowingDoubleToIntFunction;
import com.github.fge.lambdas.functions.doublefunctions
    .ThrowingDoubleToLongFunction;
import com.github.fge.lambdas.functions.intfunctions.ThrowingIntFunction;
import com.github.fge.lambdas.functions.intfunctions
    .ThrowingIntToDoubleFunction;
import com.github.fge.lambdas.functions.intfunctions.ThrowingIntToLongFunction;
import com.github.fge.lambdas.functions.longfunctions.ThrowingLongFunction;
import com.github.fge.lambdas.functions.longfunctions
    .ThrowingLongToDoubleFunction;
import com.github.fge.lambdas.functions.longfunctions.ThrowingLongToIntFunction;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Utility wrappers for throwing {@link Function}s and
 * {@link BiFunction}s
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
public final class Functions
{
    private Functions()
    {
        throw new Error("nice try!");
    }

    /*
     * Functions
     */
    public static <T, R> ThrowingFunction<T, R> wrap(
        final ThrowingFunction<T, R> f)
    {
        return f;
    }

    public static <T, R> ThrowingFunction<T, R> tryWith(
        final ThrowingFunction<T, R> f)
    {
        return wrap(f);
    }

    public static <T, R> ThrowingFunction<T, R> rethrow(
        final ThrowingFunction<T, R> f)
    {
        return wrap(f);
    }

    public static <T> ThrowingToIntFunction<T> wrap(
        final ThrowingToIntFunction<T> f)
    {
        return f;
    }

    public static <T> ThrowingToIntFunction<T> tryWith(
        final ThrowingToIntFunction<T> f)
    {
        return wrap(f);
    }

    public static <T> ThrowingToIntFunction<T> rethrow(
        final ThrowingToIntFunction<T> f)
    {
        return wrap(f);
    }

    public static <T> ThrowingToLongFunction<T> wrap(
        final ThrowingToLongFunction<T> f)
    {
        return f;
    }

    public static <T> ThrowingToLongFunction<T> tryWith(
        final ThrowingToLongFunction<T> f)
    {
        return wrap(f);
    }

    public static <T> ThrowingToLongFunction<T> rethrow(
        final ThrowingToLongFunction<T> f)
    {
        return wrap(f);
    }

    public static <T> ThrowingToDoubleFunction<T> wrap(
        final ThrowingToDoubleFunction<T> f)
    {
        return f;
    }

    public static <T> ThrowingToDoubleFunction<T> tryWith(
        final ThrowingToDoubleFunction<T> f)
    {
        return wrap(f);
    }

    public static <T> ThrowingToDoubleFunction<T> rethrow(
        final ThrowingToDoubleFunction<T> f)
    {
        return wrap(f);
    }

    /*
     * Int functions
     */

    public static <R> ThrowingIntFunction<R> wrap(
        final ThrowingIntFunction<R> f)
    {
        return f;
    }

    public static <R> ThrowingIntFunction<R> tryWith(
        final ThrowingIntFunction<R> f)
    {
        return wrap(f);
    }

    public static <R> ThrowingIntFunction<R> rethrow(
        final ThrowingIntFunction<R> f)
    {
        return wrap(f);
    }

    public static ThrowingIntToLongFunction wrap(
        final ThrowingIntToLongFunction f)
    {
        return f;
    }

    public static ThrowingIntToLongFunction tryWith(
        final ThrowingIntToLongFunction f)
    {
        return wrap(f);
    }

    public static ThrowingIntToLongFunction rethrow(
        final ThrowingIntToLongFunction f)
    {
        return wrap(f);
    }

    public static ThrowingIntToDoubleFunction wrap(
        final ThrowingIntToDoubleFunction f)
    {
        return f;
    }

    public static ThrowingIntToDoubleFunction tryWith(
        final ThrowingIntToDoubleFunction f)
    {
        return wrap(f);
    }

    public static ThrowingIntToDoubleFunction rethrow(
        final ThrowingIntToDoubleFunction f)
    {
        return wrap(f);
    }

    /*
     * Long functions
     */

    public static <R> ThrowingLongFunction<R> wrap(
        final ThrowingLongFunction<R> f)
    {
        return f;
    }

    public static <R> ThrowingLongFunction<R> tryWith(
        final ThrowingLongFunction<R> f)
    {
        return wrap(f);
    }

    public static <R> ThrowingLongFunction<R> rethrow(
        final ThrowingLongFunction<R> f)
    {
        return wrap(f);
    }

    public static ThrowingLongToIntFunction wrap(
        final ThrowingLongToIntFunction f)
    {
        return f;
    }

    public static ThrowingLongToIntFunction tryWith(
        final ThrowingLongToIntFunction f)
    {
        return wrap(f);
    }

    public static ThrowingLongToIntFunction rethrow(
        final ThrowingLongToIntFunction f)
    {
        return wrap(f);
    }

    public static ThrowingLongToDoubleFunction wrap(
        final ThrowingLongToDoubleFunction f)
    {
        return f;
    }

    public static ThrowingLongToDoubleFunction tryWith(
        final ThrowingLongToDoubleFunction f)
    {
        return wrap(f);
    }

    public static ThrowingLongToDoubleFunction rethrow(
        final ThrowingLongToDoubleFunction f)
    {
        return wrap(f);
    }

    /*
     * Double functions
     */

    public static <R> ThrowingDoubleFunction<R> wrap(
        final ThrowingDoubleFunction<R> f)
    {
        return f;
    }

    public static <R> ThrowingDoubleFunction<R> tryWith(
        final ThrowingDoubleFunction<R> f)
    {
        return wrap(f);
    }

    public static <R> ThrowingDoubleFunction<R> rethrow(
        final ThrowingDoubleFunction<R> f)
    {
        return wrap(f);
    }

    public static ThrowingDoubleToIntFunction wrap(
        final ThrowingDoubleToIntFunction f)
    {
        return f;
    }

    public static ThrowingDoubleToIntFunction tryWith(
        final ThrowingDoubleToIntFunction f)
    {
        return wrap(f);
    }

    public static ThrowingDoubleToIntFunction rethrow(
        final ThrowingDoubleToIntFunction f)
    {
        return wrap(f);
    }

    public static ThrowingDoubleToLongFunction wrap(
        final ThrowingDoubleToLongFunction f)
    {
        return f;
    }

    public static ThrowingDoubleToLongFunction tryWith(
        final ThrowingDoubleToLongFunction f)
    {
        return wrap(f);
    }

    public static ThrowingDoubleToLongFunction rethrow(
        final ThrowingDoubleToLongFunction f)
    {
        return wrap(f);
    }

    /*
     * Bifunctions
     */

    public static <T, U, R> ThrowingBiFunction<T, U, R> wrap(
        final ThrowingBiFunction<T, U, R> f)
    {
        return f;
    }

    public static <T, U, R> ThrowingBiFunction<T, U, R> tryWith(
        final ThrowingBiFunction<T, U, R> f)
    {
        return wrap(f);
    }

    public static <T, U, R> ThrowingBiFunction<T, U, R> rethrow(
        final ThrowingBiFunction<T, U, R> f)
    {
        return wrap(f);
    }
}
