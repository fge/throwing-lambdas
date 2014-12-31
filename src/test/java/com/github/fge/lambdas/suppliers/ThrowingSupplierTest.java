package com.github.fge.lambdas.suppliers;

import com.github.fge.lambdas.ThrowingInterfaceTest;
import com.github.fge.lambdas.helpers.Type1;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({"ProhibitedExceptionDeclared", "OverlyBroadThrowsClause"})
public final class ThrowingSupplierTest
    extends ThrowingInterfaceTest<ThrowingSupplier<Type1>, ThrowingSupplier<Type1>, Supplier<Type1>, Type1>
{
    public ThrowingSupplierTest()
    {
        super(SpiedThrowingSupplier::newSpy, () -> mock(Supplier.class),
            Type1.mock(), Type1.mock());
    }

    @Override
    protected void setupFull(final ThrowingSupplier<Type1> instance)
        throws Throwable
    {
        when(instance.doGet()).thenReturn(ret1).thenThrow(checked)
            .thenThrow(unchecked).thenThrow(error);
    }

    @Override
    protected void setupAlternate(final ThrowingSupplier<Type1> instance)
        throws Throwable
    {
        when(instance.doGet()).thenReturn(ret2);
    }

    @Override
    protected void setupFallback(final Supplier<Type1> instance)
    {
        when(instance.get()).thenReturn(ret2);
    }

    @Override
    protected Callable<Type1> asCallable(final Supplier<Type1> instance)
    {
        return instance::get;
    }

    public void testChainedWithOrReturn()
        throws Throwable
    {
        final Supplier<Type1> instance = getFullInstance().orReturn(ret2);

        final Callable<Type1> callable = asCallable(instance);

        assertThat(callable.call()).isSameAs(ret1);
        assertThat(callable.call()).isSameAs(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}
