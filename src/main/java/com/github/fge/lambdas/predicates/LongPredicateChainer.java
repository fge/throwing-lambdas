package com.github.fge.lambdas.predicates;

import com.github.fge.lambdas.Chainer;

import java.util.function.LongPredicate;

public class LongPredicateChainer
    extends Chainer<LongPredicate, ThrowingLongPredicate, LongPredicateChainer>
    implements ThrowingLongPredicate
{
    public LongPredicateChainer(
        final ThrowingLongPredicate throwing)
    {
        super(throwing);
    }

    @Override
    public boolean doTest(final long value)
        throws Throwable
    {
        return throwing.doTest(value);
    }

    @Override
    public LongPredicateChainer orTryWith(final ThrowingLongPredicate other)
    {
        final ThrowingLongPredicate longPredicate = value -> {
            try {
                return throwing.doTest(value);
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                return other.doTest(value);
            }
        };

        return new LongPredicateChainer(longPredicate);
    }

    @Override
    public <E extends RuntimeException> ThrowingLongPredicate orThrow(
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
    public LongPredicate fallbackTo(final LongPredicate fallback)
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

    public LongPredicate orReturnTrue()
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

    public LongPredicate orReturnFalse()
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
