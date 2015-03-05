package com.github.fge.lambdas.functions.operators;

import com.github.fge.lambdas.ChainTest;
import com.github.fge.lambdas.Throwing;
import org.testng.annotations.Test;

import java.util.concurrent.Callable;
import java.util.function.LongUnaryOperator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class LongUnaryOperatorChainTest
    extends ChainTest<LongUnaryOperator, ThrowingLongUnaryOperator, LongUnaryOperatorChain, Long>
{
    private final long operand = 1L;

    public LongUnaryOperatorChainTest()
    {
        super(42L, 24L);
    }

    @Override
    protected LongUnaryOperator getFallback()
    {
        return mock(LongUnaryOperator.class);
    }

    @Override
    protected ThrowingLongUnaryOperator getThrowing()
    {
        return mock(ThrowingLongUnaryOperator.class);
    }

    @Override
    protected LongUnaryOperatorChain getChain(
        final ThrowingLongUnaryOperator throwing)
    {
        return Throwing.longUnaryOperator(throwing);
    }

    @Override
    protected Callable<Long> toCallable(final LongUnaryOperator chain)
    {
        return () -> chain.applyAsLong(operand);
    }

    @Override
    protected void configureFull(final ThrowingLongUnaryOperator throwing)
        throws Throwable
    {
        when(throwing.doApplyAsLong(operand))
            .thenReturn(ret1)
            .thenThrow(checked)
            .thenThrow(unchecked)
            .thenThrow(error);
    }

    @Override
    protected void configureAlternate(
        final ThrowingLongUnaryOperator throwing)
        throws Throwable
    {
        when(throwing.doApplyAsLong(operand))
            .thenReturn(ret2);
    }

    @Override
    protected void configureFallback(final LongUnaryOperator fallback)
    {
        when(fallback.applyAsLong(operand))
            .thenReturn(ret2);
    }

    @Test
    public void orReturnTest()
        throws Throwable
    {
        final ThrowingLongUnaryOperator throwing = getThrowing();
        configureFull(throwing);

        final LongUnaryOperator chain = getChain(throwing).orReturn(ret2);

        final Callable<Long> callable = toCallable(chain);

        assertThat(callable.call()).isEqualTo(ret1);

        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    @Test
    public void orReturnSelfTest()
        throws Throwable
    {
        final ThrowingLongUnaryOperator throwing = getThrowing();
        configureFull(throwing);

        final LongUnaryOperator chain = getChain(throwing).orReturnSelf();

        final Callable<Long> callable = toCallable(chain);

        assertThat(callable.call()).isEqualTo(ret1);

        assertThat(callable.call()).isEqualTo(operand);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}
