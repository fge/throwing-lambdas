package com.github.fge.lambdas.predicates;

import com.github.fge.lambdas.ThrownByLambdaException;
import com.github.fge.lambdas.helpers.Type1;
import org.testng.annotations.Test;

import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static com.github.fge.lambdas.predicates.Predicates.rethrow;
import static com.github.fge.lambdas.helpers.CustomAssertions.shouldHaveThrown;
import static com.github.fge.lambdas.helpers.Throwables.CHECKED;
import static com.github.fge.lambdas.helpers.Throwables.ERROR;
import static com.github.fge.lambdas.helpers.Throwables.UNCHECKED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings("ErrorNotRethrown")
public final class RethrowPredicatesTest
{
    @Test
    public void wrappedPredicateThrowsAppropriateException()
        throws Throwable
    {
        final ThrowingPredicate<Type1> p;

        p = t -> { throw CHECKED; };

        try {
            Stream.of(Type1.mock()).anyMatch(rethrow(p));
            shouldHaveThrown(ThrownByLambdaException.class);
        } catch (ThrownByLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }

        p = t -> { throw UNCHECKED; };

        try {
            Stream.of(Type1.mock()).anyMatch(rethrow(p));
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(UNCHECKED);
        }

        p = t -> { throw ERROR; };

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
        final ThrowingIntPredicate p;

        p = t -> { throw CHECKED; };

        try {
            IntStream.of(0).anyMatch(rethrow(p));
            shouldHaveThrown(ThrownByLambdaException.class);
        } catch (ThrownByLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }

        p = t -> { throw UNCHECKED; };

        try {
            IntStream.of(0).anyMatch(rethrow(p));
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(UNCHECKED);
        }

        p = t -> { throw ERROR; };

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
        final ThrowingLongPredicate p;

        p = t -> { throw CHECKED; };

        try {
            LongStream.of(0L).anyMatch(rethrow(p));
            shouldHaveThrown(ThrownByLambdaException.class);
        } catch (ThrownByLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }

        p = t -> { throw UNCHECKED; };

        try {
            LongStream.of(0L).anyMatch(rethrow(p));
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(UNCHECKED);
        }

        p = t -> { throw ERROR; };

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
        final ThrowingDoublePredicate p;

        p = t -> { throw CHECKED; };

        try {
            DoubleStream.of(0.0).anyMatch(rethrow(p));
            shouldHaveThrown(ThrownByLambdaException.class);
        } catch (ThrownByLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }

        p = t -> { throw UNCHECKED; };

        try {
            DoubleStream.of(0.0).anyMatch(rethrow(p));
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(UNCHECKED);
        }

        p = t -> { throw ERROR; };

        try {
            DoubleStream.of(0.0).anyMatch(rethrow(p));
            shouldHaveThrown(Error.class);
        } catch (Error e) {
            assertThat(e).isSameAs(ERROR);
        }
    }
}
