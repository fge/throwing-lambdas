package com.github.fge.lambdas.suppliers;

import com.github.fge.lambdas.ThrowablesFactory;
import com.github.fge.lambdas.ThrowingFunctionalInterface;
import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.Supplier;

@FunctionalInterface
public interface ThrowingSupplier<T>
    extends Supplier<T>,
    ThrowingFunctionalInterface<ThrowingSupplier<T>, Supplier<T>>
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

    @Override
    default ThrowingSupplier<T> orTryWith(ThrowingSupplier<T> other)
    {
        return () -> {
            try {
                return doGet();
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return other.doGet();
            }
        };
    }

    @Override
    default Supplier<T> or(Supplier<T> byDefault)
    {
        return () -> {
            try {
                return doGet();
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return byDefault.get();
            }
        };
    }

    default Supplier<T> orReturn(T defaultValue)
    {
        return () -> {
            try {
                return doGet();
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
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
