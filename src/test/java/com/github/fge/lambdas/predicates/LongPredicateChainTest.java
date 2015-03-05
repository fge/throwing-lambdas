package com.github.fge.lambdas.predicates;

import com.github.fge.lambdas.ChainTest;
import com.github.fge.lambdas.Throwing;
import org.testng.annotations.Test;

import java.util.concurrent.Callable;
import java.util.function.LongPredicate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class LongPredicateChainTest
    extends ChainTest<LongPredicate, ThrowingLongPredicate, LongPredicateChain, Boolean>
{
    private final long value = 1L;

    public LongPredicateChainTest()
    {
        super(true, false);
    }

    @Override
    protected LongPredicate getFallback()
    {
        return mock(LongPredicate.class);
    }

    @Override
    protected ThrowingLongPredicate getThrowing()
    {
        return mock(ThrowingLongPredicate.class);
    }

    @Override
    protected LongPredicateChain getChain(final ThrowingLongPredicate throwing)
    {
        return Throwing.longPredicate(throwing);
    }

    @Override
    protected Callable<Boolean> toCallable(final LongPredicate chain)
    {
        return () -> chain.test(value);
    }

    @Override
    protected void configureFull(final ThrowingLongPredicate throwing)
        throws Throwable
    {
        when(throwing.doTest(value))
            .thenReturn(ret1)
            .thenThrow(checked)
            .thenThrow(unchecked)
            .thenThrow(error);
    }

    @Override
    protected void configureAlternate(final ThrowingLongPredicate throwing)
        throws Throwable
    {
        when(throwing.doTest(value))
            .thenReturn(ret2);
    }

    @Override
    protected void configureFallback(final LongPredicate fallback)
    {
        when(fallback.test(value))
            .thenReturn(ret2);
    }

    @Test
    public void orReturnTrueTest()
        throws Throwable
    {
        final ThrowingLongPredicate throwing = getThrowing();
        configureFull(throwing);

        final LongPredicate chain = getChain(throwing).orReturnTrue();

        final Callable<Boolean> callable = toCallable(chain);

        assertThat(callable.call()).isEqualTo(ret1);

        assertThat(callable.call()).isTrue();

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    @Test
    public void orReturnFalseTest()
        throws Throwable
    {
        final ThrowingLongPredicate throwing = getThrowing();
        configureFull(throwing);

        final LongPredicate chain = getChain(throwing).orReturnFalse();

        final Callable<Boolean> callable = toCallable(chain);

        assertThat(callable.call()).isEqualTo(ret1);

        assertThat(callable.call()).isFalse();

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}
