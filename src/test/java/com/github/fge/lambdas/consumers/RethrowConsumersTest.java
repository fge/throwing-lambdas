package com.github.fge.lambdas.consumers;

import com.github.fge.lambdas.ThrownByLambdaException;
import com.github.fge.lambdas.helpers.Type1;
import com.github.fge.lambdas.helpers.Type2;
import org.testng.annotations.Test;

import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static com.github.fge.lambdas.consumers.Consumers.rethrow;
import static com.github.fge.lambdas.helpers.CustomAssertions.shouldHaveThrown;
import static com.github.fge.lambdas.helpers.Throwables.CHECKED;
import static com.github.fge.lambdas.helpers.Throwables.ERROR;
import static com.github.fge.lambdas.helpers.Throwables.UNCHECKED;
import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("ErrorNotRethrown")
public final class RethrowConsumersTest
{
    @Test
    public void wrappedConsumerThrowsAppropriateException()
    {
        ThrowingConsumer<Type1> c;

        c = t -> { throw CHECKED; };

        try {
            Stream.of(Type1.mock()).forEach(rethrow(c));
            shouldHaveThrown(ThrownByLambdaException.class);
        } catch (ThrownByLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }

        c = t -> { throw UNCHECKED; };

        try {
            Stream.of(Type1.mock()).forEach(rethrow(c));
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(UNCHECKED);
        }

        c = t -> { throw ERROR; };

        try {
            Stream.of(Type1.mock()).forEach(rethrow(c));
            shouldHaveThrown(Error.class);
        } catch (Error e) {
            assertThat(e).isSameAs(ERROR);
        }
    }

    @Test
    public void wrappedIntConsumerThrowsAppropriateException()
    {
        ThrowingIntConsumer c;

        c = value -> { throw CHECKED; };

        try {
            IntStream.of(0).forEach(rethrow(c));
            shouldHaveThrown(ThrownByLambdaException.class);
        } catch (ThrownByLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }

        c = value -> { throw UNCHECKED; };

        try {
            IntStream.of(0).forEach(rethrow(c));
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(UNCHECKED);
        }

        c = value -> { throw ERROR; };

        try {
            IntStream.of(0).forEach(rethrow(c));
            shouldHaveThrown(Error.class);
        } catch (Error e) {
            assertThat(e).isSameAs(ERROR);
        }
    }

    @Test
    public void wrappedLongConsumerThrowsAppropriateException()
    {
        ThrowingLongConsumer c;

        c = value -> { throw CHECKED; };

        try {
            LongStream.of(0L).forEach(rethrow(c));
            shouldHaveThrown(ThrownByLambdaException.class);
        } catch (ThrownByLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }

        c = value -> { throw UNCHECKED; };

        try {
            LongStream.of(0L).forEach(rethrow(c));
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(UNCHECKED);
        }

        c = value -> { throw ERROR; };

        try {
            LongStream.of(0L).forEach(rethrow(c));
            shouldHaveThrown(Error.class);
        } catch (Error e) {
            assertThat(e).isSameAs(ERROR);
        }
    }

    @Test
    public void wrappedDoubleConsumerThrowsAppropriateException()
    {
        ThrowingDoubleConsumer c;

        c = value -> { throw CHECKED; };

        try {
            DoubleStream.of(0.0).forEach(rethrow(c));
            shouldHaveThrown(ThrownByLambdaException.class);
        } catch (ThrownByLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }

        c = value -> { throw UNCHECKED; };

        try {
            DoubleStream.of(0.0).forEach(rethrow(c));
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(UNCHECKED);
        }

        c = value -> { throw ERROR; };

        try {
            DoubleStream.of(0.0).forEach(rethrow(c));
            shouldHaveThrown(Error.class);
        } catch (Error e) {
            assertThat(e).isSameAs(ERROR);
        }
    }

