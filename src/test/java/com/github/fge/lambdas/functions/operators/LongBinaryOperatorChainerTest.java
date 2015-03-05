package com.github.fge.lambdas.functions.operators;

import com.github.fge.lambdas.ChainerTest;
import com.github.fge.lambdas.Throwing;
import org.testng.annotations.Test;

import java.util.concurrent.Callable;
import java.util.function.LongBinaryOperator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class LongBinaryOperatorChainerTest
    extends ChainerTest<LongBinaryOperator, ThrowingLongBinaryOperator, LongBinaryOperatorChainer, Long>
{
    private final long left = 1L;
    private final long right = 2L;

    public LongBinaryOperatorChainerTest()
    {
        super(42L, 24L);
    }

    @Override
    protected LongBinaryOperator getFallback()
    {
        return mock(LongBinaryOperator.class);
    }

    @Override
    protected ThrowingLongBinaryOperator getThrowing()
    {
        return mock(ThrowingLongBinaryOperator.class);
    }

    @Override
    protected LongBinaryOperatorChainer getChain(
        final ThrowingLongBinaryOperator throwing)
    {
        return Throwing.longBinaryOperator(throwing);
    }

    @Override
    protected Callable<Long> toCallable(final LongBinaryOperator chain)
    {
        return () -> chain.applyAsLong(left, right);
    }

    @Override
    protected void configureFull(final ThrowingLongBinaryOperator throwing)
        throws Throwable
    {
        when(throwing.doApplyAsLong(left, right))
            .thenReturn(ret1)
            .thenThrow(checked)
            .thenThrow(unchecked)
            .thenThrow(error);
    }

    @Override
    protected void configureAlternate(
        final ThrowingLongBinaryOperator throwing)
        throws Throwable
    {
        when(throwing.doApplyAsLong(left, right))
            .thenReturn(ret2);
    }

    @Override
    protected void configureFallback(final LongBinaryOperator fallback)
    {
        when(fallback.applyAsLong(left, right))
            .thenReturn(ret2);
    }

    @Test
    public void orReturnTest()
        throws Throwable
    {
        final ThrowingLongBinaryOperator throwing = getThrowing();
        configureFull(throwing);

        final LongBinaryOperator chain = getChain(throwing).orReturn(ret2);

        final Callable<Long> callable = toCallable(chain);

        assertThat(callable.call()).isEqualTo(ret1);

        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    @Test
    public void orReturnLeftTest()
        throws Throwable
    {
        final ThrowingLongBinaryOperator throwing = getThrowing();
        configureFull(throwing);

        final LongBinaryOperator chain = getChain(throwing).orReturnLeft();

        final Callable<Long> callable = toCallable(chain);

        assertThat(callable.call()).isEqualTo(ret1);

        assertThat(callable.call()).isEqualTo(left);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    @Test
    public void orReturnRightTest()
        throws Throwable
    {
        final ThrowingLongBinaryOperator throwing = getThrowing();
        configureFull(throwing);

        final LongBinaryOperator chain = getChain(throwing).orReturnRight();

        final Callable<Long> callable = toCallable(chain);

        assertThat(callable.call()).isEqualTo(ret1);

        assertThat(callable.call()).isEqualTo(right);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}
