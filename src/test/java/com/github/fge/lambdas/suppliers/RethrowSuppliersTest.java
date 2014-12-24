package com.github.fge.lambdas.suppliers;

import com.github.fge.lambdas.ThrownByLambdaException;
import com.github.fge.lambdas.helpers.Type1;
import org.testng.annotations.Test;

import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static com.github.fge.lambdas.Rethrow.rethrow;
import static com.github.fge.lambdas.helpers.CustomAssertions.shouldHaveThrown;
import static com.github.fge.lambdas.helpers.Throwables.CHECKED;
import static com.github.fge.lambdas.helpers.Throwables.ERROR;
import static com.github.fge.lambdas.helpers.Throwables.UNCHECKED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({ "unchecked", "ProhibitedExceptionDeclared",
    "ErrorNotRethrown", "AutoBoxing" })
public final class RethrowSuppliersTest
{
    @Test
    public void wrappedSupplierThrowsAppropriateExceptions()
        throws Throwable
    {
        final ThrowingSupplier<Type1> s = mock(ThrowingSupplier.class);

        when(s.get()).thenThrow(CHECKED).thenThrow(UNCHECKED)
            .thenThrow(ERROR);

        try {
            Stream.generate(rethrow(s)).count();
            shouldHaveThrown(ThrownByLambdaException.class);
        } catch (ThrownByLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }

        try {
            Stream.generate(rethrow(s)).count();
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(UNCHECKED);
        }

        try {
            Stream.generate(rethrow(s)).count();
            shouldHaveThrown(Error.class);
        } catch (Error e) {
            assertThat(e).isSameAs(ERROR);
        }
    }

    @Test
    public void wrappedIntSupplierThrowsAppropriateException()
        throws Throwable
    {
        final ThrowingIntSupplier s = mock(ThrowingIntSupplier.class);

        when(s.getAsInt())
            .thenThrow(CHECKED).thenThrow(UNCHECKED).thenThrow(ERROR);

        try {
            IntStream.generate(rethrow(s)).count();
            shouldHaveThrown(ThrownByLambdaException.class);
        } catch (ThrownByLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }

        try {
            IntStream.generate(rethrow(s)).count();
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(UNCHECKED);
        }

        try {
            IntStream.generate(rethrow(s)).count();
            shouldHaveThrown(Error.class);
        } catch (Error e) {
            assertThat(e).isSameAs(ERROR);
        }
    }

    @Test
    public void wrappedLongSupplierThrowsAppropriateException()
        throws Throwable
    {
        final ThrowingLongSupplier s = mock(ThrowingLongSupplier.class);

        when(s.getAsLong())
            .thenThrow(CHECKED).thenThrow(UNCHECKED).thenThrow(ERROR);

        try {
            LongStream.generate(rethrow(s)).count();
            shouldHaveThrown(ThrownByLambdaException.class);
        } catch (ThrownByLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }

        try {
            LongStream.generate(rethrow(s)).count();
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(UNCHECKED);
        }

        try {
            LongStream.generate(rethrow(s)).count();
            shouldHaveThrown(Error.class);
        } catch (Error e) {
            assertThat(e).isSameAs(ERROR);
        }
    }

    @Test
    public void wrappedDoubleSupplierThrowsAppropriateException()
        throws Throwable
    {
        final ThrowingDoubleSupplier s = mock(ThrowingDoubleSupplier.class);

        when(s.getAsDouble())
            .thenThrow(CHECKED).thenThrow(UNCHECKED).thenThrow(ERROR);

        try {
            DoubleStream.generate(rethrow(s)).count();
            shouldHaveThrown(ThrownByLambdaException.class);
        } catch (ThrownByLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }

        try {
            DoubleStream.generate(rethrow(s)).count();
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(UNCHECKED);
        }

        try {
            DoubleStream.generate(rethrow(s)).count();
            shouldHaveThrown(Error.class);
        } catch (Error e) {
            assertThat(e).isSameAs(ERROR);
        }
    }
}
