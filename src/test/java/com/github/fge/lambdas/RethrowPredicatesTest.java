package com.github.fge.lambdas;

import com.github.fge.lambdas.helpers.Type1;
import com.github.fge.lambdas.predicates.ThrowingDoublePredicate;
import com.github.fge.lambdas.predicates.ThrowingIntPredicate;
import com.github.fge.lambdas.predicates.ThrowingLongPredicate;
import com.github.fge.lambdas.predicates.ThrowingPredicate;
import org.testng.annotations.Test;

import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static com.github.fge.lambdas.CustomAssertions.shouldHaveThrown;
import static com.github.fge.lambdas.Rethrow.rethrow;
import static com.github.fge.lambdas.helpers.Throwables.ERROR;
import static com.github.fge.lambdas.helpers.Throwables.CHECKED;
import static com.github.fge.lambdas.helpers.Throwables.UNCHECKED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({
    "unchecked",
    "AutoBoxing",
    "ProhibitedExceptionDeclared",
    "ErrorNotRethrown"
})
public final class RethrowPredicatesTest
{
    @Test
    public void wrappedPredicateThrowsAppropriateException()
        throws Throwable
    {
        final ThrowingPredicate<Type1> p = mock(ThrowingPredicate.class);

        when(p.test(Type1.any()))
            .thenThrow(CHECKED).thenThrow(UNCHECKED).thenThrow(ERROR);

        try {
            Stream.of(Type1.mock()).anyMatch(rethrow(p));
            shouldHaveThrown(ThrownByLambdaException.class);
        } catch (ThrownByLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }

        try {
            Stream.of(Type1.mock()).anyMatch(rethrow(p));
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(UNCHECKED);
        }

        try {
            Stream.of(Type1.mock()).anyMatch(rethrow(p));
            shouldHaveThrown(Error.class);
        } catch (Error e) {
            assertThat(e).isSameAs(ERROR);
        }
    }

    @Test
    public void wrappedIntPredicateThrowsAppropriateException()
        throws Throwable
    {
        final ThrowingIntPredicate p = mock(ThrowingIntPredicate.class);

        when(p.test(anyInt()))
            .thenThrow(CHECKED).thenThrow(UNCHECKED).thenThrow(ERROR);

        try {
            IntStream.of(0).anyMatch(rethrow(p));
            shouldHaveThrown(ThrownByLambdaException.class);
        } catch (ThrownByLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }

        try {
            IntStream.of(0).anyMatch(rethrow(p));
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(UNCHECKED);
        }

        try {
            IntStream.of(0).anyMatch(rethrow(p));
            shouldHaveThrown(Error.class);
        } catch (Error e) {
            assertThat(e).isSameAs(ERROR);
        }
    }

    @Test
    public void wrappedLongPredicateThrowAppropriateException()
        throws Throwable
    {
        final ThrowingLongPredicate p = mock(ThrowingLongPredicate.class);

        when(p.test(anyLong()))
            .thenThrow(CHECKED).thenThrow(UNCHECKED).thenThrow(ERROR);

        try {
            LongStream.of(0L).anyMatch(rethrow(p));
            shouldHaveThrown(ThrownByLambdaException.class);
        } catch (ThrownByLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }

        try {
            LongStream.of(0L).anyMatch(rethrow(p));
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(UNCHECKED);
        }

        try {
            LongStream.of(0L).anyMatch(rethrow(p));
            shouldHaveThrown(Error.class);
        } catch (Error e) {
            assertThat(e).isSameAs(ERROR);
        }
    }

    @Test
    public void wrappedDoublePredicateThrowsAppropriateException()
        throws Throwable
    {
        final ThrowingDoublePredicate p = mock(ThrowingDoublePredicate.class);

        when(p.test(anyDouble()))
            .thenThrow(CHECKED).thenThrow(UNCHECKED).thenThrow(ERROR);

        try {
            DoubleStream.of(0.0).anyMatch(rethrow(p));
            shouldHaveThrown(ThrownByLambdaException.class);
        } catch (ThrownByLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }

        try {
            DoubleStream.of(0.0).anyMatch(rethrow(p));
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(UNCHECKED);
        }

        try {
            DoubleStream.of(0.0).anyMatch(rethrow(p));
            shouldHaveThrown(Error.class);
        } catch (Error e) {
            assertThat(e).isSameAs(ERROR);
        }
    }
}
