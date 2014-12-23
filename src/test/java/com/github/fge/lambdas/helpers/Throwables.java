package com.github.fge.lambdas.helpers;

import java.io.IOException;

public final class Throwables
{
    private Throwables()
    {
        throw new Error("nice try!");
    }

    public static final Exception REGULAR_EXCEPTION = new IOException("meh");

    public static final RuntimeException RUNTIME_EXCEPTION
        = new RuntimeException("meh");

    public static final Error ERROR = new Error("meh");
}