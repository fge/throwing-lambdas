package com.github.fge.lambdas.suppliers;

import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.DoubleSupplier;

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
        } catch (Throwable tooBad) {
            throw new ThrownByLambdaException(tooBad);
        }
    }
}
