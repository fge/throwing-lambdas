package com.github.fge.lambdas.consumer;

import com.github.fge.lambdas.Throwing;

import java.util.function.DoubleConsumer;

/**
 * @deprecated use {@link Throwing} instead
 */
@Deprecated
public final class Consumers
{
    private Consumers()
    {
        throw new Error("nice try!");
    }

    /**
     * Return a new throwing consumer chain
     *
     * @param consumer the throwing consumer
     * @param <T> type parameter of the throwing consumer
     * @return a chain
     *
     * @deprecated use {@link Throwing#consumer(ThrowingConsumer)} instead
     */
    @Deprecated
    public static <T> ConsumerChain<T> wrap(final ThrowingConsumer<T> consumer)
    {
        return new ConsumerChain<>(consumer);
    }

    /**
     * Return a new throwing consumer chain
     *
     * @param consumer the throwing consumer
     * @param <T> type parameter of the throwing consumer
     * @return a chain
     *
     * @deprecated use {@link Throwing#consumer(ThrowingConsumer)} instead
     */
    @Deprecated
    public static <T> ConsumerChain<T> tryWith(
        final ThrowingConsumer<T> consumer)
    {
        return wrap(consumer);
    }

    /**
     * Return a new throwing consumer chain
     *
     * @param consumer the throwing consumer
     * @param <T> type parameter of the throwing consumer
     * @return a chain
     *
     * @deprecated use {@link Throwing#consumer(ThrowingConsumer)} instead
     */
    @Deprecated
    public static <T> ConsumerChain<T> rethrow(
        final ThrowingConsumer<T> consumer)
    {
        return wrap(consumer);
    }

    /**
     * Return a new throwing biconsumer chain
     *
     * @param consumer the throwing consumer
     * @param <T> type parameter of the first argument
     * @param <U> type parameter of the second argument
     * @return a chain
     *
     * @deprecated use {@link Throwing#biConsumer(ThrowingBiConsumer)} instead
     */
    @Deprecated
    public static <T, U> BiConsumerChain<T, U> wrap(
        final ThrowingBiConsumer<T, U> consumer)
    {
        return new BiConsumerChain<>(consumer);
    }

    /**
     * Return a new throwing biconsumer chain
     *
     * @param consumer the throwing consumer
     * @param <T> type parameter of the first argument
     * @param <U> type parameter of the second argument
     * @return a chain
     *
     * @deprecated use {@link Throwing#biConsumer(ThrowingBiConsumer)} instead
     */
    @Deprecated
    public static <T, U> BiConsumerChain<T, U> tryWith(
        final ThrowingBiConsumer<T, U> consumer)
    {
        return wrap(consumer);
    }

    /**
     * Return a new throwing biconsumer chain
     *
     * @param consumer the throwing consumer
     * @param <T> type parameter of the first argument
     * @param <U> type parameter of the second argument
     * @return a chain
     *
     * @deprecated use {@link Throwing#biConsumer(ThrowingBiConsumer)} instead
     */
    @Deprecated
    public static <T, U> BiConsumerChain<T, U> rethrow(
        final ThrowingBiConsumer<T, U> consumer)
    {
        return wrap(consumer);
    }

    /**
     * Return a new throwing {@link DoubleConsumer} chain
     *
     * @param consumer the consumer
     * @return a new chain
     *
     * @deprecated use {@link Throwing#consumer(ThrowingDoubleConsumer)}
     * instead
     */
    @Deprecated
    public static DoubleConsumerChain wrap(
        final ThrowingDoubleConsumer consumer)
    {
        return new DoubleConsumerChain(consumer);
    }

    /**
     * Return a new throwing {@link DoubleConsumer} chain
     *
     * @param consumer the consumer
     * @return a new chain
     *
     * @deprecated use {@link Throwing#consumer(ThrowingDoubleConsumer)}
     * instead
     */
    @Deprecated
    public static DoubleConsumerChain tryWith(
        final ThrowingDoubleConsumer consumer)
    {
        return wrap(consumer);
    }

    /**
     * Return a new throwing {@link DoubleConsumer} chain
     *
     * @param consumer the consumer
     * @return a new chain
     *
     * @deprecated use {@link Throwing#consumer(ThrowingDoubleConsumer)}
     * instead
     */
    @Deprecated
    public static DoubleConsumerChain rethrow(
        final ThrowingDoubleConsumer consumer)
    {
        return wrap(consumer);
    }
}
