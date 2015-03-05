package com.github.fge.lambdas.predicates;

import com.github.fge.lambdas.Chainer;

import java.util.function.IntPredicate;

public class IntPredicateChainer
    extends Chainer<IntPredicate, ThrowingIntPredicate, IntPredicateChainer>
    implements ThrowingIntPredicate
{
    public IntPredicateChainer(
        final ThrowingIntPredicate throwing)
    {
        super(throwing);
    }

    @Override
    public boolean doTest(final int value)
        throws Throwable
    {
        return throwing.doTest(value);
    }

    @Override
    public IntPredicateChainer orTryWith(final ThrowingIntPredicate other)
    {
        final ThrowingIntPredicate intPredicate = value -> {
            try {
                return throwing.doTest(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return other.doTest(value);
            }
        };

        return new IntPredicateChainer(intPredicate);
    }

    @Override
    public <E extends RuntimeException> ThrowingIntPredicate orThrow(
        final Class<E> exclass)
    {
        return value -> {
            try {
                return throwing.doTest(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable throwable) {
                throw rethrow(exclass, throwable);
            }
        };
    }

    @Override
    public IntPredicate fallbackTo(final IntPredicate fallback)
    {
        return value -> {
            try {
                return throwing.doTest(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return fallback.test(value);
            }
        };
    }

    public IntPredicate orReturnTrue()
    {
        return value -> {
            try {
                return throwing.doTest(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return true;
            }
        };
    }

    public IntPredicate orReturnFalse()
    {
        return value -> {
            try {
                return throwing.doTest(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return false;
            }
        };
    }
}
