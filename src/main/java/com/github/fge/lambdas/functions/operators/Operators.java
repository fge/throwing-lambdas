package com.github.fge.lambdas.functions.operators;

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
