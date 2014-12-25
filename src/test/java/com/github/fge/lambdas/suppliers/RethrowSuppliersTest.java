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

@SuppressWarnings("ErrorNotRethrown")
public final class RethrowSuppliersTest
{
    @Test
    public void wrappedSupplierThrowsAppropriateExceptions()
    {
        ThrowingSupplier<Type1> s;

        s = () -> { throw CHECKED; };

        try {
            Stream.generate(Suppliers.rethrow(s)).count();
            shouldHaveThrown(ThrownByLambdaException.class);
        } catch (ThrownByLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }

        s = () -> { throw UNCHECKED; };

        try {
            Stream.generate(Suppliers.rethrow(s)).count();
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(UNCHECKED);
        }

        s = () -> { throw ERROR; };

        try {
            Stream.generate(Suppliers.rethrow(s)).count();
            shouldHaveThrown(Error.class);
        } catch (Error e) {
            assertThat(e).isSameAs(ERROR);
        }
    }

    @Test
    public void wrappedIntSupplierThrowsAppropriateException()
    {
        ThrowingIntSupplier s;

        s = () -> { throw CHECKED; };

        try {
            IntStream.generate(Suppliers.rethrow(s)).count();
            shouldHaveThrown(ThrownByLambdaException.class);
        } catch (ThrownByLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }

        s = () -> { throw UNCHECKED; };

        try {
            IntStream.generate(rethrow(s)).count();
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(UNCHECKED);
        }

        s = () -> { throw ERROR; };

        try {
            IntStream.generate(rethrow(s)).count();
            shouldHaveThrown(Error.class);
        } catch (Error e) {
            assertThat(e).isSameAs(ERROR);
        }
    }

    @Test
    public void wrappedLongSupplierThrowsAppropriateException()
    {
        ThrowingLongSupplier s;

        s = () -> { throw CHECKED; };

        try {
            LongStream.generate(Suppliers.rethrow(s)).count();
            shouldHaveThrown(ThrownByLambdaException.class);
        } catch (ThrownByLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }

        s = () -> { throw UNCHECKED; };

        try {
            LongStream.generate(rethrow(s)).count();
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(UNCHECKED);
        }

        s = () -> { throw ERROR; };

        try {
            LongStream.generate(rethrow(s)).count();
            shouldHaveThrown(Error.class);
        } catch (Error e) {
            assertThat(e).isSameAs(ERROR);
        }
    }

    @Test
    public void wrappedDoubleSupplierThrowsAppropriateException()
    {
        ThrowingDoubleSupplier s;

        s = () -> { throw CHECKED; };

        try {
            DoubleStream.generate(Suppliers.rethrow(s)).count();
            shouldHaveThrown(ThrownByLambdaException.class);
        } catch (ThrownByLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }

        s = () -> { throw UNCHECKED; };

        try {
            DoubleStream.generate(rethrow(s)).count();
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(UNCHECKED);
        }

        s = () -> { throw ERROR; };

        try {
            DoubleStream.generate(rethrow(s)).count();
            shouldHaveThrown(Error.class);
        } catch (Error e) {
            assertThat(e).isSameAs(ERROR);
        }
    }
}
