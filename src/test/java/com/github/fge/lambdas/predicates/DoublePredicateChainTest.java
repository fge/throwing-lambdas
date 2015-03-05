package com.github.fge.lambdas.predicates;

import com.github.fge.lambdas.ChainTest;
import com.github.fge.lambdas.Throwing;
import org.testng.annotations.Test;

import java.util.concurrent.Callable;
import java.util.function.DoublePredicate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class DoublePredicateChainTest
    extends ChainTest<DoublePredicate, ThrowingDoublePredicate, DoublePredicateChain, Boolean>
{
    private final double value = 1.0;

    public DoublePredicateChainTest()
    {
        super(true, false);
    }

    @Override
    protected DoublePredicate getFallback()
    {
        return mock(DoublePredicate.class);
    }

    @Override
    protected ThrowingDoublePredicate getThrowing()
    {
        return mock(ThrowingDoublePredicate.class);
    }

    @Override
    protected DoublePredicateChain getChain(final ThrowingDoublePredicate throwing)
    {
        return Throwing.doublePredicate(throwing);
    }

    @Override
    protected Callable<Boolean> toCallable(final DoublePredicate chain)
    {
        return () -> chain.test(value);
    }

    @Override
    protected void configureFull(final ThrowingDoublePredicate throwing)
        throws Throwable
    {
        when(throwing.doTest(value))
            .thenReturn(ret1)
            .thenThrow(checked)
            .thenThrow(unchecked)
            .thenThrow(error);
    }

    @Override
    protected void configureAlternate(final ThrowingDoublePredicate throwing)
        throws Throwable
    {
        when(throwing.doTest(value))
            .thenReturn(ret2);
    }

    @Override
    protected void configureFallback(final DoublePredicate fallback)
    {
        when(fallback.test(value))
            .thenReturn(ret2);
    }

    @Test
    public void orReturnTrueTest()
        throws Throwable
    {
        final ThrowingDoublePredicate throwing = getThrowing();
        configureFull(throwing);

        final DoublePredicate chain = getChain(throwing).orReturnTrue();

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
        final ThrowingDoublePredicate throwing = getThrowing();
        configureFull(throwing);

        final DoublePredicate chain = getChain(throwing).orReturnFalse();

        final Callable<Boolean> callable = toCallable(chain);

        assertThat(callable.call()).isEqualTo(ret1);

        assertThat(callable.call()).isFalse();

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}
