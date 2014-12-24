package com.github.fge.lambdas.helpers;

import org.assertj.core.api.Assertions;

public final class CustomAssertions
    extends Assertions
{
    public static void shouldHaveThrown(final Class<? extends Throwable> c)
    {
        failBecauseExceptionWasNotThrown(c);
    }
}
