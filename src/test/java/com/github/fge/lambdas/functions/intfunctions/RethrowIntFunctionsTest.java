package com.github.fge.lambdas.functions.intfunctions;

import com.github.fge.lambdas.ThrownByLambdaException;
import com.github.fge.lambdas.helpers.Type1;
import org.testng.annotations.Test;

import java.util.stream.IntStream;

import static com.github.fge.lambdas.Rethrow.rethrow;
import static com.github.fge.lambdas.helpers.CustomAssertions.shouldHaveThrown;
import static com.github.fge.lambdas.helpers.Throwables.CHECKED;
import static com.github.fge.lambdas.helpers.Throwables.ERROR;
import static com.github.fge.lambdas.helpers.Throwables.UNCHECKED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({
    "ProhibitedExceptionDeclared",
    "unchecked",
    "AutoBoxing",
    "ErrorNotRethrown"
})
public final class RethrowIntFunctionsTest
{
    @Test
    public void wrappedIntFunctionThrowsAppropriateException()
        throws Throwable
    {
        final ThrowingIntFunction<Type1> f = mock(ThrowingIntFunction.class);

        when(f.apply(anyInt()))
            .thenThrow(CHECKED).thenThrow(UNCHECKED).thenThrow(ERROR);

        try {
            IntStream.of(0).mapToObj(rethrow(f)).count();
            shouldHaveThrown(ThrownByLambdaException.class);
        } catch (ThrownByLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }

        try {
            IntStream.of(0).mapToObj(rethrow(f)).count();
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(UNCHECKED);
        }

        try {
            IntStream.of(0).mapToObj(rethrow(f)).count();
            shouldHaveThrown(Error.class);
        } catch (Error e) {
            assertThat(e).isSameAs(ERROR);
        }
    }

    @Test
    public void wrappedIntToLongFunctionThrowsAppropriateException()
        throws Throwable
    {
        final ThrowingIntToLongFunction f
            = mock(ThrowingIntToLongFunction.class);

        when(f.apply(anyInt()))
            .thenThrow(CHECKED).thenThrow(UNCHECKED).thenThrow(ERROR);

        try {
            IntStream.of(0).mapToLong(rethrow(f)).count();
            shouldHaveThrown(ThrownByLambdaException.class);
        } catch (ThrownByLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }

        try {
            IntStream.of(0).mapToLong(rethrow(f)).count();
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(UNCHECKED);
        }

        try {
            IntStream.of(0).mapToLong(rethrow(f)).count();
            shouldHaveThrown(Error.class);
        } catch (Error e) {
            assertThat(e).isSameAs(ERROR);
        }
    }

    @Test
    public void wrappedIntToDoubleFunctionThrowsAppropriateException()
        throws Throwable
    {
        final ThrowingIntToDoubleFunction f
            = mock(ThrowingIntToDoubleFunction.class);

        when(f.apply(anyInt()))
            .thenThrow(CHECKED).thenThrow(UNCHECKED).thenThrow(ERROR);

        try {
            IntStream.of(0).mapToDouble(rethrow(f)).count();
            shouldHaveThrown(ThrownByLambdaException.class);
        } catch (ThrownByLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }

        try {
            IntStream.of(0).mapToDouble(rethrow(f)).count();
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(UNCHECKED);
        }

        try {
            IntStream.of(0).mapToDouble(rethrow(f)).count();
            shouldHaveThrown(Error.class);
        } catch (Error e) {
            assertThat(e).isSameAs(ERROR);
        }
    }
}