package com.github.fge.lambdas.functions.twoarity;

import com.github.fge.lambdas.ThrownByLambdaException;
import com.github.fge.lambdas.helpers.Type1;
import com.github.fge.lambdas.helpers.Type2;
import com.github.fge.lambdas.helpers.Type3;
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

@SuppressWarnings({ "unchecked", "ProhibitedExceptionDeclared" })
public final class RethrowTwoArityFunctionsTest
{
    @Test
    public void wrappedBiFunctionThrowsAppropriateException()
        throws Throwable
    {
        final ThrowingBiFunction<Type1, Type2, Type3> f
            = mock(ThrowingBiFunction.class);

        when(f.apply(Type1.any(), Type2.any()))
            .thenThrow(CHECKED).thenThrow(UNCHECKED).thenThrow(ERROR);

        try {
            rethrow(f).apply(Type1.mock(), Type2.mock());
            shouldHaveThrown(ThrownByLambdaException.class);
        } catch (ThrownByLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }

        try {
            rethrow(f).apply(Type1.mock(), Type2.mock());
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(UNCHECKED);
        }

        try {
            rethrow(f).apply(Type1.mock(), Type2.mock());
            shouldHaveThrown(Error.class);
        } catch (Error e) {
            assertThat(e).isSameAs(ERROR);
        }
    }


    @Test
    public void wrappedBinaryOperatorThrowsAppropriateException()
        throws Throwable
    {
        final ThrowingBinaryOperator<Type1> o
            = mock(ThrowingBinaryOperator.class);

        when(o.apply(Type1.any(), Type1.any()))
            .thenThrow(CHECKED).thenThrow(UNCHECKED).thenThrow(ERROR);

        try {
            Stream.of(Type1.mock(), Type1.mock()).reduce(rethrow(o));
            shouldHaveThrown(ThrownByLambdaException.class);
        } catch (ThrownByLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }

        try {
            Stream.of(Type1.mock(), Type1.mock()).reduce(rethrow(o));
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(UNCHECKED);
        }

        try {
            Stream.of(Type1.mock(), Type1.mock()).reduce(rethrow(o));
            shouldHaveThrown(Error.class);
        } catch (Error e) {
            assertThat(e).isSameAs(ERROR);
        }
    }
}
