package com.github.fge.lambdas;

import com.github.fge.lambdas.helpers.Type1;
import com.github.fge.lambdas.predicates.ThrowingPredicate;
import org.testng.annotations.Test;

import java.util.stream.Stream;

import static com.github.fge.lambdas.CustomAssertions.shouldHaveThrown;
import static com.github.fge.lambdas.Rethrow.rethrow;
import static com.github.fge.lambdas.helpers.Throwables.ERROR;
import static com.github.fge.lambdas.helpers.Throwables.REGULAR_EXCEPTION;
import static com.github.fge.lambdas.helpers.Throwables.RUNTIME_EXCEPTION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({ "unchecked", "AutoBoxing", "ProhibitedExceptionDeclared" })
public final class RethrowPredicatesTest
{
    @Test
    public void regularExceptionThrownFromPredicateIsWrapped()
        throws Throwable
    {
        final ThrowingPredicate<Type1> p = mock(ThrowingPredicate.class);

        when(p.test(any(Type1.class))).thenThrow(REGULAR_EXCEPTION);

        try {
            Stream.of(mock(Type1.class)).anyMatch(rethrow(p));
            shouldHaveThrown(ThrownFromLambdaException.class);
        } catch (ThrownFromLambdaException e) {
            assertThat(e.getCause()).isSameAs(REGULAR_EXCEPTION);
        }
    }

    @Test
    public void runtimeExceptionThrownFromPredicateIsThrownAsIs()
        throws Throwable
    {
        final ThrowingPredicate<Type1> p = mock(ThrowingPredicate.class);

        when(p.test(any(Type1.class))).thenThrow(RUNTIME_EXCEPTION);

        try {
            Stream.of(mock(Type1.class)).anyMatch(rethrow(p));
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(RUNTIME_EXCEPTION);
        }
    }

    @Test
    public void errorThrownFromPredicateIsThrownAsIs()
        throws Throwable
    {
        final ThrowingPredicate<Type1> p = mock(ThrowingPredicate.class);

        when(p.test(any(Type1.class))).thenThrow(ERROR);

        try {
            Stream.of(mock(Type1.class)).anyMatch(rethrow(p));
            shouldHaveThrown(Error.class);
        } catch (Error e) {
            assertThat(e).isSameAs(ERROR);
        }
    }
}
