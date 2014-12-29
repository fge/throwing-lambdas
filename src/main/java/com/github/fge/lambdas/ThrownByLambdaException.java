package com.github.fge.lambdas;

/**
 * Default exception thrown when a lambda throws a checked exception
 *
 * @see com.github.fge.lambdas.ThrowingFunctionalInterface
 */
public final class ThrownByLambdaException
    extends RuntimeException
{
    public ThrownByLambdaException(final Throwable cause)
    {
        super(cause);
    }
}
