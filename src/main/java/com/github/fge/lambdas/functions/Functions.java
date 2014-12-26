package com.github.fge.lambdas.functions;

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
import java.util.function.DoubleFunction;
import java.util.function.DoubleToIntFunction;
import java.util.function.DoubleToLongFunction;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntToLongFunction;
import java.util.function.LongFunction;
import java.util.function.LongToDoubleFunction;
import java.util.function.LongToIntFunction;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

public final class Functions
{
    private Functions()
    {
        throw new Error("nice try!");
    }

    /*
     * Functions
     */

    public static <T, R> Function<T, R> rethrow(final ThrowingFunction<T, R> f)
    {
        return f;
    }

    public static <T, R> ThrowingFunction<T, R> wrap(
        final ThrowingFunction<T, R> f)
    {
        return f;
    }

    public static <T> ToIntFunction<T> rethrow(final ThrowingToIntFunction<T> f)
    {
        return f;
    }

    public static <T> ThrowingToIntFunction<T> wrap(
        final ThrowingToIntFunction<T> f)
    {
        return f;
    }

    public static <T> ToLongFunction<T> rethrow(
        final ThrowingToLongFunction<T> f)
    {
        return f;
    }

    public static <T> ThrowingToLongFunction<T> wrap(
        final ThrowingToLongFunction<T> f)
    {
        return f;
    }

    public static <T> ToDoubleFunction<T> rethrow(
        final ThrowingToDoubleFunction<T> f)
    {
        return f;
    }

    public static <T> ThrowingToDoubleFunction<T> wrap(
        final ThrowingToDoubleFunction<T> f)
    {
        return f;
    }

    /*
     * Int functions
     */

    public static <R> IntFunction<R> rethrow(final ThrowingIntFunction<R> f)
    {
        return f;
    }

    public static IntToLongFunction rethrow(final ThrowingIntToLongFunction f)
    {
        return f;
    }

    public static IntToDoubleFunction rethrow(
        final ThrowingIntToDoubleFunction f)
    {
        return f;
    }

    /*
     * Long functions
     */

    public static <R> LongFunction<R> rethrow(final ThrowingLongFunction<R> f)
    {
        return f;
    }

    public static LongToIntFunction rethrow(final ThrowingLongToIntFunction f)
    {
        return f;
    }

    public static LongToDoubleFunction rethrow(
        final ThrowingLongToDoubleFunction f)
    {
        return f;
    }

    /*
     * Double functions
     */

    public static <R> DoubleFunction<R> rethrow(
        final ThrowingDoubleFunction<R> f)
    {
        return f;
    }

    public static DoubleToIntFunction rethrow(
        final ThrowingDoubleToIntFunction f)
    {
        return f;
    }

    public static DoubleToLongFunction rethrow(
        final ThrowingDoubleToLongFunction f)
    {
        return f;
    }

    /*
     * Bifunctions
     */
    public static <T, U, R> BiFunction<T, U, R> rethrow(
        final ThrowingBiFunction<T, U, R> f)
    {
        return f;
    }
}
