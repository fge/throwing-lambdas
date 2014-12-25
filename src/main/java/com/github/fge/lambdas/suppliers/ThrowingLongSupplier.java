package com.github.fge.lambdas.suppliers;

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
}
