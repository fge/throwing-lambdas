package com.github.fge.lambdas.helpers;

import java.io.IOException;

public final class Throwables
{
    private Throwables()
    {
        throw new Error("nice try!");
    }

    public static final Exception CHECKED = new IOException();

    public static final RuntimeException UNCHECKED = new RuntimeException();

    public static final Error ERROR = new Error("meh");
}