package com.github.fge.lambdas.supplier;

import com.github.fge.lambdas.ChainerTest;
import com.github.fge.lambdas.Throwing;
import org.testng.annotations.Test;

import java.util.concurrent.Callable;
import java.util.function.IntSupplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class IntSupplierChainerTest
    extends ChainerTest<IntSupplier, ThrowingIntSupplier, IntSupplierChainer, Integer>
{
    public IntSupplierChainerTest()
    {
        super(42, 24);
    }

    @Override
    protected IntSupplier getFallback()
    {
        return mock(IntSupplier.class);
    }

    @Override
    protected ThrowingIntSupplier getThrowing()
    {
        return mock(ThrowingIntSupplier.class);
    }

    @Override
    protected IntSupplierChainer getChain(
        final ThrowingIntSupplier throwing)
    {
        return Throwing.intSupplier(throwing);
    }

    @Override
    protected Callable<Integer> toCallable(final IntSupplier chain)
    {
        return chain::getAsInt;
    }

    @Override
    protected void configureFull(final ThrowingIntSupplier throwing)
        throws Throwable
    {
        when(throwing.doGetAsInt())
            .thenReturn(ret1)
            .thenThrow(checked)
            .thenThrow(unchecked)
            .thenThrow(error);
    }

    @Override
    protected void configureAlternate(final ThrowingIntSupplier throwing)
        throws Throwable
    {
        when(throwing.doGetAsInt())
            .thenReturn(ret2);
    }

    @Override
    protected void configureFallback(final IntSupplier fallback)
    {
        when(fallback.getAsInt())
            .thenReturn(ret2);
    }

    @Test
    public void orReturnTest()
        throws Throwable
    {
        final ThrowingIntSupplier throwing = getThrowing();
        configureFull(throwing);

        final IntSupplier chain = getChain(throwing).orReturn(ret2);

        final Callable<Integer> callable = toCallable(chain);

        assertThat(callable.call()).isEqualTo(ret1);

        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}
