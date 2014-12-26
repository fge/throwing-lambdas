package com.github.fge.lambdas.suppliers;

import com.github.fge.lambdas.ThrowablesFactory;
import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.Supplier;

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

    default Supplier<T> orReturn(T defaultValue)
    {
        return () -> {
            try {
                return doGet();
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable tooBad) {
                return defaultValue;
            }
        };
    }

    default <E extends RuntimeException> Supplier<T> orThrow(
        Class<E> exceptionClass)
    {
        return () -> {
            try {
                return doGet();
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable tooBad) {
                throw ThrowablesFactory.INSTANCE.get(exceptionClass, tooBad);
            }
        };
    }
}
