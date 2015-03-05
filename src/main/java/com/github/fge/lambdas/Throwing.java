package com.github.fge.lambdas;

import com.github.fge.lambdas.comparators.ComparatorChainer;
import com.github.fge.lambdas.comparators.ThrowingComparator;
import com.github.fge.lambdas.consumers.BiConsumerChainer;
import com.github.fge.lambdas.consumers.ConsumerChainer;
import com.github.fge.lambdas.consumers.DoubleConsumerChainer;
import com.github.fge.lambdas.consumers.IntConsumerChainer;
import com.github.fge.lambdas.consumers.LongConsumerChainer;
import com.github.fge.lambdas.consumers.ObjDoubleConsumerChainer;
import com.github.fge.lambdas.consumers.ObjIntConsumerChainer;
import com.github.fge.lambdas.consumers.ObjLongConsumerChainer;
import com.github.fge.lambdas.consumers.ThrowingBiConsumer;
import com.github.fge.lambdas.consumers.ThrowingConsumer;
import com.github.fge.lambdas.consumers.ThrowingDoubleConsumer;
import com.github.fge.lambdas.consumers.ThrowingIntConsumer;
import com.github.fge.lambdas.consumers.ThrowingLongConsumer;
import com.github.fge.lambdas.consumers.ThrowingObjDoubleConsumer;
import com.github.fge.lambdas.consumers.ThrowingObjIntConsumer;
import com.github.fge.lambdas.consumers.ThrowingObjLongConsumer;
import com.github.fge.lambdas.functions.BiFunctionChainer;
import com.github.fge.lambdas.functions.FunctionChainer;
import com.github.fge.lambdas.functions.ThrowingBiFunction;
import com.github.fge.lambdas.functions.ThrowingFunction;
import com.github.fge.lambdas.functions.ThrowingToDoubleFunction;
import com.github.fge.lambdas.functions.ThrowingToIntFunction;
import com.github.fge.lambdas.functions.ThrowingToLongFunction;
import com.github.fge.lambdas.functions.ToDoubleFunctionChainer;
import com.github.fge.lambdas.functions.ToIntFunctionChainer;
import com.github.fge.lambdas.functions.ToLongFunctionChainer;
import com.github.fge.lambdas.functions.doublefunctions.DoubleFunctionChainer;
import com.github.fge.lambdas.functions.doublefunctions.DoubleToIntFunctionChainer;
import com.github.fge.lambdas.functions.doublefunctions.DoubleToLongFunctionChainer;
import com.github.fge.lambdas.functions.doublefunctions.ThrowingDoubleFunction;
import com.github.fge.lambdas.functions.doublefunctions.ThrowingDoubleToIntFunction;
import com.github.fge.lambdas.functions.doublefunctions.ThrowingDoubleToLongFunction;
import com.github.fge.lambdas.functions.intfunctions.IntFunctionChainer;
import com.github.fge.lambdas.functions.intfunctions.IntToDoubleFunctionChainer;
import com.github.fge.lambdas.functions.intfunctions.IntToLongFunctionChainer;
import com.github.fge.lambdas.functions.intfunctions.ThrowingIntFunction;
import com.github.fge.lambdas.functions.intfunctions.ThrowingIntToDoubleFunction;
import com.github.fge.lambdas.functions.intfunctions.ThrowingIntToLongFunction;
import com.github.fge.lambdas.functions.longfunctions.LongFunctionChainer;
import com.github.fge.lambdas.functions.longfunctions.LongToDoubleFunctionChainer;
import com.github.fge.lambdas.functions.longfunctions.LongToIntFunctionChainer;
import com.github.fge.lambdas.functions.longfunctions.ThrowingLongFunction;
import com.github.fge.lambdas.functions.longfunctions.ThrowingLongToDoubleFunction;
import com.github.fge.lambdas.functions.longfunctions.ThrowingLongToIntFunction;
import com.github.fge.lambdas.functions.operators.BinaryOperatorChainer;
import com.github.fge.lambdas.functions.operators.DoubleBinaryOperatorChainer;
import com.github.fge.lambdas.functions.operators.DoubleUnaryOperatorChainer;
import com.github.fge.lambdas.functions.operators.IntBinaryOperatorChainer;
import com.github.fge.lambdas.functions.operators.IntUnaryOperatorChainer;
import com.github.fge.lambdas.functions.operators.LongBinaryOperatorChainer;
import com.github.fge.lambdas.functions.operators.LongUnaryOperatorChainer;
import com.github.fge.lambdas.functions.operators.ThrowingBinaryOperator;
import com.github.fge.lambdas.functions.operators.ThrowingDoubleBinaryOperator;
import com.github.fge.lambdas.functions.operators.ThrowingDoubleUnaryOperator;
import com.github.fge.lambdas.functions.operators.ThrowingIntBinaryOperator;
import com.github.fge.lambdas.functions.operators.ThrowingIntUnaryOperator;
import com.github.fge.lambdas.functions.operators.ThrowingLongBinaryOperator;
import com.github.fge.lambdas.functions.operators.ThrowingLongUnaryOperator;
import com.github.fge.lambdas.functions.operators.ThrowingUnaryOperator;
import com.github.fge.lambdas.functions.operators.UnaryOperatorChainer;
import com.github.fge.lambdas.predicates.DoublePredicateChainer;
import com.github.fge.lambdas.predicates.IntPredicateChainer;
import com.github.fge.lambdas.predicates.LongPredicateChainer;
import com.github.fge.lambdas.predicates.PredicateChainer;
import com.github.fge.lambdas.predicates.ThrowingDoublePredicate;
import com.github.fge.lambdas.predicates.ThrowingIntPredicate;
import com.github.fge.lambdas.predicates.ThrowingLongPredicate;
import com.github.fge.lambdas.predicates.ThrowingPredicate;
import com.github.fge.lambdas.supplier.DoubleSupplierChainer;
import com.github.fge.lambdas.supplier.IntSupplierChainer;
import com.github.fge.lambdas.supplier.LongSupplierChainer;
import com.github.fge.lambdas.supplier.SupplierChainer;
import com.github.fge.lambdas.supplier.ThrowingDoubleSupplier;
import com.github.fge.lambdas.supplier.ThrowingIntSupplier;
import com.github.fge.lambdas.supplier.ThrowingLongSupplier;
import com.github.fge.lambdas.supplier.ThrowingSupplier;

