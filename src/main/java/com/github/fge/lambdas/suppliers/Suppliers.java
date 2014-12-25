package com.github.fge.lambdas.suppliers;

import java.util.function.DoubleSupplier;
import java.util.function.IntSupplier;
import java.util.function.LongSupplier;
import java.util.function.Supplier;

public final class Suppliers
{
    private Suppliers()
    {
        throw new Error("nice try!");
    }

    public static <T> Supplier<T> rethrow(final ThrowingSupplier<T> s)
    {
        return s;
    }

    public static IntSupplier rethrow(final ThrowingIntSupplier s)
    {
        return s;
    }

    public static LongSupplier rethrow(final ThrowingLongSupplier s)
    {
        return s;
    }

    public static DoubleSupplier rethrow(final ThrowingDoubleSupplier s)
    {
        return s;
    }
}
