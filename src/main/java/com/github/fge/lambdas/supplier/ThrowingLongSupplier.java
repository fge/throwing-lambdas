package com.github.fge.lambdas.supplier;

import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.LongSupplier;

/**
 * A throwing {@link LongSupplier}
 */
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
}
