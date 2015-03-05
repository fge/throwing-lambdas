package com.github.fge.lambdas.functions.doublefunctions;

import com.github.fge.lambdas.ChainTest;
import com.github.fge.lambdas.Throwing;
import org.testng.annotations.Test;

import java.util.concurrent.Callable;
import java.util.function.DoubleToIntFunction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class DoubleToIntFunctionChainTest
    extends ChainTest<DoubleToIntFunction, ThrowingDoubleToIntFunction, DoubleToIntFunctionChain, Integer>
{
    private final double value = 42.0;

    public DoubleToIntFunctionChainTest()
    {
        super(42, 24);
    }

    @Override
    protected DoubleToIntFunction getFallback()
    {
        return mock(DoubleToIntFunction.class);
    }

    @Override
    protected ThrowingDoubleToIntFunction getThrowing()
    {
        return mock(ThrowingDoubleToIntFunction.class);
    }

    @Override
    protected DoubleToIntFunctionChain getChain(
        final ThrowingDoubleToIntFunction throwing)
    {
        return Throwing.doubleToIntFunction(throwing);
    }

    @Override
    protected Callable<Integer> toCallable(final DoubleToIntFunction chain)
    {
        return () -> chain.applyAsInt(value);
    }

    @Override
    protected void configureFull(final ThrowingDoubleToIntFunction throwing)
        throws Throwable
    {
        when(throwing.doApplyAsInt(value))
            .thenReturn(ret1)
            .thenThrow(checked)
            .thenThrow(unchecked)
            .thenThrow(error);
    }

    @Override
    protected void configureAlternate(
        final ThrowingDoubleToIntFunction throwing)
        throws Throwable
    {
        when(throwing.doApplyAsInt(value))
            .thenReturn(ret2);
    }

    @Override
    protected void configureFallback(final DoubleToIntFunction fallback)
    {
        when(fallback.applyAsInt(value))
            .thenReturn(ret2);
    }

    @Test
    public void orReturnTest()
        throws Throwable
    {
        final ThrowingDoubleToIntFunction throwing = getThrowing();
        configureFull(throwing);

        final DoubleToIntFunction chain = getChain(throwing).orReturn(ret2);

        final Callable<Integer> callable = toCallable(chain);

        assertThat(callable.call()).isEqualTo(ret1);

        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}
