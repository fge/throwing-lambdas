package com.github.fge.lambdas.functions.operators;

import com.github.fge.lambdas.ThrowingFunctionalInterface;

import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

/**
 * Utility wrappers for throwing {@link UnaryOperator}s and
 * {@link BinaryOperator}s
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
public final class Operators
{
    private Operators()
    {
        throw new Error("nice try!");
    }

    public static <T> ThrowingUnaryOperator<T> wrap(
        final ThrowingUnaryOperator<T> o)
    {
        return o;
    }

    public static <T> ThrowingUnaryOperator<T> tryWith(
        final ThrowingUnaryOperator<T> o)
    {
        return wrap(o);
    }

    public static <T> ThrowingUnaryOperator<T> rethrow(
        final ThrowingUnaryOperator<T> o)
    {
        return wrap(o);
    }

    public static ThrowingIntUnaryOperator wrap(
        final ThrowingIntUnaryOperator o)
    {
        return o;
    }

    public static ThrowingIntUnaryOperator tryWith(
        final ThrowingIntUnaryOperator o)
    {
        return wrap(o);
    }

    public static ThrowingIntUnaryOperator rethrow(
        final ThrowingIntUnaryOperator o)
    {
        return wrap(o);
    }

    public static ThrowingLongUnaryOperator wrap(
        final ThrowingLongUnaryOperator o)
    {
        return o;
    }

    public static ThrowingLongUnaryOperator tryWith(
        final ThrowingLongUnaryOperator o)
    {
        return wrap(o);
    }

    public static ThrowingLongUnaryOperator rethrow(
        final ThrowingLongUnaryOperator o)
    {
        return wrap(o);
    }

    public static ThrowingDoubleUnaryOperator wrap(
        final ThrowingDoubleUnaryOperator o)
    {
        return o;
    }

    public static ThrowingDoubleUnaryOperator tryWith(
        final ThrowingDoubleUnaryOperator o)
    {
        return wrap(o);
    }

    public static ThrowingDoubleUnaryOperator rethrow(
        final ThrowingDoubleUnaryOperator o)
    {
        return wrap(o);
    }

    public static <T> ThrowingBinaryOperator<T> wrap(
        final ThrowingBinaryOperator<T> o)
    {
        return o;
    }

    public static <T> ThrowingBinaryOperator<T> tryWith(
        final ThrowingBinaryOperator<T> o)
    {
        return wrap(o);
    }

    public static <T> ThrowingBinaryOperator<T> rethrow(
        final ThrowingBinaryOperator<T> o)
    {
        return wrap(o);
    }

    public static ThrowingIntBinaryOperator wrap(
        final ThrowingIntBinaryOperator o)
    {
        return o;
    }

    public static ThrowingIntBinaryOperator tryWith(
        final ThrowingIntBinaryOperator o)
    {
        return wrap(o);
    }

    public static ThrowingIntBinaryOperator rethrow(
        final ThrowingIntBinaryOperator o)
    {
        return wrap(o);
    }

    public static ThrowingLongBinaryOperator wrap(
        final ThrowingLongBinaryOperator o)
    {
        return o;
    }

    public static ThrowingLongBinaryOperator tryWith(
        final ThrowingLongBinaryOperator o)
    {
        return wrap(o);
    }

    public static ThrowingLongBinaryOperator rethrow(
        final ThrowingLongBinaryOperator o)
    {
        return wrap(o);
    }

    public static ThrowingDoubleBinaryOperator wrap(
        final ThrowingDoubleBinaryOperator o)
    {
        return o;
    }

    public static ThrowingDoubleBinaryOperator tryWith(
        final ThrowingDoubleBinaryOperator o)
    {
        return wrap(o);
    }

    public static ThrowingDoubleBinaryOperator rethrow(
        final ThrowingDoubleBinaryOperator o)
    {
        return wrap(o);
    }
}
