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
import com.github.fge.lambdas.functions.BiFunctionChain;
import com.github.fge.lambdas.functions.FunctionChain;
import com.github.fge.lambdas.functions.ThrowingBiFunction;
import com.github.fge.lambdas.functions.ThrowingFunction;
import com.github.fge.lambdas.functions.ThrowingToDoubleFunction;
import com.github.fge.lambdas.functions.ThrowingToIntFunction;
import com.github.fge.lambdas.functions.ThrowingToLongFunction;
import com.github.fge.lambdas.functions.ToDoubleFunctionChain;
import com.github.fge.lambdas.functions.ToIntFunctionChain;
import com.github.fge.lambdas.functions.ToLongFunctionChain;
import com.github.fge.lambdas.functions.doublefunctions.DoubleFunctionChain;
import com.github.fge.lambdas.functions.doublefunctions.DoubleToIntFunctionChain;
import com.github.fge.lambdas.functions.doublefunctions.DoubleToLongFunctionChain;
import com.github.fge.lambdas.functions.doublefunctions.ThrowingDoubleFunction;
import com.github.fge.lambdas.functions.doublefunctions.ThrowingDoubleToIntFunction;
import com.github.fge.lambdas.functions.doublefunctions.ThrowingDoubleToLongFunction;
import com.github.fge.lambdas.functions.intfunctions.IntFunctionChain;
import com.github.fge.lambdas.functions.intfunctions.IntToDoubleFunctionChain;
import com.github.fge.lambdas.functions.intfunctions.IntToLongFunctionChain;
import com.github.fge.lambdas.functions.intfunctions.ThrowingIntFunction;
import com.github.fge.lambdas.functions.intfunctions.ThrowingIntToDoubleFunction;
import com.github.fge.lambdas.functions.intfunctions.ThrowingIntToLongFunction;
import com.github.fge.lambdas.functions.longfunctions.LongFunctionChain;
import com.github.fge.lambdas.functions.longfunctions.LongToDoubleFunctionChain;
import com.github.fge.lambdas.functions.longfunctions.LongToIntFunctionChain;
import com.github.fge.lambdas.functions.longfunctions.ThrowingLongFunction;
import com.github.fge.lambdas.functions.longfunctions.ThrowingLongToDoubleFunction;
import com.github.fge.lambdas.functions.longfunctions.ThrowingLongToIntFunction;
import com.github.fge.lambdas.functions.operators.BinaryOperatorChain;
import com.github.fge.lambdas.functions.operators.DoubleBinaryOperatorChain;
import com.github.fge.lambdas.functions.operators.DoubleUnaryOperatorChain;
import com.github.fge.lambdas.functions.operators.IntBinaryOperatorChain;
import com.github.fge.lambdas.functions.operators.IntUnaryOperatorChain;
import com.github.fge.lambdas.functions.operators.LongBinaryOperatorChain;
import com.github.fge.lambdas.functions.operators.LongUnaryOperatorChain;
import com.github.fge.lambdas.functions.operators.ThrowingBinaryOperator;
import com.github.fge.lambdas.functions.operators.ThrowingDoubleBinaryOperator;
import com.github.fge.lambdas.functions.operators.ThrowingDoubleUnaryOperator;
import com.github.fge.lambdas.functions.operators.ThrowingIntBinaryOperator;
import com.github.fge.lambdas.functions.operators.ThrowingIntUnaryOperator;
import com.github.fge.lambdas.functions.operators.ThrowingLongBinaryOperator;
import com.github.fge.lambdas.functions.operators.ThrowingLongUnaryOperator;
import com.github.fge.lambdas.functions.operators.ThrowingUnaryOperator;
import com.github.fge.lambdas.functions.operators.UnaryOperatorChain;
import com.github.fge.lambdas.predicates.DoublePredicateChain;
import com.github.fge.lambdas.predicates.IntPredicateChain;
import com.github.fge.lambdas.predicates.LongPredicateChain;
import com.github.fge.lambdas.predicates.PredicateChain;
import com.github.fge.lambdas.predicates.ThrowingDoublePredicate;
import com.github.fge.lambdas.predicates.ThrowingIntPredicate;
import com.github.fge.lambdas.predicates.ThrowingLongPredicate;
import com.github.fge.lambdas.predicates.ThrowingPredicate;
import com.github.fge.lambdas.supplier.DoubleSupplierChain;
import com.github.fge.lambdas.supplier.IntSupplierChain;
import com.github.fge.lambdas.supplier.LongSupplierChain;
import com.github.fge.lambdas.supplier.SupplierChain;
import com.github.fge.lambdas.supplier.ThrowingDoubleSupplier;
import com.github.fge.lambdas.supplier.ThrowingIntSupplier;
import com.github.fge.lambdas.supplier.ThrowingLongSupplier;
import com.github.fge.lambdas.supplier.ThrowingSupplier;

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

    public static <T, U, R> BiFunctionChain<T, U, R> biFunction(
        final ThrowingBiFunction<T, U, R> biFunction)
    {
        return new BiFunctionChain<>(biFunction);
    }

    public static <T> ToDoubleFunctionChain<T> toDoubleFunction(
        final ThrowingToDoubleFunction<T> toDoubleFunction)
    {
        return new ToDoubleFunctionChain<>(toDoubleFunction);
    }

    public static <T> ToIntFunctionChain<T> toIntFunction(
        final ThrowingToIntFunction<T> intFunction)
    {
        return new ToIntFunctionChain<>(intFunction);
    }

    public static <T> ToLongFunctionChain<T> toLongFunction(
        final ThrowingToLongFunction<T> toLongFunction)
    {
        return new ToLongFunctionChain<>(toLongFunction);
    }

    public static <R> DoubleFunctionChain<R> doubleFunction(
        final ThrowingDoubleFunction<R> doubleFunction)
    {
        return new DoubleFunctionChain<>(doubleFunction);
    }

    public static DoubleToIntFunctionChain doubleToIntFunction(
        final ThrowingDoubleToIntFunction doubleToIntFunction)
    {
        return new DoubleToIntFunctionChain(doubleToIntFunction);
    }

    public static DoubleToLongFunctionChain doubleToLongFunction(
        final ThrowingDoubleToLongFunction doubleToLongFunction)
    {
        return new DoubleToLongFunctionChain(doubleToLongFunction);
    }

    public static <R> IntFunctionChain<R> intFunction(
        final ThrowingIntFunction<R> intFunction)
    {
        return new IntFunctionChain<>(intFunction);
    }

    public static IntToDoubleFunctionChain intToDoubleFunction(
        final ThrowingIntToDoubleFunction intToDoubleFunction)
    {
        return new IntToDoubleFunctionChain(intToDoubleFunction);
    }

    public static IntToLongFunctionChain intToLongFunction(
        final ThrowingIntToLongFunction intToLongFunction)
    {
        return new IntToLongFunctionChain(intToLongFunction);
    }

    public static <R> LongFunctionChain<R> longFunction(
        final ThrowingLongFunction<R> longFunction)
    {
        return new LongFunctionChain<>(longFunction);
    }

    public static LongToDoubleFunctionChain longToDoubleFunction(
        final ThrowingLongToDoubleFunction longToDoubleFunction)
    {
        return new LongToDoubleFunctionChain(longToDoubleFunction);
    }

    public static LongToIntFunctionChain longToIntFunction(
        final ThrowingLongToIntFunction longToIntFunction)
    {
        return new LongToIntFunctionChain(longToIntFunction);
    }

    public static <T> BinaryOperatorChain<T> binaryOperator(
        final ThrowingBinaryOperator<T> binaryOperator)
    {
        return new BinaryOperatorChain<>(binaryOperator);
    }

    public static DoubleBinaryOperatorChain doubleBinaryOperator(
        final ThrowingDoubleBinaryOperator doubleBinaryOperator)
    {
        return new DoubleBinaryOperatorChain(doubleBinaryOperator);
    }

    public static DoubleUnaryOperatorChain doubleUnaryOperator(
        final ThrowingDoubleUnaryOperator doubleUnaryOperator)
    {
        return new DoubleUnaryOperatorChain(doubleUnaryOperator);
    }

    public static IntBinaryOperatorChain intBinaryOperator(
        final ThrowingIntBinaryOperator intBinaryOperator)
    {
        return new IntBinaryOperatorChain(intBinaryOperator);
    }

    public static IntUnaryOperatorChain intUnaryOperator(
        final ThrowingIntUnaryOperator intUnaryOperator)
    {
        return new IntUnaryOperatorChain(intUnaryOperator);
    }

    public static LongBinaryOperatorChain longBinaryOperator(
        final ThrowingLongBinaryOperator longBinaryOperator)
    {
        return new LongBinaryOperatorChain(longBinaryOperator);
    }

    public static LongUnaryOperatorChain longUnaryOperator(
        final ThrowingLongUnaryOperator longUnaryOperator)
    {
        return new LongUnaryOperatorChain(longUnaryOperator);
    }

    public static <T> UnaryOperatorChain<T> unaryOperator(
        final ThrowingUnaryOperator<T> unaryOperator)
    {
        return new UnaryOperatorChain<>(unaryOperator);
    }

    /*
     * Predicates
     */
    public static <T> PredicateChain<T> predicate(
        final ThrowingPredicate<T> predicate)
    {
        return new PredicateChain<>(predicate);
    }

    public static IntPredicateChain intPredicate(
        final ThrowingIntPredicate intPredicate)
    {
        return new IntPredicateChain(intPredicate);
    }

    public static DoublePredicateChain doublePredicate(
        final ThrowingDoublePredicate doublePredicate)
    {
        return new DoublePredicateChain(doublePredicate);
    }

    public static LongPredicateChain longPredicate(
        final ThrowingLongPredicate longPredicate)
    {
        return new LongPredicateChain(longPredicate);
    }

    /*
     * Suppliers
     */
    public static <T> SupplierChain<T> supplier(
        final ThrowingSupplier<T> supplier)
    {
        return new SupplierChain<>(supplier);
    }

    public static DoubleSupplierChain doubleSupplier(
        final ThrowingDoubleSupplier doubleSupplier)
    {
        return new DoubleSupplierChain(doubleSupplier);
    }

    public static IntSupplierChain intSupplier(
        final ThrowingIntSupplier intSupplier)
    {
        return new IntSupplierChain(intSupplier);
    }

    public static LongSupplierChain longSupplier(
        final ThrowingLongSupplier longSupplier)
    {
        return new LongSupplierChain(longSupplier);
    }
}
