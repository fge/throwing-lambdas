
package com.github.fge.lambdas.suppliers;

import com.github.fge.lambdas.ThrowablesFactory;
import com.github.fge.lambdas.ThrowingFunctionalInterface;
import com.github.fge.lambdas.ThrownByLambdaException;

import java.util.function.DoubleSupplier;

@FunctionalInterface
public interface ThrowingDoubleSupplier
    extends DoubleSupplier,
    ThrowingFunctionalInterface<ThrowingDoubleSupplier, DoubleSupplier>
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

    @Override
    default ThrowingDoubleSupplier orTryWith(ThrowingDoubleSupplier other)
    {
        return () -> {
            try {
                return doGetAsDouble();
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return other.doGetAsDouble();
            }
        };
    }

    @Override
    default DoubleSupplier fallbackTo(DoubleSupplier byDefault)
    {
        return () -> {
            try {
                return doGetAsDouble();
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return byDefault.getAsDouble();
            }
        };
    }

    default DoubleSupplier orReturn(double defaultValue)
    {
        return () -> {
            try {
                return doGetAsDouble();
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return defaultValue;
            }
        };
    }

    default <E extends RuntimeException> DoubleSupplier orThrow(
        Class<E> exceptionClass)
    {
        return () -> {
            try {
                return doGetAsDouble();
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable tooBad) {
                throw ThrowablesFactory.INSTANCE.get(exceptionClass, tooBad);
            }
        };
    }
}
