package com.github.fge.lambdas.comparators;

import com.github.fge.lambdas.ThrowablesFactory;
import com.github.fge.lambdas.ThrowingFunctionalInterface;
import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.Comparator;

@FunctionalInterface
public interface ThrowingComparator<T>
    extends Comparator<T>,
    ThrowingFunctionalInterface<ThrowingComparator<T>, Comparator<T>>
{
    int doCompare(T o1, T o2)
        throws Throwable;

    @Override
    default int compare(T o1, T o2)
    {
        try {
            return doCompare(o1, o2);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable tooBad) {
            throw new ThrownByLambdaException(tooBad);
        }
    }

    @Override
    default ThrowingComparator<T> orTryWith(ThrowingComparator<T> other)
    {
        return (o1, o2) -> {
            try {
                return doCompare(o1, o2);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return other.compare(o1, o2);
            }
        };
    }

    @Override
    default Comparator<T> fallbackTo(Comparator<T> byDefault)
    {
        return (o1, o2) -> {
            try {
                return doCompare(o1, o2);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return byDefault.compare(o1, o2);
            }
        };
    }


    @Override
    default <E extends RuntimeException> Comparator<T> orThrow(
        Class<E> exceptionClass)
    {
        return (o1, o2) -> {
            try {
                return doCompare(o1, o2);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable tooBad) {
                throw ThrowablesFactory.INSTANCE.get(exceptionClass, tooBad);
            }
        };
    }

    default Comparator<T> orReturn(int defaultValue)
    {
        return (o1, o2) -> {
            try {
                return doCompare(o1, o2);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return defaultValue;
            }
        };
    }
}
