package com.github.fge.lambdas;

import com.github.fge.lambdas.comparator.ComparatorChain;
import com.github.fge.lambdas.comparator.ThrowingComparator;
import com.github.fge.lambdas.consumer.BiConsumerChain;
import com.github.fge.lambdas.consumer.ConsumerChain;
import com.github.fge.lambdas.consumer.DoubleConsumerChain;
import com.github.fge.lambdas.consumer.IntConsumerChain;
import com.github.fge.lambdas.consumer.LongConsumerChain;
import com.github.fge.lambdas.consumer.ThrowingBiConsumer;
import com.github.fge.lambdas.consumer.ThrowingConsumer;
import com.github.fge.lambdas.consumer.ThrowingDoubleConsumer;
import com.github.fge.lambdas.consumer.ThrowingIntConsumer;
import com.github.fge.lambdas.consumer.ThrowingLongConsumer;
import com.github.fge.lambdas.function.FunctionChain;
import com.github.fge.lambdas.function.ThrowingFunction;

public final class Throwing
{
    private Throwing()
    {
        throw new Error("nice try!");
    }

    public static <T> ComparatorChain<T> comparator(
        final ThrowingComparator<T> comparator)
    {
        return new ComparatorChain<>(comparator);
    }

    public static <T> ConsumerChain<T> consumer(
        final ThrowingConsumer<T> consumer)
    {
        return new ConsumerChain<>(consumer);
    }

    public static <T, U> BiConsumerChain<T, U> biConsumer(
        final ThrowingBiConsumer<T, U> consumer)
    {
        return new BiConsumerChain<>(consumer);
    }

    public static DoubleConsumerChain doubleConsumer(
        final ThrowingDoubleConsumer consumer)
    {
        return new DoubleConsumerChain(consumer);
    }

    public static IntConsumerChain intConsumer(
        final ThrowingIntConsumer consumer)
    {
        return new IntConsumerChain(consumer);
    }

    public static LongConsumerChain longConsumer(
        final ThrowingLongConsumer consumer)
    {
        return new LongConsumerChain(consumer);
    }

    public static <T, R> FunctionChain<T, R> function(
        final ThrowingFunction<T, R> function)
    {
        return new FunctionChain<>(function);
    }
}
