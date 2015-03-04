package com.github.fge.lambdas.comparator;

import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.Comparator;

/**
 * A throwing {@link Comparator}
 *
 * @param <T> type parameter of the argument
 */
@FunctionalInterface
public interface ThrowingComparator<T>
    extends Comparator<T>
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
        } catch (Throwable throwable) {
            throw new ThrownByLambdaException(throwable);
        }
    }
}
