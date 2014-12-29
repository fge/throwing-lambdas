package com.github.fge.lambdas.suppliers;

import com.github.fge.lambdas.ThrowablesFactory;
import com.github.fge.lambdas.ThrowingFunctionalInterface;
import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.LongSupplier;

/**
 * A throwing {@link LongSupplier}
 */
@FunctionalInterface
public interface ThrowingLongSupplier
    extends LongSupplier,
    ThrowingFunctionalInterface<ThrowingLongSupplier, LongSupplier>
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

    @Override
    default ThrowingLongSupplier orTryWith(ThrowingLongSupplier other)
    {
        return () -> {
            try {
                return doGetAsLong();
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return other.doGetAsLong();
            }
        };
    }

    @Override
    default LongSupplier fallbackTo(LongSupplier fallback)
    {
        return () -> {
            try {
                return doGetAsLong();
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return fallback.getAsLong();
            }
        };
    }

    default LongSupplier orReturn(long defaultValue)
    {
        return () -> {
            try {
                return doGetAsLong();
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return defaultValue;
            }
        };
    }

    @Override
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
