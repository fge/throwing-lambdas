package com.github.fge.lambdas.suppliers;

public final class Suppliers
{
    private Suppliers()
    {
        throw new Error("nice try!");
    }

    public static <T> ThrowingSupplier<T> wrap(final ThrowingSupplier<T> s)
    {
        return s;
    }

    public static <T> ThrowingSupplier<T> tryWith(final ThrowingSupplier<T> s)
    {
        return wrap(s);
    }

    public static <T> ThrowingSupplier<T> rethrow(final ThrowingSupplier<T> s)
    {
        return wrap(s);
    }

    public static ThrowingIntSupplier wrap(final ThrowingIntSupplier s)
    {
        return s;
    }

    public static ThrowingIntSupplier tryWith(final ThrowingIntSupplier s)
    {
        return wrap(s);
    }

    public static ThrowingIntSupplier rethrow(final ThrowingIntSupplier s)
    {
        return wrap(s);
    }

    public static ThrowingLongSupplier wrap(final ThrowingLongSupplier s)
    {
        return s;
    }

    public static ThrowingLongSupplier tryWith(final ThrowingLongSupplier s)
    {
        return wrap(s);
    }

    public static ThrowingLongSupplier rethrow(final ThrowingLongSupplier s)
    {
        return wrap(s);
    }

    public static ThrowingDoubleSupplier wrap(final ThrowingDoubleSupplier s)
    {
        return s;
    }

    public static ThrowingDoubleSupplier tryWith(final ThrowingDoubleSupplier s)
    {
        return wrap(s);
    }

    public static ThrowingDoubleSupplier rethrow(final ThrowingDoubleSupplier s)
    {
        return wrap(s);
    }
}
