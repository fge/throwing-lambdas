package com.github.fge.lambdas.functions.operators;

import java.util.function.BinaryOperator;
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

    public static <T> BinaryOperator<T> rethrow(
        final ThrowingBinaryOperator<T> o)
    {
        return o;
    }
}
