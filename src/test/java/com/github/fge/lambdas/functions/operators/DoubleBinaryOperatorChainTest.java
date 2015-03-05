package com.github.fge.lambdas.functions.operators;

import com.github.fge.lambdas.ChainTest;
import com.github.fge.lambdas.Throwing;
import org.testng.annotations.Test;

import java.util.concurrent.Callable;
import java.util.function.DoubleBinaryOperator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class DoubleBinaryOperatorChainTest
    extends ChainTest<DoubleBinaryOperator, ThrowingDoubleBinaryOperator, DoubleBinaryOperatorChain, Double>
{
    private final double left = 1.0;
    private final double right = 2.0;

    public DoubleBinaryOperatorChainTest()
    {
        super(42.0, 24.0);
    }

    @Override
    protected DoubleBinaryOperator getFallback()
    {
        return mock(DoubleBinaryOperator.class);
    }

    @Override
    protected ThrowingDoubleBinaryOperator getThrowing()
    {
        return mock(ThrowingDoubleBinaryOperator.class);
    }

    @Override
    protected DoubleBinaryOperatorChain getChain(
        final ThrowingDoubleBinaryOperator throwing)
    {
        return Throwing.doubleBinaryOperator(throwing);
    }

    @Override
    protected Callable<Double> toCallable(final DoubleBinaryOperator chain)
    {
        return () -> chain.applyAsDouble(left, right);
    }

    @Override
    protected void configureFull(final ThrowingDoubleBinaryOperator throwing)
        throws Throwable
    {
        when(throwing.doApplyAsDouble(left, right))
            .thenReturn(ret1)
            .thenThrow(checked)
            .thenThrow(unchecked)
            .thenThrow(error);
    }

    @Override
    protected void configureAlternate(
        final ThrowingDoubleBinaryOperator throwing)
        throws Throwable
    {
        when(throwing.doApplyAsDouble(left, right))
            .thenReturn(ret2);
    }

    @Override
    protected void configureFallback(final DoubleBinaryOperator fallback)
    {
        when(fallback.applyAsDouble(left, right))
            .thenReturn(ret2);
    }

    @Test
    public void orReturnTest()
        throws Throwable
    {
        final ThrowingDoubleBinaryOperator throwing = getThrowing();
        configureFull(throwing);

        final DoubleBinaryOperator chain = getChain(throwing).orReturn(ret2);

        final Callable<Double> callable = toCallable(chain);

        assertThat(callable.call()).isEqualTo(ret1);

        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    @Test
    public void orReturnLeftTest()
        throws Throwable
    {
        final ThrowingDoubleBinaryOperator throwing = getThrowing();
        configureFull(throwing);

        final DoubleBinaryOperator chain = getChain(throwing).orReturnLeft();

        final Callable<Double> callable = toCallable(chain);

        assertThat(callable.call()).isEqualTo(ret1);

        assertThat(callable.call()).isEqualTo(left);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    @Test
    public void orReturnRightTest()
        throws Throwable
    {
        final ThrowingDoubleBinaryOperator throwing = getThrowing();
        configureFull(throwing);

        final DoubleBinaryOperator chain = getChain(throwing).orReturnRight();

        final Callable<Double> callable = toCallable(chain);

        assertThat(callable.call()).isEqualTo(ret1);

        assertThat(callable.call()).isEqualTo(right);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}
