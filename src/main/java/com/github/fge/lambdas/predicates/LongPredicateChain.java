package com.github.fge.lambdas.predicates;

import com.github.fge.lambdas.Chain;
import com.github.fge.lambdas.ThrowablesFactory;

import java.util.function.LongPredicate;

public class LongPredicateChain
    extends Chain<LongPredicate, ThrowingLongPredicate, LongPredicateChain>
    implements ThrowingLongPredicate
{
    public LongPredicateChain(
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
    public LongPredicateChain orTryWith(final ThrowingLongPredicate other)
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

        return new LongPredicateChain(longPredicate);
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
                throw ThrowablesFactory.INSTANCE.get(exclass, throwable);
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
