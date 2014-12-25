package com.github.fge.lambdas.functions.doublefunctions;

import com.github.fge.lambdas.ThrownByLambdaException;
import com.github.fge.lambdas.helpers.Type1;
import org.testng.annotations.Test;

import java.util.stream.DoubleStream;

import static com.github.fge.lambdas.functions.Functions.rethrow;
import static com.github.fge.lambdas.helpers.CustomAssertions.shouldHaveThrown;
import static com.github.fge.lambdas.helpers.Throwables.CHECKED;
import static com.github.fge.lambdas.helpers.Throwables.ERROR;
import static com.github.fge.lambdas.helpers.Throwables.UNCHECKED;
import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("ErrorNotRethrown")
public final class RethrowDoubleFunctionsTest
{
    @Test
    public void wrappedDoubleFunctionThrowsAppropriateException()
    {
        ThrowingDoubleFunction<Type1> f;

        f = d -> { throw CHECKED; };

        try {
            DoubleStream.of(0.0).mapToObj(rethrow(f)).count();
            shouldHaveThrown(ThrownByLambdaException.class);
        } catch (ThrownByLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }

        f = d -> { throw UNCHECKED; };

        try {
            DoubleStream.of(0.0).mapToObj(rethrow(f)).count();
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(UNCHECKED);
        }

        f = d -> { throw ERROR; };

        try {
            DoubleStream.of(0.0).mapToObj(rethrow(f)).count();
            shouldHaveThrown(Error.class);
        } catch (Error e) {
            assertThat(e).isSameAs(ERROR);
        }
    }

    @Test
    public void wrappedDoubleToIntFunctionThrowsAppropriateException()
    {
        ThrowingDoubleToIntFunction f;

        f = d -> { throw CHECKED; };

        try {
            DoubleStream.of(0.0).mapToInt(rethrow(f)).count();
            shouldHaveThrown(ThrownByLambdaException.class);
        } catch (ThrownByLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }

        f = d -> { throw UNCHECKED; };

        try {
            DoubleStream.of(0.0).mapToInt(rethrow(f)).count();
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(UNCHECKED);
        }

        f = d -> { throw ERROR; };

        try {
            DoubleStream.of(0.0).mapToInt(rethrow(f)).count();
            shouldHaveThrown(Error.class);
        } catch (Error e) {
            assertThat(e).isSameAs(ERROR);
        }
    }

    @Test
    public void wrappedDoubleToLongFunctionThrowsAppropriateException()
    {
        ThrowingDoubleToLongFunction f;

        f = d -> { throw CHECKED; };

        try {
            DoubleStream.of(0.0).mapToLong(rethrow(f)).count();
            shouldHaveThrown(ThrownByLambdaException.class);
        } catch (ThrownByLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }

        f = d -> { throw UNCHECKED; };

        try {
            DoubleStream.of(0.0).mapToLong(rethrow(f)).count();
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(UNCHECKED);
        }

        f = d -> { throw ERROR; };

        try {
            DoubleStream.of(0.0).mapToLong(rethrow(f)).count();
            shouldHaveThrown(Error.class);
        } catch (Error e) {
            assertThat(e).isSameAs(ERROR);
        }
    }
}