package com.github.fge.lambdas;

import com.github.fge.lambdas.consumers.ThrowingIntConsumer;

/**
 * Base interface for all {@code Throwing} versions of functional interfaces
 *
 * <p>All interfaces of this package extend this one. For instance,{@link
 * ThrowingIntConsumer} extends {@code
 * ThrowingFunctionalInterface&lt;ThrowingIntConsumer, IntConsumer&gt;}.</p>
 *
 * @param <T> type of the Throwing interface
 * @param <N> type of the non-Throwing interface
 */
public interface ThrowingFunctionalInterface<T extends N, N>
{
    /**
     * Chain this throwing lambda with another throwing lambda if this one fails
     *
     * <p>This means that if this lambda fails with an unchecked exception, the
     * second one will be tried instead.</p>
     *
     * <p>Since this method itself returns a throwing lambda, it means you can
     * chain more than two lambdas this way: {@code
     * l1.orTryWith(l2).orTryWith(l3)} etc.</p>
     *
     * @param other the other throwing lambda
     * @return a new throwing lambda
     */
    T orTryWith(T other);

    /**
     * Chain this throwing lambda with a non throwing lambda fallback
     *
     * <p>If this throwing lambda fails with a checked exception, the fallback
     * (which cannot throw checked exceptions) is invoked instead.</p>
     *
     * <p>As this method returns a non throwing version, you cannot chain any
     * behavior afterwards.</p>
     *
     * @param fallback the fallback
     * @return a non throwing lambda
     */
    N fallbackTo(N fallback);

    /**
     * Change the unchecked exception thrown by this throwing lambda
     *
     * <p>This allows to change the exception which is thrown if this throwing
     * lambda throws a checked exception; as for the default behavior, the
     * exception thrown by the lambda is set as the cause of an instance of this
     * exception class.</p>
     *
     * <p>See {@link ThrowablesFactory} for more details.</p>
     *
     * <p>As this method returns a non throwing version, you cannot chain any
     * behavior afterwards.</p>
     *
     * @param exceptionClass the exception class
     * @param <E> the type of this exception class
     * @return a non throwing lambda
     */
    <E extends RuntimeException> N orThrow(Class<E> exceptionClass);

    /**
     * Alias to {@link #orThrow(Class)}
     *
     * <p>This alias is here purely for convenience. It allows you to write:</p>
     *
     * <pre>
     *     rethrow(something).as(SomeException.class)
     * </pre>
     *
     * @param exceptionClass the exception class
     * @param <E> the type of this exception class
     * @return a non throwing lambda
     *
     * @see #orThrow(Class)
     */
    default <E extends RuntimeException> N as(Class<E> exceptionClass)
    {
        return orThrow(exceptionClass);
    }
}
