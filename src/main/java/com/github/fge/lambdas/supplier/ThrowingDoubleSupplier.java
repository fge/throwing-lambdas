
package com.github.fge.lambdas.supplier;

import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.DoubleSupplier;

/**
 * A throwing {@link DoubleSupplier}
 */
@FunctionalInterface
public interface ThrowingDoubleSupplier
    extends DoubleSupplier
{
    double doGetAsDouble()
        throws Throwable;

    @Override
    default double getAsDouble()
    {
        try {
            return doGetAsDouble();
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable throwable) {
            throw new ThrownByLambdaException(throwable);
        }
    }
}
