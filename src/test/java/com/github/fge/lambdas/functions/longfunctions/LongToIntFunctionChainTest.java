package com.github.fge.lambdas.functions.longfunctions;

import com.github.fge.lambdas.ChainTest;
import com.github.fge.lambdas.Throwing;
import org.testng.annotations.Test;

import java.util.concurrent.Callable;
import java.util.function.LongToIntFunction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class LongToIntFunctionChainTest
    extends ChainTest<LongToIntFunction, ThrowingLongToIntFunction, LongToIntFunctionChain, Integer>
{
    private final long value = 42L;

    public LongToIntFunctionChainTest()
    {
        super(42, 24);
    }

    @Override
    protected LongToIntFunction getFallback()
    {
        return mock(LongToIntFunction.class);
    }

    @Override
    protected ThrowingLongToIntFunction getThrowing()
    {
        return mock(ThrowingLongToIntFunction.class);
    }

    @Override
    protected LongToIntFunctionChain getChain(
        final ThrowingLongToIntFunction throwing)
    {
        return Throwing.longToIntFunction(throwing);
    }

    @Override
    protected Callable<Integer> toCallable(final LongToIntFunction chain)
    {
        return () -> chain.applyAsInt(value);
    }

    @Override
    protected void configureFull(final ThrowingLongToIntFunction throwing)
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
        final ThrowingLongToIntFunction throwing)
        throws Throwable
    {
        when(throwing.doApplyAsInt(value))
            .thenReturn(ret2);
    }

    @Override
    protected void configureFallback(final LongToIntFunction fallback)
    {
        when(fallback.applyAsInt(value))
            .thenReturn(ret2);
    }

    @Test
    public void orReturnTest()
        throws Throwable
    {
        final ThrowingLongToIntFunction throwing = getThrowing();
        configureFull(throwing);

        final LongToIntFunction chain = getChain(throwing).orReturn(ret2);

        final Callable<Integer> callable = toCallable(chain);

        assertThat(callable.call()).isEqualTo(ret1);

        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}
