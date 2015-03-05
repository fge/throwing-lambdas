package com.github.fge.lambdas.function.longfunctions;

import com.github.fge.lambdas.ChainTest;
import com.github.fge.lambdas.Throwing;
import org.testng.annotations.Test;

import java.util.concurrent.Callable;
import java.util.function.LongToDoubleFunction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class LongToDoubleFunctionChainTest
    extends ChainTest<LongToDoubleFunction, ThrowingLongToDoubleFunction, LongToDoubleFunctionChain, Double>
{
    private final long value = 42L;

    public LongToDoubleFunctionChainTest()
    {
        super(42.0, 24.0);
    }

    @Override
    protected LongToDoubleFunction getFallback()
    {
        return mock(LongToDoubleFunction.class);
    }

    @Override
    protected ThrowingLongToDoubleFunction getThrowing()
    {
        return mock(ThrowingLongToDoubleFunction.class);
    }

    @Override
    protected LongToDoubleFunctionChain getChain(
        final ThrowingLongToDoubleFunction throwing)
    {
        return Throwing.longToDoubleFunction(throwing);
    }

    @Override
    protected Callable<Double> toCallable(final LongToDoubleFunction chain)
    {
        return () -> chain.applyAsDouble(value);
    }

    @Override
    protected void configureFull(final ThrowingLongToDoubleFunction throwing)
        throws Throwable
    {
        when(throwing.doApplyAsDouble(value))
            .thenReturn(ret1)
            .thenThrow(checked)
            .thenThrow(unchecked)
            .thenThrow(error);
    }

    @Override
    protected void configureAlternate(
        final ThrowingLongToDoubleFunction throwing)
        throws Throwable
    {
        when(throwing.doApplyAsDouble(value))
            .thenReturn(ret2);
    }

    @Override
    protected void configureFallback(final LongToDoubleFunction fallback)
    {
        when(fallback.applyAsDouble(value))
            .thenReturn(ret2);
    }

    @Test
    public void orReturnTest()
        throws Throwable
    {
        final ThrowingLongToDoubleFunction throwing = getThrowing();
        configureFull(throwing);

        final LongToDoubleFunction chain = getChain(throwing).orReturn(ret2);

        final Callable<Double> callable = toCallable(chain);

        assertThat(callable.call()).isEqualTo(ret1);

        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}
