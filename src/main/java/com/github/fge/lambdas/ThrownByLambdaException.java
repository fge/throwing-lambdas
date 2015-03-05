package com.github.fge.lambdas;

/**
 * Default exception thrown when a lambda throws a checked exception
 */
public final class ThrownByLambdaException
    extends RuntimeException
{
    public ThrownByLambdaException(final Throwable cause)
    {
        super(cause);
    }
}
