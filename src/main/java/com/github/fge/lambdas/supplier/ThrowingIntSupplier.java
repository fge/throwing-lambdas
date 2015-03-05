package com.github.fge.lambdas.supplier;

import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.IntSupplier;

/**
 * A throwing {@link IntSupplier}
 */
@FunctionalInterface
public interface ThrowingIntSupplier
    extends IntSupplier
{
    int doGetAsInt()
        throws Throwable;

    @Override
    default int getAsInt()
    {
        try {
            return doGetAsInt();
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable tooBad) {
            throw new ThrownByLambdaException(tooBad);
        }
    }
}
