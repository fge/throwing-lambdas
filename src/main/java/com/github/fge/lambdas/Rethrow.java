package com.github.fge.lambdas;


import com.github.fge.lambdas.predicates.ThrowingDoublePredicate;
import com.github.fge.lambdas.predicates.ThrowingIntPredicate;
import com.github.fge.lambdas.predicates.ThrowingLongPredicate;
import com.github.fge.lambdas.predicates.ThrowingPredicate;

import java.util.function.DoublePredicate;
import java.util.function.IntPredicate;
import java.util.function.LongPredicate;
import java.util.function.Predicate;

@SuppressWarnings("NonFinalUtilityClass")
public class Rethrow
{
    public static <T> Predicate<T> rethrow(final ThrowingPredicate<T> p)
    {
        return t -> {
            try {
                return p.test(t);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable tooBad) {
                throw new ThrownByLambdaException(tooBad);
            }
        };
    }

    public static IntPredicate rethrow(final ThrowingIntPredicate p)
    {
        return t -> {
            try {
                return p.test(t);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable tooBad) {
                throw new ThrownByLambdaException(tooBad);
            }
        };
    }

    public static LongPredicate rethrow(final ThrowingLongPredicate p)
    {
        return t -> {
            try {
                return p.test(t);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable tooBad) {
                throw new ThrownByLambdaException(tooBad);
            }
        };
    }

    public static DoublePredicate rethrow(final ThrowingDoublePredicate p)
    {
        return t -> {
            try {
                return p.test(t);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable tooBad) {
                throw new ThrownByLambdaException(tooBad);
            }
        };
    }
}
