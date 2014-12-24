package com.github.fge.lambdas.consumers;

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
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

@SuppressWarnings({
    "unchecked",
    "ProhibitedExceptionDeclared",
    "ErrorNotRethrown"
})
public final class RethrowConsumersTest
{
    @Test
    public void wrappedConsumerThrowsAppropriateException()
        throws Throwable
    {
        final ThrowingConsumer<Type1> c = mock(ThrowingConsumer.class);

        doThrow(CHECKED).doThrow(UNCHECKED).doThrow(ERROR)
            .when(c).accept(Type1.any());

        try {
            Stream.of(Type1.mock()).forEach(rethrow(c));
            shouldHaveThrown(ThrownByLambdaException.class);
        } catch (ThrownByLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }

        try {
            Stream.of(Type1.mock()).forEach(rethrow(c));
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(UNCHECKED);
        }

        try {
            Stream.of(Type1.mock()).forEach(rethrow(c));
            shouldHaveThrown(Error.class);
        } catch (Error e) {
            assertThat(e).isSameAs(ERROR);
        }
    }

    @Test
    public void wrappedIntConsumerThrowsAppropriateException()
        throws Throwable
    {
        final ThrowingIntConsumer c = mock(ThrowingIntConsumer.class);

        doThrow(CHECKED).doThrow(UNCHECKED).doThrow(ERROR)
            .when(c).accept(anyInt());

        try {
            IntStream.of(0).forEach(rethrow(c));
            shouldHaveThrown(ThrownByLambdaException.class);
        } catch (ThrownByLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }

        try {
            IntStream.of(0).forEach(rethrow(c));
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(UNCHECKED);
        }

        try {
            IntStream.of(0).forEach(rethrow(c));
            shouldHaveThrown(Error.class);
        } catch (Error e) {
            assertThat(e).isSameAs(ERROR);
        }
    }

    @Test
    public void wrappedLongConsumerThrowsAppropriateException()
        throws Throwable
    {
        final ThrowingLongConsumer c = mock(ThrowingLongConsumer.class);

        doThrow(CHECKED).doThrow(UNCHECKED).doThrow(ERROR)
            .when(c).accept(anyLong());

        try {
            LongStream.of(0L).forEach(rethrow(c));
            shouldHaveThrown(ThrownByLambdaException.class);
        } catch (ThrownByLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }

        try {
            LongStream.of(0L).forEach(rethrow(c));
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(UNCHECKED);
        }

        try {
            LongStream.of(0L).forEach(rethrow(c));
            shouldHaveThrown(Error.class);
        } catch (Error e) {
            assertThat(e).isSameAs(ERROR);
        }
    }

    @Test
    public void wrappedDoubleConsumerThrowsAppropriateException()
        throws Throwable
    {
        final ThrowingDoubleConsumer c = mock(ThrowingDoubleConsumer.class);

        doThrow(CHECKED).doThrow(UNCHECKED).doThrow(ERROR)
            .when(c).accept(anyDouble());

        try {
            DoubleStream.of(0.0).forEach(rethrow(c));
            shouldHaveThrown(ThrownByLambdaException.class);
        } catch (ThrownByLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }

        try {
            DoubleStream.of(0.0).forEach(rethrow(c));
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(UNCHECKED);
        }

        try {
            DoubleStream.of(0.0).forEach(rethrow(c));
            shouldHaveThrown(Error.class);
        } catch (Error e) {
            assertThat(e).isSameAs(ERROR);
        }
    }
}
