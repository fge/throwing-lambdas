package com.github.fge.lambdas.suppliers;

import com.github.fge.lambdas.ThrowingInterfaceTest;

import java.util.concurrent.Callable;
import java.util.function.LongSupplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({"ProhibitedExceptionDeclared", "AutoBoxing",
    "OverlyBroadThrowsClause"})
public final class ThrowingLongSupplierTest
    extends ThrowingInterfaceTest<ThrowingLongSupplier, ThrowingLongSupplier, LongSupplier, Long>
{
    public ThrowingLongSupplierTest()
    {
        super(SpiedThrowingLongSupplier::newSpy, () -> mock(LongSupplier.class),
            42L, 24L);
    }

    @Override
    protected void setupFull(final ThrowingLongSupplier instance)
        throws Throwable
    {
        when(instance.doGetAsLong()).thenReturn(ret1).thenThrow(checked)
            .thenThrow(unchecked).thenThrow(error);
    }

    @Override
    protected void setupAlternate(final ThrowingLongSupplier instance)
        throws Throwable
    {
        when(instance.doGetAsLong()).thenReturn(ret2);
    }

    @Override
    protected void setupFallback(final LongSupplier instance)
    {
        when(instance.getAsLong()).thenReturn(ret2);
    }

    @Override
    protected Callable<Long> asCallable(final LongSupplier instance)
    {
        return instance::getAsLong;
    }

    public void testChainedWithOrReturn()
        throws Throwable
    {
        final LongSupplier instance = getFullInstance().orReturn(ret2);

        final Callable<Long> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}
