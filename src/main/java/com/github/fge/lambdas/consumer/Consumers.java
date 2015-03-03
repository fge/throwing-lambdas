package com.github.fge.lambdas.consumer;

import com.github.fge.lambdas.Throwing;

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
    public static <T> ThrowingConsumerChain<T> wrap(
        final ThrowingConsumer<T> consumer)
    {
        return new ThrowingConsumerChain<>(consumer);
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
    public static <T> ThrowingConsumerChain<T> tryWith(
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
    public static <T> ThrowingConsumerChain<T> rethrow(
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
    public static <T, U> ThrowingBiConsumerChain<T, U> wrap(
        final ThrowingBiConsumer<T, U> consumer)
    {
        return new ThrowingBiConsumerChain<>(consumer);
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
    public static <T, U> ThrowingBiConsumerChain<T, U> tryWith(
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
    public static <T, U> ThrowingBiConsumerChain<T, U> rethrow(
        final ThrowingBiConsumer<T, U> consumer)
    {
        return wrap(consumer);
    }
}
