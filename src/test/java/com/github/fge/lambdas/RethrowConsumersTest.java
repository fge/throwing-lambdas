package com.github.fge.lambdas;

import com.github.fge.lambdas.consumers.ThrowingConsumer;
import com.github.fge.lambdas.consumers.ThrowingIntConsumer;
import com.github.fge.lambdas.helpers.Type1;
import org.testng.annotations.Test;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.github.fge.lambdas.CustomAssertions.shouldHaveThrown;
import static com.github.fge.lambdas.Rethrow.rethrow;
import static com.github.fge.lambdas.helpers.Throwables.CHECKED;
import static com.github.fge.lambdas.helpers.Throwables.ERROR;
import static com.github.fge.lambdas.helpers.Throwables.UNCHECKED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
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
    public void checkedExceptionThrownByConsumerIsWrapped()
        throws Throwable
    {
        final ThrowingConsumer<Type1> c = mock(ThrowingConsumer.class);

        doThrow(CHECKED).when(c).accept(any(Type1.class));

        try {
            Stream.of(mock(Type1.class)).forEach(rethrow(c));
            shouldHaveThrown(ThrownByLambdaException.class);
        } catch (ThrownByLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }
    }

    @Test
    public void uncheckedExceptionThrownByConsumerIsThrownAsIs()
        throws Throwable
    {
        final ThrowingConsumer<Type1> c = mock(ThrowingConsumer.class);

        doThrow(UNCHECKED).when(c).accept(any(Type1.class));

        try {
            Stream.of(mock(Type1.class)).forEach(rethrow(c));
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(UNCHECKED);
        }
    }

    @Test
    public void errorThrownByConsumerIsThrownAsIs()
        throws Throwable
    {
        final ThrowingConsumer<Type1> c = mock(ThrowingConsumer.class);

        doThrow(ERROR).when(c).accept(any(Type1.class));

        try {
            Stream.of(mock(Type1.class)).forEach(rethrow(c));
            shouldHaveThrown(Error.class);
        } catch (Error e) {
            assertThat(e).isSameAs(ERROR);
        }
    }

    @Test
    public void checkedExceptionThrownByIntConsumerIsWrapped()
        throws Throwable
    {
        final ThrowingIntConsumer c = mock(ThrowingIntConsumer.class);

        doThrow(CHECKED).when(c).accept(anyInt());

        try {
            IntStream.of(0).forEach(rethrow(c));
            shouldHaveThrown(ThrownByLambdaException.class);
        } catch (ThrownByLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }
    }

    @Test
    public void uncheckedExceptionThrownByIntConsumerIsThrownAsIs()
        throws Throwable
    {
        final ThrowingIntConsumer c = mock(ThrowingIntConsumer.class);

        doThrow(UNCHECKED).when(c).accept(anyInt());

        try {
            IntStream.of(0).forEach(rethrow(c));
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(UNCHECKED);
        }
    }

    @Test
    public void errorThrownByIntConsumerIsThrownAsIs()
        throws Throwable
    {
        final ThrowingIntConsumer c = mock(ThrowingIntConsumer.class);

        doThrow(ERROR).when(c).accept(anyInt());

        try {
            IntStream.of(0).forEach(rethrow(c));
            shouldHaveThrown(Error.class);
        } catch (Error e) {
            assertThat(e).isSameAs(ERROR);
        }
    }
}
