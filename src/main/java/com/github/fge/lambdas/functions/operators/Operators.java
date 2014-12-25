package com.github.fge.lambdas.functions.operators;

import java.util.function.BinaryOperator;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;
import java.util.function.IntBinaryOperator;
import java.util.function.IntUnaryOperator;
import java.util.function.LongBinaryOperator;
import java.util.function.LongUnaryOperator;
import java.util.function.UnaryOperator;

public final class Operators
{
    private Operators()
    {
        throw new Error("nice try!");
    }

    public static <T> UnaryOperator<T> rethrow(final ThrowingUnaryOperator<T> o)
    {
        return o;
    }

    public static IntUnaryOperator rethrow(final ThrowingIntUnaryOperator o)
    {
        return o;
    }

    public static LongUnaryOperator rethrow(final ThrowingLongUnaryOperator o)
    {
        return o;
    }

    public static DoubleUnaryOperator rethrow(
        final ThrowingDoubleUnaryOperator o)
    {
        return o;
    }

    public static <T> BinaryOperator<T> rethrow(
        final ThrowingBinaryOperator<T> o)
    {
        return o;
    }

    public static IntBinaryOperator rethrow(final ThrowingIntBinaryOperator o)
    {
        return o;
    }

    public static LongBinaryOperator rethrow(final ThrowingLongBinaryOperator o)
    {
        return o;
    }

    public static DoubleBinaryOperator rethrow(
        final ThrowingDoubleBinaryOperator o)
    {
        return o;
    }
}
