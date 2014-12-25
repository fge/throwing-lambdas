package com.github.fge.lambdas.functions.longfunctions;

import com.github.fge.lambdas.ThrownByLambdaException;
import com.github.fge.lambdas.helpers.Type1;
import org.testng.annotations.Test;

import java.util.stream.LongStream;

import static com.github.fge.lambdas.functions.Functions.rethrow;
import static com.github.fge.lambdas.helpers.CustomAssertions.shouldHaveThrown;
import static com.github.fge.lambdas.helpers.Throwables.CHECKED;
import static com.github.fge.lambdas.helpers.Throwables.ERROR;
import static com.github.fge.lambdas.helpers.Throwables.UNCHECKED;
import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("ErrorNotRethrown")
public final class RethrowLongFunctionsTest
{
    @Test
    public void wrappedLongFunctionThrowsAppropriateException()
    {
        ThrowingLongFunction<Type1> f;

        f = l -> { throw CHECKED; };

        try {
            LongStream.of(0L).mapToObj(rethrow(f)).count();
            shouldHaveThrown(ThrownByLambdaException.class);
        } catch (ThrownByLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }

        f = l -> { throw UNCHECKED; };

        try {
            LongStream.of(0L).mapToObj(rethrow(f)).count();
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(UNCHECKED);
        }

        f = l -> { throw ERROR; };

        try {
            LongStream.of(0L).mapToObj(rethrow(f)).count();
            shouldHaveThrown(Error.class);
        } catch (Error e) {
            assertThat(e).isSameAs(ERROR);
        }
    }

    @Test
    public void wrappedLongToIntFunctionThrowsAppropriateException()
    {
        ThrowingLongToIntFunction f;

        f = l -> { throw CHECKED; };

        try {
            LongStream.of(0L).mapToInt(rethrow(f)).count();
            shouldHaveThrown(ThrownByLambdaException.class);
        } catch (ThrownByLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }

        f = l -> { throw UNCHECKED; };

        try {
            LongStream.of(0L).mapToInt(rethrow(f)).count();
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(UNCHECKED);
        }

        f = l -> { throw ERROR; };

        try {
            LongStream.of(0L).mapToInt(rethrow(f)).count();
            shouldHaveThrown(Error.class);
        } catch (Error e) {
            assertThat(e).isSameAs(ERROR);
        }
    }

    @Test
    public void wrappedLongToDoubleFunctionThrowsAppropriateException()
    {
        ThrowingLongToDoubleFunction f;

        f = l -> { throw CHECKED; };

        try {
            LongStream.of(0L).mapToDouble(rethrow(f)).count();
            shouldHaveThrown(ThrownByLambdaException.class);
        } catch (ThrownByLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }

        f = l -> { throw UNCHECKED; };

        try {
            LongStream.of(0L).mapToDouble(rethrow(f)).count();
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(UNCHECKED);
        }

        f = l -> { throw ERROR; };

        try {
            LongStream.of(0L).mapToDouble(rethrow(f)).count();
            shouldHaveThrown(Error.class);
        } catch (Error e) {
            assertThat(e).isSameAs(ERROR);
        }
    }
}