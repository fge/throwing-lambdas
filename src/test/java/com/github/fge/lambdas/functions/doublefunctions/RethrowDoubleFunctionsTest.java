package com.github.fge.lambdas.functions.doublefunctions;

import com.github.fge.lambdas.ThrownByLambdaException;
import com.github.fge.lambdas.helpers.Type1;
import org.testng.annotations.Test;

import java.util.stream.DoubleStream;

import static com.github.fge.lambdas.Rethrow.rethrow;
import static com.github.fge.lambdas.helpers.CustomAssertions.shouldHaveThrown;
import static com.github.fge.lambdas.helpers.Throwables.CHECKED;
import static com.github.fge.lambdas.helpers.Throwables.ERROR;
import static com.github.fge.lambdas.helpers.Throwables.UNCHECKED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({
    "ProhibitedExceptionDeclared",
    "unchecked",
    "AutoBoxing",
    "ErrorNotRethrown"
})
public final class RethrowDoubleFunctionsTest
{
    @Test
    public void wrappedDoubleFunctionThrowsAppropriateException()
        throws Throwable
    {
        final ThrowingDoubleFunction<Type1> f
            = mock(ThrowingDoubleFunction.class);

        when(f.apply(anyDouble()))
            .thenThrow(CHECKED).thenThrow(UNCHECKED).thenThrow(ERROR);

        try {
            DoubleStream.of(0.0).mapToObj(rethrow(f)).count();
            shouldHaveThrown(ThrownByLambdaException.class);
        } catch (ThrownByLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }

        try {
            DoubleStream.of(0.0).mapToObj(rethrow(f)).count();
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(UNCHECKED);
        }

        try {
            DoubleStream.of(0.0).mapToObj(rethrow(f)).count();
            shouldHaveThrown(Error.class);
        } catch (Error e) {
            assertThat(e).isSameAs(ERROR);
        }
    }

    @Test
    public void wrappedDoubleToIntFunctionThrowsAppropriateException()
        throws Throwable
    {
        final ThrowingDoubleToIntFunction f
            = mock(ThrowingDoubleToIntFunction.class);

        when(f.apply(anyDouble()))
            .thenThrow(CHECKED).thenThrow(UNCHECKED).thenThrow(ERROR);

        try {
            DoubleStream.of(0.0).mapToInt(rethrow(f)).count();
            shouldHaveThrown(ThrownByLambdaException.class);
        } catch (ThrownByLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }

        try {
            DoubleStream.of(0.0).mapToInt(rethrow(f)).count();
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(UNCHECKED);
        }

        try {
            DoubleStream.of(0.0).mapToInt(rethrow(f)).count();
            shouldHaveThrown(Error.class);
        } catch (Error e) {
            assertThat(e).isSameAs(ERROR);
        }
    }

    @Test
    public void wrappedDoubleToLongFunctionThrowsAppropriateException()
        throws Throwable
    {
        final ThrowingDoubleToLongFunction f
            = mock(ThrowingDoubleToLongFunction.class);

        when(f.apply(anyDouble()))
            .thenThrow(CHECKED).thenThrow(UNCHECKED).thenThrow(ERROR);

        try {
            DoubleStream.of(0.0).mapToLong(rethrow(f)).count();
            shouldHaveThrown(ThrownByLambdaException.class);
        } catch (ThrownByLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }

        try {
            DoubleStream.of(0.0).mapToLong(rethrow(f)).count();
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(UNCHECKED);
        }

        try {
            DoubleStream.of(0.0).mapToLong(rethrow(f)).count();
            shouldHaveThrown(Error.class);
        } catch (Error e) {
            assertThat(e).isSameAs(ERROR);
        }
    }
}