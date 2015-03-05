package com.github.fge.lambdas.function.operators;

import com.github.fge.lambdas.ChainTest;
import com.github.fge.lambdas.Throwing;
import org.testng.annotations.Test;

import java.util.concurrent.Callable;
import java.util.function.IntBinaryOperator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class IntBinaryOperatorChainTest
    extends ChainTest<IntBinaryOperator, ThrowingIntBinaryOperator, IntBinaryOperatorChain, Integer>
{
    private final int left = 1;
    private final int right = 2;

    public IntBinaryOperatorChainTest()
    {
        super(42, 24);
    }

    @Override
    protected IntBinaryOperator getFallback()
    {
        return mock(IntBinaryOperator.class);
    }

    @Override
    protected ThrowingIntBinaryOperator getThrowing()
    {
        return mock(ThrowingIntBinaryOperator.class);
    }

    @Override
    protected IntBinaryOperatorChain getChain(
        final ThrowingIntBinaryOperator throwing)
    {
        return Throwing.intBinaryOperator(throwing);
    }

    @Override
    protected Callable<Integer> toCallable(final IntBinaryOperator chain)
    {
        return () -> chain.applyAsInt(left, right);
    }

    @Override
    protected void configureFull(final ThrowingIntBinaryOperator throwing)
        throws Throwable
    {
        when(throwing.doApplyAsInt(left, right))
            .thenReturn(ret1)
            .thenThrow(checked)
            .thenThrow(unchecked)
            .thenThrow(error);
    }

    @Override
    protected void configureAlternate(
        final ThrowingIntBinaryOperator throwing)
        throws Throwable
    {
        when(throwing.doApplyAsInt(left, right))
            .thenReturn(ret2);
    }

    @Override
    protected void configureFallback(final IntBinaryOperator fallback)
    {
        when(fallback.applyAsInt(left, right))
            .thenReturn(ret2);
    }

    @Test
    public void orReturnTest()
        throws Throwable
    {
        final ThrowingIntBinaryOperator throwing = getThrowing();
        configureFull(throwing);

        final IntBinaryOperator chain = getChain(throwing).orReturn(ret2);

        final Callable<Integer> callable = toCallable(chain);

        assertThat(callable.call()).isEqualTo(ret1);

        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    @Test
    public void orReturnLeftTest()
        throws Throwable
    {
        final ThrowingIntBinaryOperator throwing = getThrowing();
        configureFull(throwing);

        final IntBinaryOperator chain = getChain(throwing).orReturnLeft();

        final Callable<Integer> callable = toCallable(chain);

        assertThat(callable.call()).isEqualTo(ret1);

        assertThat(callable.call()).isEqualTo(left);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    @Test
    public void orReturnRightTest()
        throws Throwable
    {
        final ThrowingIntBinaryOperator throwing = getThrowing();
        configureFull(throwing);

        final IntBinaryOperator chain = getChain(throwing).orReturnRight();

        final Callable<Integer> callable = toCallable(chain);

        assertThat(callable.call()).isEqualTo(ret1);

        assertThat(callable.call()).isEqualTo(right);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}
