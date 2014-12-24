package com.github.fge.lambdas;

public final class ThrownByLambdaException
    extends RuntimeException
{
    public ThrownByLambdaException(final Throwable cause)
    {
        super(cause);
    }
}