    @Test
    public void wrappedBiConsumerThrowsAppropriateException()
    {
        final Supplier<Type2> supplier = Type2::mock;
        final BiConsumer<Type2, Type2> combiner = (t, u) -> {};

        ThrowingBiConsumer<Type2, Type1> accumulator;

        accumulator = (t, u) -> { throw CHECKED; };

        try {
            Stream.of(Type1.mock())
                .collect(supplier, Consumers.rethrow(accumulator), combiner);
            shouldHaveThrown(ThrownByLambdaException.class);
        } catch (ThrownByLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }

        accumulator = (t, u) -> { throw UNCHECKED; };

        try {
            Stream.of(Type1.mock())
                .collect(supplier, Consumers.rethrow(accumulator), combiner);
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(UNCHECKED);
        }

        accumulator = (t, u) -> { throw ERROR; };

        try {
            Stream.of(Type1.mock())
                .collect(supplier, Consumers.rethrow(accumulator), combiner);
            shouldHaveThrown(Error.class);
        } catch (Error e) {
            assertThat(e).isSameAs(ERROR);
        }
    }

    @Test
    public void wrappedObjIntConsumerThrowsAppropriateException()
    {
        final Supplier<Type1> supplier = Type1::mock;
        final BiConsumer<Type1, Type1> combiner = (t, u) -> {};

        ThrowingObjIntConsumer<Type1> accumulator;

        accumulator = (t, value) -> { throw CHECKED; };

        try {
            IntStream.of(0).collect(supplier, rethrow(accumulator), combiner);
            shouldHaveThrown(ThrownByLambdaException.class);
        } catch (ThrownByLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }

        accumulator = (t, value) -> { throw UNCHECKED; };

        try {
            IntStream.of(0).collect(supplier, rethrow(accumulator), combiner);
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(UNCHECKED);
        }

        accumulator = (t, value) -> { throw ERROR; };

        try {
            IntStream.of(0).collect(supplier, rethrow(accumulator), combiner);
            shouldHaveThrown(Error.class);
        } catch (Error e) {
            assertThat(e).isSameAs(ERROR);
        }
    }

    @Test
    public void wrappedObjLongConsumerThrowsAppropriateException()
    {
        final Supplier<Type1> supplier = Type1::mock;
        final BiConsumer<Type1, Type1> combiner = (t, u) -> {};

        ThrowingObjLongConsumer<Type1> accumulator;

        accumulator = (t, value) -> { throw CHECKED; };

        try {
            LongStream.of(0L).collect(supplier, rethrow(accumulator), combiner);
            shouldHaveThrown(ThrownByLambdaException.class);
        } catch (ThrownByLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }

        accumulator = (t, value) -> { throw UNCHECKED; };

        try {
            LongStream.of(0L).collect(supplier, rethrow(accumulator), combiner);
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(UNCHECKED);
        }

        accumulator = (t, value) -> { throw ERROR; };

        try {
            LongStream.of(0L).collect(supplier, rethrow(accumulator), combiner);
            shouldHaveThrown(Error.class);
        } catch (Error e) {
            assertThat(e).isSameAs(ERROR);
        }
    }

    @Test
    public void wrappedObjDoubleConsumerThrowsAppropriateException()
    {
        final Supplier<Type1> supplier = Type1::mock;
        final BiConsumer<Type1, Type1> combiner = (t, u) -> {};

        ThrowingObjDoubleConsumer<Type1> accumulator;

        accumulator = (t, value) -> { throw CHECKED; };

        try {
            DoubleStream.of(0.0)
                .collect(supplier, rethrow(accumulator), combiner);
            shouldHaveThrown(ThrownByLambdaException.class);
        } catch (ThrownByLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }

        accumulator = (t, value) -> { throw UNCHECKED; };

        try {
            DoubleStream.of(0.0)
                .collect(supplier, rethrow(accumulator), combiner);
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(UNCHECKED);
        }

        accumulator = (t, value) -> { throw ERROR; };

        try {
            DoubleStream.of(0.0)
                .collect(supplier, rethrow(accumulator), combiner);
            shouldHaveThrown(Error.class);
        } catch (Error e) {
            assertThat(e).isSameAs(ERROR);
        }
    }
}
