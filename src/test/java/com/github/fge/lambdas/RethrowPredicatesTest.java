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
    public void checkedExceptionThrownFromPredicateIsWrapped()
        throws Throwable
    {
        final ThrowingPredicate<Type1> p = mock(ThrowingPredicate.class);

        when(p.test(any(Type1.class))).thenThrow(CHECKED);

        try {
            Stream.of(mock(Type1.class)).anyMatch(rethrow(p));
            shouldHaveThrown(ThrownFromLambdaException.class);
        } catch (ThrownFromLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }
    }

    @Test
    public void uncheckedExceptionThrownFromPredicateIsThrownAsIs()
        throws Throwable
    {
        final ThrowingPredicate<Type1> p = mock(ThrowingPredicate.class);

        when(p.test(any(Type1.class))).thenThrow(UNCHECKED);

        try {
            Stream.of(mock(Type1.class)).anyMatch(rethrow(p));
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(UNCHECKED);
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

    @Test
    public void checkedExceptionThrownFromIntPredicateIsWrapped()
        throws Throwable
    {
        final ThrowingIntPredicate p = mock(ThrowingIntPredicate.class);

        when(p.test(anyInt())).thenThrow(CHECKED);

        try {
            IntStream.of(0).anyMatch(rethrow(p));
            shouldHaveThrown(ThrownFromLambdaException.class);
        } catch (ThrownFromLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }
    }

    @Test
    public void uncheckedExceptionThrownFromIntPredicateIsThrownAsIs()
        throws Throwable
    {
        final ThrowingIntPredicate p = mock(ThrowingIntPredicate.class);

        when(p.test(anyInt())).thenThrow(UNCHECKED);

        try {
            IntStream.of(0).anyMatch(rethrow(p));
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(UNCHECKED);
        }
    }

    @Test
    public void errorThrownFromIntPredicateisThrownAsIs()
        throws Throwable
    {
        final ThrowingIntPredicate p = mock(ThrowingIntPredicate.class);

        when(p.test(anyInt())).thenThrow(ERROR);

        try {
            IntStream.of(0).anyMatch(rethrow(p));
            shouldHaveThrown(Error.class);
        } catch (Error e) {
            assertThat(e).isSameAs(ERROR);
        }
    }

    @Test
    public void checkedExceptionThrownFromLongPredicateIsWrapped()
        throws Throwable
    {
        final ThrowingLongPredicate p = mock(ThrowingLongPredicate.class);

        when(p.test(anyLong())).thenThrow(CHECKED);

        try {
            LongStream.of(0L).anyMatch(rethrow(p));
            shouldHaveThrown(ThrownFromLambdaException.class);
        } catch (ThrownFromLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }
    }

    @Test
    public void uncheckedExceptionThrownFromLongPredicateIsThrownAsIs()
        throws Throwable
    {
        final ThrowingLongPredicate p = mock(ThrowingLongPredicate.class);

        when(p.test(anyLong())).thenThrow(UNCHECKED);

        try {
            LongStream.of(0L).anyMatch(rethrow(p));
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(UNCHECKED);
        }
    }

    @Test
    public void errorThrownFromLongPredicateisThrownAsIs()
        throws Throwable
    {
        final ThrowingLongPredicate p = mock(ThrowingLongPredicate.class);

        when(p.test(anyLong())).thenThrow(ERROR);

        try {
            LongStream.of(0L).anyMatch(rethrow(p));
            shouldHaveThrown(Error.class);
        } catch (Error e) {
            assertThat(e).isSameAs(ERROR);
        }
    }

    @Test
    public void checkedExceptionThrownFromDoublePredicateIsWrapped()
        throws Throwable
    {
        final ThrowingDoublePredicate p = mock(ThrowingDoublePredicate.class);

        when(p.test(anyDouble())).thenThrow(CHECKED);

        try {
            DoubleStream.of(0.0).anyMatch(rethrow(p));
            shouldHaveThrown(ThrownFromLambdaException.class);
        } catch (ThrownFromLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }
    }

    @Test
    public void uncheckedExceptionThrownFromDoublePredicateIsThrownAsIs()
        throws Throwable
    {
        final ThrowingDoublePredicate p = mock(ThrowingDoublePredicate.class);

        when(p.test(anyDouble())).thenThrow(UNCHECKED);

        try {
            DoubleStream.of(0.0).anyMatch(rethrow(p));
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(UNCHECKED);
        }
    }

    @Test
    public void errorThrownFromDoublePredicateisThrownAsIs()
        throws Throwable
    {
        final ThrowingDoublePredicate p = mock(ThrowingDoublePredicate.class);

        when(p.test(anyDouble())).thenThrow(ERROR);

        try {
            DoubleStream.of(0.0).anyMatch(rethrow(p));
            shouldHaveThrown(Error.class);
        } catch (Error e) {
            assertThat(e).isSameAs(ERROR);
        }
    }
}
