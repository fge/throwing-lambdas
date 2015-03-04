package com.github.fge.lambdas;

import com.github.fge.lambdas.comparators.ComparatorChain;
import com.github.fge.lambdas.comparators.ThrowingComparator;
import com.github.fge.lambdas.consumers.BiConsumerChain;
import com.github.fge.lambdas.consumers.ConsumerChain;
import com.github.fge.lambdas.consumers.DoubleConsumerChain;
import com.github.fge.lambdas.consumers.IntConsumerChain;
import com.github.fge.lambdas.consumers.LongConsumerChain;
import com.github.fge.lambdas.consumers.ObjDoubleConsumerChain;
import com.github.fge.lambdas.consumers.ObjIntConsumerChain;
import com.github.fge.lambdas.consumers.ObjLongConsumerChain;
import com.github.fge.lambdas.consumers.ThrowingBiConsumer;
import com.github.fge.lambdas.consumers.ThrowingConsumer;
import com.github.fge.lambdas.consumers.ThrowingDoubleConsumer;
import com.github.fge.lambdas.consumers.ThrowingIntConsumer;
import com.github.fge.lambdas.consumers.ThrowingLongConsumer;
import com.github.fge.lambdas.consumers.ThrowingObjDoubleConsumer;
import com.github.fge.lambdas.consumers.ThrowingObjIntConsumer;
import com.github.fge.lambdas.consumers.ThrowingObjLongConsumer;
import com.github.fge.lambdas.function.FunctionChain;
import com.github.fge.lambdas.function.ThrowingFunction;

public final class Throwing
{
    private Throwing()
    {
        throw new Error("nice try!");
    }

    /*
     * Comparators
     */

    public static <T> ComparatorChain<T> comparator(
        final ThrowingComparator<T> comparator)
    {
        return new ComparatorChain<>(comparator);
    }

    /*
     * Consumers
     */
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

    public static <T>ObjDoubleConsumerChain<T> objDoubleConsumer(
        final ThrowingObjDoubleConsumer<T> consumer)
    {
        return new ObjDoubleConsumerChain<>(consumer);
    }

    public static <T>ObjIntConsumerChain<T> objIntConsumer(
        final ThrowingObjIntConsumer<T> consumer)
    {
        return new ObjIntConsumerChain<>(consumer);
    }

    public static <T>ObjLongConsumerChain<T> objLongConsumer(
        final ThrowingObjLongConsumer<T> consumer)
    {
        return new ObjLongConsumerChain<>(consumer);
    }

    /*
     * Functions
     */
    public static <T, R> FunctionChain<T, R> function(
        final ThrowingFunction<T, R> function)
    {
        return new FunctionChain<>(function);
    }
}
