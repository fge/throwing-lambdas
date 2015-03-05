package com.github.fge.lambdas.functions.doublefunctions;

import com.github.fge.lambdas.ChainerTest;
import com.github.fge.lambdas.Throwing;
import org.testng.annotations.Test;

import java.util.concurrent.Callable;
import java.util.function.DoubleToLongFunction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class DoubleToLongFunctionChainerTest
    extends ChainerTest<DoubleToLongFunction, ThrowingDoubleToLongFunction, DoubleToLongFunctionChainer, Long>
{
    private final double value = 42.0;

    public DoubleToLongFunctionChainerTest()
    {
        super(42L, 24L);
    }

    @Override
    protected DoubleToLongFunction getFallback()
    {
        return mock(DoubleToLongFunction.class);
    }

    @Override
    protected ThrowingDoubleToLongFunction getThrowing()
    {
        return mock(ThrowingDoubleToLongFunction.class);
    }

    @Override
    protected DoubleToLongFunctionChainer getChain(
        final ThrowingDoubleToLongFunction throwing)
    {
        return Throwing.doubleToLongFunction(throwing);
    }

    @Override
    protected Callable<Long> toCallable(final DoubleToLongFunction chain)
    {
        return () -> chain.applyAsLong(value);
    }

    @Override
    protected void configureFull(final ThrowingDoubleToLongFunction throwing)
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
        final ThrowingDoubleToLongFunction throwing)
        throws Throwable
    {
        when(throwing.doApplyAsLong(value))
            .thenReturn(ret2);
    }

    @Override
    protected void configureFallback(final DoubleToLongFunction fallback)
    {
        when(fallback.applyAsLong(value))
            .thenReturn(ret2);
    }

    @Test
    public void orReturnTest()
        throws Throwable
    {
        final ThrowingDoubleToLongFunction throwing = getThrowing();
        configureFull(throwing);

        final DoubleToLongFunction chain = getChain(throwing).orReturn(ret2);

        final Callable<Long> callable = toCallable(chain);

        assertThat(callable.call()).isEqualTo(ret1);

        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}
