package com.github.fge.lambdas.function.intfunctions;

import com.github.fge.lambdas.ChainTest;
import com.github.fge.lambdas.Throwing;
import org.testng.annotations.Test;

import java.util.concurrent.Callable;
import java.util.function.IntToLongFunction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class IntToLongFunctionChainTest
    extends ChainTest<IntToLongFunction, ThrowingIntToLongFunction, IntToLongFunctionChain, Long>
{
    private final int value = 42;

    public IntToLongFunctionChainTest()
    {
        super(42L, 24L);
    }

    @Override
    protected IntToLongFunction getFallback()
    {
        return mock(IntToLongFunction.class);
    }

    @Override
    protected ThrowingIntToLongFunction getThrowing()
    {
        return mock(ThrowingIntToLongFunction.class);
    }

    @Override
    protected IntToLongFunctionChain getChain(
        final ThrowingIntToLongFunction throwing)
    {
        return Throwing.intToLongFunction(throwing);
    }

    @Override
    protected Callable<Long> toCallable(final IntToLongFunction chain)
    {
        return () -> chain.applyAsLong(value);
    }

    @Override
    protected void configureFull(final ThrowingIntToLongFunction throwing)
        throws Throwable
    {
        when(throwing.doApplyAsLong(value))
            .thenReturn(ret1)
            .thenThrow(checked)
            .thenThrow(unchecked)
            .thenThrow(error);
    }

    @Override
    protected void configureAlternate(
        final ThrowingIntToLongFunction throwing)
        throws Throwable
    {
        when(throwing.doApplyAsLong(value))
            .thenReturn(ret2);
    }

    @Override
    protected void configureFallback(final IntToLongFunction fallback)
    {
        when(fallback.applyAsLong(value))
            .thenReturn(ret2);
    }

    @Test
    public void orReturnTest()
        throws Throwable
    {
        final ThrowingIntToLongFunction throwing = getThrowing();
        configureFull(throwing);

        final IntToLongFunction chain = getChain(throwing).orReturn(ret2);

        final Callable<Long> callable = toCallable(chain);

        assertThat(callable.call()).isEqualTo(ret1);

        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}
