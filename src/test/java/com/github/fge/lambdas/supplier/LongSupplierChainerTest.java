package com.github.fge.lambdas.supplier;

import com.github.fge.lambdas.ChainerTest;
import com.github.fge.lambdas.Throwing;
import org.testng.annotations.Test;

import java.util.concurrent.Callable;
import java.util.function.LongSupplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class LongSupplierChainerTest
    extends ChainerTest<LongSupplier, ThrowingLongSupplier, LongSupplierChainer, Long>
{
    public LongSupplierChainerTest()
    {
        super(42L, 24L);
    }

    @Override
    protected LongSupplier getFallback()
    {
        return mock(LongSupplier.class);
    }

    @Override
    protected ThrowingLongSupplier getThrowing()
    {
        return mock(ThrowingLongSupplier.class);
    }

    @Override
    protected LongSupplierChainer getChain(
        final ThrowingLongSupplier throwing)
    {
        return Throwing.longSupplier(throwing);
    }

    @Override
    protected Callable<Long> toCallable(final LongSupplier chain)
    {
        return chain::getAsLong;
    }

    @Override
    protected void configureFull(final ThrowingLongSupplier throwing)
        throws Throwable
    {
        when(throwing.doGetAsLong())
            .thenReturn(ret1)
            .thenThrow(checked)
            .thenThrow(unchecked)
            .thenThrow(error);
    }

    @Override
    protected void configureAlternate(final ThrowingLongSupplier throwing)
        throws Throwable
    {
        when(throwing.doGetAsLong())
            .thenReturn(ret2);
    }

    @Override
    protected void configureFallback(final LongSupplier fallback)
    {
        when(fallback.getAsLong())
            .thenReturn(ret2);
    }

    @Test
    public void orReturnTest()
        throws Throwable
    {
        final ThrowingLongSupplier throwing = getThrowing();
        configureFull(throwing);

        final LongSupplier chain = getChain(throwing).orReturn(ret2);

        final Callable<Long> callable = toCallable(chain);

        assertThat(callable.call()).isEqualTo(ret1);

        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}
