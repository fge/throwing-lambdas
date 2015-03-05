package com.github.fge.lambdas.supplier;

import com.github.fge.lambdas.ChainerTest;
import com.github.fge.lambdas.Throwing;
import org.testng.annotations.Test;

import java.util.concurrent.Callable;
import java.util.function.DoubleSupplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class DoubleSupplierChainerTest
    extends ChainerTest<DoubleSupplier, ThrowingDoubleSupplier, DoubleSupplierChainer, Double>
{
    public DoubleSupplierChainerTest()
    {
        super(42.0, 24.0);
    }

    @Override
    protected DoubleSupplier getFallback()
    {
        return mock(DoubleSupplier.class);
    }

    @Override
    protected ThrowingDoubleSupplier getThrowing()
    {
        return mock(ThrowingDoubleSupplier.class);
    }

    @Override
    protected DoubleSupplierChainer getChain(
        final ThrowingDoubleSupplier throwing)
    {
        return Throwing.doubleSupplier(throwing);
    }

    @Override
    protected Callable<Double> toCallable(final DoubleSupplier chain)
    {
        return chain::getAsDouble;
    }

    @Override
    protected void configureFull(final ThrowingDoubleSupplier throwing)
        throws Throwable
    {
        when(throwing.doGetAsDouble())
            .thenReturn(ret1)
            .thenThrow(checked)
            .thenThrow(unchecked)
            .thenThrow(error);
    }

    @Override
    protected void configureAlternate(final ThrowingDoubleSupplier throwing)
        throws Throwable
    {
        when(throwing.doGetAsDouble())
            .thenReturn(ret2);
    }

    @Override
    protected void configureFallback(final DoubleSupplier fallback)
    {
        when(fallback.getAsDouble())
            .thenReturn(ret2);
    }

    @Test
    public void orReturnTest()
        throws Throwable
    {
        final ThrowingDoubleSupplier throwing = getThrowing();
        configureFull(throwing);

        final DoubleSupplier chain = getChain(throwing).orReturn(ret2);

        final Callable<Double> callable = toCallable(chain);

        assertThat(callable.call()).isEqualTo(ret1);

        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}
