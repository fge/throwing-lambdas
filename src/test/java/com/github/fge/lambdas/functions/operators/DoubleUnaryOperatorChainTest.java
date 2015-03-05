package com.github.fge.lambdas.functions.operators;

import com.github.fge.lambdas.ChainTest;
import com.github.fge.lambdas.Throwing;
import org.testng.annotations.Test;

import java.util.concurrent.Callable;
import java.util.function.DoubleUnaryOperator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class DoubleUnaryOperatorChainTest
    extends ChainTest<DoubleUnaryOperator, ThrowingDoubleUnaryOperator, DoubleUnaryOperatorChain, Double>
{
    private final double operand = 1.0;

    public DoubleUnaryOperatorChainTest()
    {
        super(42.0, 24.0);
    }

    @Override
    protected DoubleUnaryOperator getFallback()
    {
        return mock(DoubleUnaryOperator.class);
    }

    @Override
    protected ThrowingDoubleUnaryOperator getThrowing()
    {
        return mock(ThrowingDoubleUnaryOperator.class);
    }

    @Override
    protected DoubleUnaryOperatorChain getChain(
        final ThrowingDoubleUnaryOperator throwing)
    {
        return Throwing.doubleUnaryOperator(throwing);
    }

    @Override
    protected Callable<Double> toCallable(final DoubleUnaryOperator chain)
    {
        return () -> chain.applyAsDouble(operand);
    }

    @Override
    protected void configureFull(final ThrowingDoubleUnaryOperator throwing)
        throws Throwable
    {
        when(throwing.doApplyAsDouble(operand))
            .thenReturn(ret1)
            .thenThrow(checked)
            .thenThrow(unchecked)
            .thenThrow(error);
    }

    @Override
    protected void configureAlternate(
        final ThrowingDoubleUnaryOperator throwing)
        throws Throwable
    {
        when(throwing.doApplyAsDouble(operand))
            .thenReturn(ret2);
    }

    @Override
    protected void configureFallback(final DoubleUnaryOperator fallback)
    {
        when(fallback.applyAsDouble(operand))
            .thenReturn(ret2);
    }

    @Test
    public void orReturnTest()
        throws Throwable
    {
        final ThrowingDoubleUnaryOperator throwing = getThrowing();
        configureFull(throwing);

        final DoubleUnaryOperator chain = getChain(throwing).orReturn(ret2);

        final Callable<Double> callable = toCallable(chain);

        assertThat(callable.call()).isEqualTo(ret1);

        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    @Test
    public void orReturnSelfTest()
        throws Throwable
    {
        final ThrowingDoubleUnaryOperator throwing = getThrowing();
        configureFull(throwing);

        final DoubleUnaryOperator chain = getChain(throwing).orReturnSelf();

        final Callable<Double> callable = toCallable(chain);

        assertThat(callable.call()).isEqualTo(ret1);

        assertThat(callable.call()).isEqualTo(operand);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}
