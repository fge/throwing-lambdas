package com.github.fge.lambdas.functions.intfunctions;

import com.github.fge.lambdas.ThrownByLambdaException;
import com.github.fge.lambdas.helpers.Type1;
import org.testng.annotations.Test;

import java.util.stream.IntStream;

import static com.github.fge.lambdas.functions.Functions.rethrow;
import static com.github.fge.lambdas.helpers.CustomAssertions.shouldHaveThrown;
import static com.github.fge.lambdas.helpers.Throwables.CHECKED;
import static com.github.fge.lambdas.helpers.Throwables.ERROR;
import static com.github.fge.lambdas.helpers.Throwables.UNCHECKED;
import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("ErrorNotRethrown")
public final class RethrowIntFunctionsTest
{
    @Test
    public void wrappedIntFunctionThrowsAppropriateException()
    {
        ThrowingIntFunction<Type1> f;

        f = i -> { throw CHECKED; };

        try {
            IntStream.of(0).mapToObj(rethrow(f)).count();
            shouldHaveThrown(ThrownByLambdaException.class);
        } catch (ThrownByLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }

        f = i -> { throw UNCHECKED; };

        try {
            IntStream.of(0).mapToObj(rethrow(f)).count();
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(UNCHECKED);
        }

        f = i -> { throw  ERROR; };

        try {
            IntStream.of(0).mapToObj(rethrow(f)).count();
            shouldHaveThrown(Error.class);
        } catch (Error e) {
            assertThat(e).isSameAs(ERROR);
        }
    }

    @Test
    public void wrappedIntToLongFunctionThrowsAppropriateException()
    {
        ThrowingIntToLongFunction f;

        f = i -> { throw CHECKED; };

        try {
            IntStream.of(0).mapToLong(rethrow(f)).count();
            shouldHaveThrown(ThrownByLambdaException.class);
        } catch (ThrownByLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }

        f = i -> { throw UNCHECKED; };

        try {
            IntStream.of(0).mapToLong(rethrow(f)).count();
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(UNCHECKED);
        }

        f = i -> { throw ERROR; };

        try {
            IntStream.of(0).mapToLong(rethrow(f)).count();
            shouldHaveThrown(Error.class);
        } catch (Error e) {
            assertThat(e).isSameAs(ERROR);
        }
    }

    @Test
    public void wrappedIntToDoubleFunctionThrowsAppropriateException()
    {
        ThrowingIntToDoubleFunction f;

        f = i -> { throw CHECKED; };

        try {
            IntStream.of(0).mapToDouble(rethrow(f)).count();
            shouldHaveThrown(ThrownByLambdaException.class);
        } catch (ThrownByLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }

        f = i -> { throw UNCHECKED; };

        try {
            IntStream.of(0).mapToDouble(rethrow(f)).count();
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(UNCHECKED);
        }

        f = i -> { throw ERROR; };

        try {
            IntStream.of(0).mapToDouble(rethrow(f)).count();
            shouldHaveThrown(Error.class);
        } catch (Error e) {
            assertThat(e).isSameAs(ERROR);
        }
    }
}