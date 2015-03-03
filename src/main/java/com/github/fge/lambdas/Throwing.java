package com.github.fge.lambdas;

import com.github.fge.lambdas.comparator.ThrowingComparator;
import com.github.fge.lambdas.comparator.ThrowingComparatorChain;
import com.github.fge.lambdas.consumer.ThrowingBiConsumer;
import com.github.fge.lambdas.consumer.ThrowingBiConsumerChain;
import com.github.fge.lambdas.consumer.ThrowingConsumer;
import com.github.fge.lambdas.consumer.ThrowingConsumerChain;
import com.github.fge.lambdas.function.ThrowingFunction;
import com.github.fge.lambdas.function.ThrowingFunctionChain;

public final class Throwing
{
    private Throwing()
    {
        throw new Error("nice try!");
    }

    public static <T> ThrowingComparatorChain<T> comparator(
        final ThrowingComparator<T> comparator)
    {
        return new ThrowingComparatorChain<>(comparator);
    }

    public static <T> ThrowingConsumerChain<T> consumer(
        final ThrowingConsumer<T> consumer)
    {
        return new ThrowingConsumerChain<>(consumer);
    }

    public static <T, U> ThrowingBiConsumerChain<T, U> biConsumer(
        final ThrowingBiConsumer<T, U> consumer)
    {
        return new ThrowingBiConsumerChain<>(consumer);
    }

    public static <T, R> ThrowingFunctionChain<T, R> function(
        final ThrowingFunction<T, R> function)
    {
        return new ThrowingFunctionChain<>(function);
    }
}
