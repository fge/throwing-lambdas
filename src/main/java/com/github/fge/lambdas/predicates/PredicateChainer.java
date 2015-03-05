package com.github.fge.lambdas.predicates;

import com.github.fge.lambdas.Chainer;
import com.github.fge.lambdas.ThrowablesFactory;

import java.util.function.Predicate;

public class PredicateChainer<T>
    extends Chainer<Predicate<T>, ThrowingPredicate<T>, PredicateChain<T>>
    implements ThrowingPredicate<T>
{
    public PredicateChainer(
        final ThrowingPredicate<T> throwing)
    {
        super(throwing);
    }

    @Override
    public boolean doTest(final T t)
        throws Throwable
    {
        return throwing.doTest(t);
    }

    @Override
    public PredicateChainer<T> orTryWith(final ThrowingPredicate<T> other)
    {
        final ThrowingPredicate<T> predicate = t -> {
            try {
                return throwing.doTest(t);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return other.doTest(t);
            }
        };

        return new PredicateChainer<>(predicate);
    }

    @Override
    public <E extends RuntimeException> ThrowingPredicate<T> orThrow(
        final Class<E> exclass)
    {
        return t -> {
            try {
                return throwing.doTest(t);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable throwable) {
                throw rethrow(exclass, throwable);
            }
        };
    }

    @Override
    public Predicate<T> fallbackTo(final Predicate<T> fallback)
    {
        return t -> {
            try {
                return throwing.doTest(t);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return fallback.test(t);
            }
        };
    }

    public Predicate<T> orReturnTrue()
    {
        return t -> {
            try {
                return throwing.doTest(t);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return true;
            }
        };
    }

    public Predicate<T> orReturnFalse()
    {
        return t -> {
            try {
                return throwing.doTest(t);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return false;
            }
        };
    }
}
