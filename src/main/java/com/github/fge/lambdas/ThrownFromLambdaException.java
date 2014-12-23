package com.github.fge.lambdas;

public final class ThrownFromLambdaException
    extends RuntimeException
{
    public ThrownFromLambdaException(final Throwable cause)
    {
        super(cause);
    }
}
