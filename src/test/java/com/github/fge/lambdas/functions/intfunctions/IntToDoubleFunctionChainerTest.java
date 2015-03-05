package com.github.fge.lambdas.functions.intfunctions;

import com.github.fge.lambdas.ChainerTest;
import com.github.fge.lambdas.Throwing;
import org.testng.annotations.Test;

import java.util.concurrent.Callable;
import java.util.function.IntToDoubleFunction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class IntToDoubleFunctionChainerTest
    extends ChainerTest<IntToDoubleFunction, ThrowingIntToDoubleFunction, IntToDoubleFunctionChainer, Double>
{
    private final int value = 42;

    public IntToDoubleFunctionChainerTest()
    {
        super(42.0, 24.0);
    }

    @Override
    protected IntToDoubleFunction getFallback()
    {
        return mock(IntToDoubleFunction.class);
    }

    @Override
    protected ThrowingIntToDoubleFunction getThrowing()
    {
        return mock(ThrowingIntToDoubleFunction.class);
    }

    @Override
    protected IntToDoubleFunctionChainer getChain(
        final ThrowingIntToDoubleFunction throwing)
    {
        return Throwing.intToDoubleFunction(throwing);
    }

    @Override
    protected Callable<Double> toCallable(final IntToDoubleFunction chain)
    {
        return () -> chain.applyAsDouble(value);
    }

    @Override
    protected void configureFull(final ThrowingIntToDoubleFunction throwing)
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
        final ThrowingIntToDoubleFunction throwing)
        throws Throwable
    {
        when(throwing.doApplyAsDouble(value))
            .thenReturn(ret2);
    }

    @Override
    protected void configureFallback(final IntToDoubleFunction fallback)
    {
        when(fallback.applyAsDouble(value))
            .thenReturn(ret2);
    }

    @Test
    public void orReturnTest()
        throws Throwable
    {
        final ThrowingIntToDoubleFunction throwing = getThrowing();
        configureFull(throwing);

        final IntToDoubleFunction chain = getChain(throwing).orReturn(ret2);

        final Callable<Double> callable = toCallable(chain);

        assertThat(callable.call()).isEqualTo(ret1);

        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}
