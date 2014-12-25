package com.github.fge.lambdas.suppliers;

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
}
