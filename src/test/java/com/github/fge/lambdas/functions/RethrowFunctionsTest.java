package com.github.fge.lambdas.functions;

import com.github.fge.lambdas.ThrownByLambdaException;
import com.github.fge.lambdas.helpers.Type1;
import com.github.fge.lambdas.helpers.Type2;
import org.testng.annotations.Test;

import java.util.stream.Stream;

import static com.github.fge.lambdas.Rethrow.rethrow;
import static com.github.fge.lambdas.helpers.CustomAssertions.shouldHaveThrown;
import static com.github.fge.lambdas.helpers.Throwables.CHECKED;
import static com.github.fge.lambdas.helpers.Throwables.ERROR;
import static com.github.fge.lambdas.helpers.Throwables.UNCHECKED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({
    "ProhibitedExceptionDeclared",
    "unchecked",
    "ErrorNotRethrown"
})
public final class RethrowFunctionsTest
{
    /*
     * Function
     */

    @Test
    public void wrappedFunctionThrowsAppropriateException()
        throws Throwable
    {
        final ThrowingFunction<Type1, Type2> f = mock(ThrowingFunction.class);

        when(f.apply(Type1.any()))
            .thenThrow(CHECKED).thenThrow(UNCHECKED).thenThrow(ERROR);

        try {
            Stream.of(Type1.mock()).map(rethrow(f)).count();
            shouldHaveThrown(ThrownByLambdaException.class);
        } catch (ThrownByLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }

        try {
            Stream.of(Type1.mock()).map(rethrow(f)).count();
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(UNCHECKED);
        }

        try {
            Stream.of(Type1.mock()).map(rethrow(f)).count();
            shouldHaveThrown(Error.class);
        } catch (Error e) {
            assertThat(e).isSameAs(ERROR);
        }
    }

    @Test
    public void wrappedUnaryOperatorThrowsAppropriateException()
        throws Throwable
    {
        final ThrowingUnaryOperator<Type1> o
            = mock(ThrowingUnaryOperator.class);

        when(o.apply(Type1.any()))
            .thenThrow(CHECKED).thenThrow(UNCHECKED).thenThrow(ERROR);

        try {
            Stream.of(Type1.mock()).map(rethrow(o)).count();
            shouldHaveThrown(ThrownByLambdaException.class);
        } catch (ThrownByLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }

        try {
            Stream.of(Type1.mock()).map(rethrow(o)).count();
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(UNCHECKED);
        }

        try {
            Stream.of(Type1.mock()).map(rethrow(o)).count();
            shouldHaveThrown(Error.class);
        } catch (Error e) {
            assertThat(e).isSameAs(ERROR);
        }
    }
}