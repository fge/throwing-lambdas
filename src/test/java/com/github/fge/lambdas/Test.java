package com.github.fge.lambdas;

import com.github.fge.lambdas.functions.ThrowingFunction;

import java.io.IOException;
import java.util.stream.Stream;

public final class Test
{
    public static void main(final String... args)
    {
        final ThrowingFunction<String, String> f
            = t -> { throw new IOException(); };

        Stream.of("").map(f.orThrow(IllegalArgumentException.class))
            .forEach(System.out::println);
    }
}
