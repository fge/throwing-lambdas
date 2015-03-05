package com.github.fge.lambdas.supplier;

import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.Supplier;

/**
 * A throwing {@link Supplier}
 *
 * @param <T> type parameter of the return value of this supplier
 */
@FunctionalInterface
public interface ThrowingSupplier<T>
    extends Supplier<T>
{
    T doGet()
        throws Throwable;

    @Override
    default T get()
    {
        try {
            return doGet();
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable tooBad) {
            throw new ThrownByLambdaException(tooBad);
        }
    }
}
