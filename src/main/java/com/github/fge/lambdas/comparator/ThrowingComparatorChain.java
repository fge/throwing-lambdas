package com.github.fge.lambdas.comparator;

import com.github.fge.lambdas.Chain;
import com.github.fge.lambdas.ThrowablesFactory;

import java.util.Comparator;

public final class ThrowingComparatorChain<T>
    extends Chain<Comparator<T>, ThrowingComparator<T>, ThrowingComparatorChain<T>>
    implements ThrowingComparator<T>
{
    public ThrowingComparatorChain(final ThrowingComparator<T> throwing)
    {
        super(throwing);
    }

    @Override
    public int doCompare(final T o1, final T o2)
        throws Throwable
    {
        return throwing.doCompare(o1, o2);
    }

    @Override
    public ThrowingComparatorChain<T> orTryWith(
        final ThrowingComparator<T> other)
    {
        final ThrowingComparator<T> comparator = (o1, o2) -> {
            try {
                return throwing.doCompare(o1, o2);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable throwable) {
                return other.doCompare(o1, o2);
            }
        };

        return new ThrowingComparatorChain<>(comparator);
    }

    @Override
    public <E extends RuntimeException> ThrowingComparator<T> orThrow(
        final Class<E> exclass)
    {
        return (o1, o2) -> {
            try {
                return throwing.doCompare(o1, o2);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable throwable) {
                throw  ThrowablesFactory.INSTANCE.get(exclass, throwable);
            }
        };
    }

    @Override
    public Comparator<T> fallbackTo(final Comparator<T> fallback)
    {
        return (o1, o2) -> {
            try {
                return throwing.doCompare(o1, o2);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return fallback.compare(o1, o2);
            }
        };
    }

    public Comparator<T> orReturn(final int value)
    {
        return (o1, o2) -> {
            try {
                return throwing.doCompare(o1, o2);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return value;
            }
        };
    }
}
