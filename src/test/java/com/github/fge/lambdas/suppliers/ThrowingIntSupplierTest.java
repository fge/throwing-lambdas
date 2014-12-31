package com.github.fge.lambdas.suppliers;

import com.github.fge.lambdas.ThrowingInterfaceTest;

import java.util.concurrent.Callable;
import java.util.function.IntSupplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({"AutoBoxing", "OverlyBroadThrowsClause",
    "ProhibitedExceptionDeclared"})
public final class ThrowingIntSupplierTest
        extends ThrowingInterfaceTest<ThrowingIntSupplier, ThrowingIntSupplier, IntSupplier, Integer>
{
    public ThrowingIntSupplierTest()
    {
        super(SpiedThrowingIntSupplier::newSpy, () -> mock(IntSupplier.class),
            42, 24);
    }

    @Override
    protected void setupFull(final ThrowingIntSupplier instance)
        throws Throwable
    {
        when(instance.doGetAsInt()).thenReturn(ret1).thenThrow(checked)
            .thenThrow(unchecked).thenThrow(error);
    }

    @Override
    protected void setupAlternate(final ThrowingIntSupplier instance)
        throws Throwable
    {
        when(instance.doGetAsInt()).thenReturn(ret2);
    }

    @Override
    protected void setupFallback(final IntSupplier instance)
    {
        when(instance.getAsInt()).thenReturn(ret2);
    }

    @Override
    protected Callable<Integer> asCallable(final IntSupplier instance)
    {
        return instance::getAsInt;
    }

    public void testChainedWithOrReturn()
            throws Throwable
    {
        final IntSupplier instance = getFullInstance().orReturn(ret2);

        final Callable<Integer> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}
