package com.github.fge.lambdas;

/**
 *
 * @param <T> type of the Throwing interface
 * @param <N> type of the non-Throwing interface
 */
public interface ThrowingFunctionalInterface<T extends N, N>
{
    T orTryWith(T other);

    N fallbackTo(N byDefault);

    <E extends RuntimeException> N orThrow(Class<E> exceptionClass);

    default <E extends RuntimeException> N as(Class<E> exceptionClass)
    {
        return orThrow(exceptionClass);
    }
}
