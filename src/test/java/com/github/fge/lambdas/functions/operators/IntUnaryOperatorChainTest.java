package com.github.fge.lambdas.functions.operators;

import com.github.fge.lambdas.ChainTest;
import com.github.fge.lambdas.Throwing;
import org.testng.annotations.Test;

import java.util.concurrent.Callable;
import java.util.function.IntUnaryOperator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class IntUnaryOperatorChainTest
    extends ChainTest<IntUnaryOperator, ThrowingIntUnaryOperator, IntUnaryOperatorChain, Integer>
{
    private final int operand = 1;

    public IntUnaryOperatorChainTest()
    {
        super(42, 24);
    }

    @Override
    protected IntUnaryOperator getFallback()
    {
        return mock(IntUnaryOperator.class);
    }

    @Override
    protected ThrowingIntUnaryOperator getThrowing()
    {
        return mock(ThrowingIntUnaryOperator.class);
    }

    @Override
    protected IntUnaryOperatorChain getChain(
        final ThrowingIntUnaryOperator throwing)
    {
        return Throwing.intUnaryOperator(throwing);
    }

    @Override
    protected Callable<Integer> toCallable(final IntUnaryOperator chain)
    {
        return () -> chain.applyAsInt(operand);
    }

    @Override
    protected void configureFull(final ThrowingIntUnaryOperator throwing)
        throws Throwable
    {
        when(throwing.doApplyAsInt(operand))
            .thenReturn(ret1)
            .thenThrow(checked)
            .thenThrow(unchecked)
            .thenThrow(error);
    }

    @Override
    protected void configureAlternate(
        final ThrowingIntUnaryOperator throwing)
        throws Throwable
    {
        when(throwing.doApplyAsInt(operand))
            .thenReturn(ret2);
    }

    @Override
    protected void configureFallback(final IntUnaryOperator fallback)
    {
        when(fallback.applyAsInt(operand))
            .thenReturn(ret2);
    }

    @Test
    public void orReturnTest()
        throws Throwable
    {
        final ThrowingIntUnaryOperator throwing = getThrowing();
        configureFull(throwing);

        final IntUnaryOperator chain = getChain(throwing).orReturn(ret2);

        final Callable<Integer> callable = toCallable(chain);

        assertThat(callable.call()).isEqualTo(ret1);

        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    @Test
    public void orReturnSelfTest()
        throws Throwable
    {
        final ThrowingIntUnaryOperator throwing = getThrowing();
        configureFull(throwing);

        final IntUnaryOperator chain = getChain(throwing).orReturnSelf();

        final Callable<Integer> callable = toCallable(chain);

        assertThat(callable.call()).isEqualTo(ret1);

        assertThat(callable.call()).isEqualTo(operand);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}
