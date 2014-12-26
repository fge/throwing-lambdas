package com.github.fge.lambdas.suppliers;

import com.github.fge.lambdas.ThrowablesFactory;
import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.LongSupplier;

@FunctionalInterface
public interface ThrowingLongSupplier
    extends LongSupplier
{
    long doGetAsLong()
        throws Throwable;

    @Override
    default long getAsLong()
    {
        try {
            return doGetAsLong();
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable tooBad) {
            throw new ThrownByLambdaException(tooBad);
        }
    }

    default LongSupplier orReturn(long defaultValue)
    {
        return () -> {
            try {
                return doGetAsLong();
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable tooBad) {
                return defaultValue;
            }
        };
    }

    default <E extends RuntimeException> LongSupplier orThrow(
        Class<E> exceptionClass)
    {
        return () -> {
            try {
                return doGetAsLong();
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable tooBad) {
                throw ThrowablesFactory.INSTANCE.get(exceptionClass, tooBad);
            }
        };
    }
}
