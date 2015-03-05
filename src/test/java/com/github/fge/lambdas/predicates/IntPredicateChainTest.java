package com.github.fge.lambdas.predicates;

import com.github.fge.lambdas.ChainTest;
import com.github.fge.lambdas.Throwing;
import org.testng.annotations.Test;

import java.util.concurrent.Callable;
import java.util.function.IntPredicate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class IntPredicateChainTest
    extends ChainTest<IntPredicate, ThrowingIntPredicate, IntPredicateChain, Boolean>
{
    private final int value = 1;

    public IntPredicateChainTest()
    {
        super(true, false);
    }

    @Override
    protected IntPredicate getFallback()
    {
        return mock(IntPredicate.class);
    }

    @Override
    protected ThrowingIntPredicate getThrowing()
    {
        return mock(ThrowingIntPredicate.class);
    }

    @Override
    protected IntPredicateChain getChain(final ThrowingIntPredicate throwing)
    {
        return Throwing.intPredicate(throwing);
    }

    @Override
    protected Callable<Boolean> toCallable(final IntPredicate chain)
    {
        return () -> chain.test(value);
    }

    @Override
    protected void configureFull(final ThrowingIntPredicate throwing)
        throws Throwable
    {
        when(throwing.doTest(value))
            .thenReturn(ret1)
            .thenThrow(checked)
            .thenThrow(unchecked)
            .thenThrow(error);
    }

    @Override
    protected void configureAlternate(final ThrowingIntPredicate throwing)
        throws Throwable
    {
        when(throwing.doTest(value))
            .thenReturn(ret2);
    }

    @Override
    protected void configureFallback(final IntPredicate fallback)
    {
        when(fallback.test(value))
            .thenReturn(ret2);
    }

    @Test
    public void orReturnTrueTest()
        throws Throwable
    {
        final ThrowingIntPredicate throwing = getThrowing();
        configureFull(throwing);

        final IntPredicate chain = getChain(throwing).orReturnTrue();

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
        final ThrowingIntPredicate throwing = getThrowing();
        configureFull(throwing);

        final IntPredicate chain = getChain(throwing).orReturnFalse();

        final Callable<Boolean> callable = toCallable(chain);

        assertThat(callable.call()).isEqualTo(ret1);

        assertThat(callable.call()).isFalse();

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}