/**
 * Class to invoke throwing instance chains
 *
 * <p>You may instantiate this class if you wish, for instance, to add your own
 * throwing instances of existing functional interfaces (either your own, or
 * those of the JDK not covered by this package).</p>
 *
 * <p>All method names are defined after the non throwing instance they provide
 * a chain for (for instance, {@code Throwing.function()}, {@code
 * Throwing.consumer()}, etc etc).</p>
 *
 * @see Chainer
 */
public class Throwing
{
    private Throwing()
    {
        throw new Error("nice try!");
    }

    /*
     * Comparators
     */
    public static <T> ComparatorChainer<T> comparator(
        final ThrowingComparator<T> comparator)
    {
        return new ComparatorChainer<>(comparator);
    }

    /*
     * Consumers
     */
    public static <T> ConsumerChainer<T> consumer(
        final ThrowingConsumer<T> consumer)
    {
        return new ConsumerChainer<>(consumer);
    }

    public static <T, U> BiConsumerChainer<T, U> biConsumer(
        final ThrowingBiConsumer<T, U> consumer)
    {
        return new BiConsumerChainer<>(consumer);
    }

    public static DoubleConsumerChainer doubleConsumer(
        final ThrowingDoubleConsumer consumer)
    {
        return new DoubleConsumerChainer(consumer);
    }

    public static IntConsumerChainer intConsumer(
        final ThrowingIntConsumer consumer)
    {
        return new IntConsumerChainer(consumer);
    }

    public static LongConsumerChainer longConsumer(
        final ThrowingLongConsumer consumer)
    {
        return new LongConsumerChainer(consumer);
    }

    public static <T>ObjDoubleConsumerChainer<T> objDoubleConsumer(
        final ThrowingObjDoubleConsumer<T> consumer)
    {
        return new ObjDoubleConsumerChainer<>(consumer);
    }

    public static <T>ObjIntConsumerChainer<T> objIntConsumer(
        final ThrowingObjIntConsumer<T> consumer)
    {
        return new ObjIntConsumerChainer<>(consumer);
    }

    public static <T>ObjLongConsumerChainer<T> objLongConsumer(
        final ThrowingObjLongConsumer<T> consumer)
    {
        return new ObjLongConsumerChainer<>(consumer);
    }

    /*
     * Functions
     */
    public static <T, R> FunctionChainer<T, R> function(
        final ThrowingFunction<T, R> function)
    {
        return new FunctionChainer<>(function);
    }

    public static <T, U, R> BiFunctionChainer<T, U, R> biFunction(
        final ThrowingBiFunction<T, U, R> biFunction)
    {
        return new BiFunctionChainer<>(biFunction);
    }

    public static <T> ToDoubleFunctionChainer<T> toDoubleFunction(
        final ThrowingToDoubleFunction<T> toDoubleFunction)
    {
        return new ToDoubleFunctionChainer<>(toDoubleFunction);
    }

    public static <T> ToIntFunctionChainer<T> toIntFunction(
        final ThrowingToIntFunction<T> intFunction)
    {
        return new ToIntFunctionChainer<>(intFunction);
    }

    public static <T> ToLongFunctionChainer<T> toLongFunction(
        final ThrowingToLongFunction<T> toLongFunction)
    {
        return new ToLongFunctionChainer<>(toLongFunction);
    }

    public static <R> DoubleFunctionChainer<R> doubleFunction(
        final ThrowingDoubleFunction<R> doubleFunction)
    {
        return new DoubleFunctionChainer<>(doubleFunction);
    }

