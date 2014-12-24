package com.github.fge.lambdas;

import com.github.fge.lambdas.collectors.ThrowingBiConsumer;
import com.github.fge.lambdas.helpers.Type1;
import com.github.fge.lambdas.helpers.Type2;
import org.testng.annotations.Test;

import static com.github.fge.lambdas.CustomAssertions.shouldHaveThrown;
import static com.github.fge.lambdas.Rethrow.rethrow;
import static com.github.fge.lambdas.helpers.Throwables.CHECKED;
import static com.github.fge.lambdas.helpers.Throwables.ERROR;
import static com.github.fge.lambdas.helpers.Throwables.UNCHECKED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

@SuppressWarnings({
    "unchecked",
    "ErrorNotRethrown",
    "ProhibitedExceptionDeclared"
})
public final class RethrowTwoArityConsumersTest
{
    @Test
    public void wrappedBiConsumerThrowsAppropriateException()
        throws Throwable
    {
        final ThrowingBiConsumer<Type1, Type2> c
            = mock(ThrowingBiConsumer.class);

        final Type1 t = Type1.mock();
        final Type2 u = Type2.mock();

        doThrow(CHECKED).doThrow(UNCHECKED).doThrow(ERROR).when(c).accept(t, u);

        try {
            rethrow(c).accept(t, u);
            shouldHaveThrown(ThrownByLambdaException.class);
        } catch (ThrownByLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }

        try {
            rethrow(c).accept(t, u);
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(UNCHECKED);
        }

        try {
            rethrow(c).accept(t, u);
            shouldHaveThrown(Error.class);
        } catch (Error e) {
            assertThat(e).isSameAs(ERROR);
        }
    }
}
