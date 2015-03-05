package com.github.fge.lambdas.comparators;

import com.github.fge.lambdas.Chainer;

import java.util.Comparator;

public class ComparatorChainer<T>
    extends Chainer<Comparator<T>, ThrowingComparator<T>, ComparatorChainer<T>>
    implements ThrowingComparator<T>
{
    public ComparatorChainer(final ThrowingComparator<T> throwing)
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
    public ComparatorChainer<T> orTryWith(final ThrowingComparator<T> other)
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

        return new ComparatorChainer<>(comparator);
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
                throw rethrow(exclass, throwable);
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

    @Override
    public Comparator<T> sneakyThrow()
    {
        return (o1, o2) -> {
            try {
                return throwing.doCompare(o1, o2);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable throwable) {
                throw doSneakyThrow(throwable);
            }
        };
    }

    public Comparator<T> orReturn(final int retval)
    {
        return (o1, o2) -> {
            try {
                return throwing.doCompare(o1, o2);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return retval;
            }
        };
    }
}
