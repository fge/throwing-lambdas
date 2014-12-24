package com.github.fge.lambdas;

import com.github.fge.lambdas.consumers.ThrowingConsumer;
import com.github.fge.lambdas.helpers.Type1;
import org.testng.annotations.Test;

import java.util.stream.Stream;

import static com.github.fge.lambdas.CustomAssertions.shouldHaveThrown;
import static com.github.fge.lambdas.Rethrow.rethrow;
import static com.github.fge.lambdas.helpers.Throwables.CHECKED;
import static com.github.fge.lambdas.helpers.Throwables.ERROR;
import static com.github.fge.lambdas.helpers.Throwables.UNCHECKED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
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
    public void checkedExceptionThrownFromConsumerIsWrapped()
        throws Throwable
    {
        final ThrowingConsumer<Type1> c = mock(ThrowingConsumer.class);

        doThrow(CHECKED).when(c).accept(any(Type1.class));

        try {
            Stream.of(mock(Type1.class)).forEach(rethrow(c));
            shouldHaveThrown(ThrownFromLambdaException.class);
        } catch (ThrownFromLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }
    }

    @Test
    public void uncheckedExceptionThrownFromConsumerIsThrownAsIs()
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
    public void errorThrownFromConsumerIsThrownAsIs()
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
}