    public static DoubleToIntFunctionChainer doubleToIntFunction(
        final ThrowingDoubleToIntFunction doubleToIntFunction)
    {
        return new DoubleToIntFunctionChainer(doubleToIntFunction);
    }

    public static DoubleToLongFunctionChainer doubleToLongFunction(
        final ThrowingDoubleToLongFunction doubleToLongFunction)
    {
        return new DoubleToLongFunctionChainer(doubleToLongFunction);
    }

    public static <R> IntFunctionChainer<R> intFunction(
        final ThrowingIntFunction<R> intFunction)
    {
        return new IntFunctionChainer<>(intFunction);
    }

    public static IntToDoubleFunctionChainer intToDoubleFunction(
        final ThrowingIntToDoubleFunction intToDoubleFunction)
    {
        return new IntToDoubleFunctionChainer(intToDoubleFunction);
    }

    public static IntToLongFunctionChainer intToLongFunction(
        final ThrowingIntToLongFunction intToLongFunction)
    {
        return new IntToLongFunctionChainer(intToLongFunction);
    }

    public static <R> LongFunctionChainer<R> longFunction(
        final ThrowingLongFunction<R> longFunction)
    {
        return new LongFunctionChainer<>(longFunction);
    }

    public static LongToDoubleFunctionChainer longToDoubleFunction(
        final ThrowingLongToDoubleFunction longToDoubleFunction)
    {
        return new LongToDoubleFunctionChainer(longToDoubleFunction);
    }

    public static LongToIntFunctionChainer longToIntFunction(
        final ThrowingLongToIntFunction longToIntFunction)
    {
        return new LongToIntFunctionChainer(longToIntFunction);
    }

    public static <T> BinaryOperatorChainer<T> binaryOperator(
        final ThrowingBinaryOperator<T> binaryOperator)
    {
        return new BinaryOperatorChainer<>(binaryOperator);
    }

    public static DoubleBinaryOperatorChainer doubleBinaryOperator(
        final ThrowingDoubleBinaryOperator doubleBinaryOperator)
    {
        return new DoubleBinaryOperatorChainer(doubleBinaryOperator);
    }

    public static DoubleUnaryOperatorChainer doubleUnaryOperator(
        final ThrowingDoubleUnaryOperator doubleUnaryOperator)
    {
        return new DoubleUnaryOperatorChainer(doubleUnaryOperator);
    }

    public static IntBinaryOperatorChainer intBinaryOperator(
        final ThrowingIntBinaryOperator intBinaryOperator)
    {
        return new IntBinaryOperatorChainer(intBinaryOperator);
    }

    public static IntUnaryOperatorChainer intUnaryOperator(
        final ThrowingIntUnaryOperator intUnaryOperator)
    {
        return new IntUnaryOperatorChainer(intUnaryOperator);
    }

    public static LongBinaryOperatorChainer longBinaryOperator(
        final ThrowingLongBinaryOperator longBinaryOperator)
    {
        return new LongBinaryOperatorChainer(longBinaryOperator);
    }

    public static LongUnaryOperatorChainer longUnaryOperator(
        final ThrowingLongUnaryOperator longUnaryOperator)
    {
        return new LongUnaryOperatorChainer(longUnaryOperator);
    }

    public static <T> UnaryOperatorChainer<T> unaryOperator(
        final ThrowingUnaryOperator<T> unaryOperator)
    {
        return new UnaryOperatorChainer<>(unaryOperator);
    }

    /*
     * Predicates
     */
    public static <T> PredicateChainer<T> predicate(
        final ThrowingPredicate<T> predicate)
    {
        return new PredicateChainer<>(predicate);
    }

    public static IntPredicateChainer intPredicate(
        final ThrowingIntPredicate intPredicate)
    {
        return new IntPredicateChainer(intPredicate);
    }

    public static DoublePredicateChainer doublePredicate(
        final ThrowingDoublePredicate doublePredicate)
    {
        return new DoublePredicateChainer(doublePredicate);
    }

    public static LongPredicateChainer longPredicate(
        final ThrowingLongPredicate longPredicate)
    {
        return new LongPredicateChainer(longPredicate);
    }

    /*
     * Suppliers
     */
    public static <T> SupplierChainer<T> supplier(
        final ThrowingSupplier<T> supplier)
    {
        return new SupplierChainer<>(supplier);
    }

    public static DoubleSupplierChainer doubleSupplier(
        final ThrowingDoubleSupplier doubleSupplier)
    {
        return new DoubleSupplierChainer(doubleSupplier);
    }

    public static IntSupplierChainer intSupplier(
        final ThrowingIntSupplier intSupplier)
    {
        return new IntSupplierChainer(intSupplier);
    }

    public static LongSupplierChainer longSupplier(
        final ThrowingLongSupplier longSupplier)
    {
        return new LongSupplierChainer(longSupplier);
    }
}
