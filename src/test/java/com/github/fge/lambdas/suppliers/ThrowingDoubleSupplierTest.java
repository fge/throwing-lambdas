package com.github.fge.lambdas.suppliers;

import com.github.fge.lambdas.ThrowingInterfaceTest;

import java.util.concurrent.Callable;
import java.util.function.DoubleSupplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({"OverlyBroadThrowsClause", "AutoBoxing",
    "ProhibitedExceptionDeclared"})
public final class ThrowingDoubleSupplierTest
    extends ThrowingInterfaceTest<ThrowingDoubleSupplier, ThrowingDoubleSupplier, DoubleSupplier, Double>
{
    public ThrowingDoubleSupplierTest()
    {
        super(SpiedThrowingDoubleSupplier::newSpy,
            () -> mock(DoubleSupplier.class), 0.5, 0.25);
    }

    @Override
    protected void setupFull(final ThrowingDoubleSupplier instance)
        throws Throwable
    {
        when(instance.doGetAsDouble()).thenReturn(ret1).thenThrow(checked)
            .thenThrow(unchecked).thenThrow(error);
    }

    @Override
    protected void setupAlternate(final ThrowingDoubleSupplier instance)
        throws Throwable
    {
        when(instance.doGetAsDouble()).thenReturn(ret2);
    }

    @Override
    protected void setupFallback(final DoubleSupplier instance)
    {
        when(instance.getAsDouble()).thenReturn(ret2);
    }

    @Override
    protected Callable<Double> asCallable(final DoubleSupplier instance)
    {
        return instance::getAsDouble;
    }

    public void testChainedWithOrReturn()
        throws Throwable
    {
        final DoubleSupplier instance = getFullInstance().orReturn(ret2);

        final Callable<Double> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}
