package com.github.fge.lambdas;

import com.github.fge.lambdas.helpers.Type1;
import com.github.fge.lambdas.suppliers.ThrowingIntSupplier;
import com.github.fge.lambdas.suppliers.ThrowingLongSupplier;
import com.github.fge.lambdas.suppliers.ThrowingSupplier;
import org.testng.annotations.Test;

import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static com.github.fge.lambdas.CustomAssertions.shouldHaveThrown;
import static com.github.fge.lambdas.Rethrow.rethrow;
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
}
