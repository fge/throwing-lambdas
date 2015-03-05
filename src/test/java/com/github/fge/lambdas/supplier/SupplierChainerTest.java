package com.github.fge.lambdas.supplier;

import com.github.fge.lambdas.ChainerTest;
import com.github.fge.lambdas.Throwing;
import com.github.fge.lambdas.helpers.Type1;
import org.testng.annotations.Test;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class SupplierChainerTest
    extends ChainerTest<Supplier<Type1>, ThrowingSupplier<Type1>, SupplierChainer<Type1>, Type1>
{
    public SupplierChainerTest()
    {
        super(Type1.mock(), Type1.mock());
    }

    @Override
    protected Supplier<Type1> getFallback()
    {
        return mock(Supplier.class);
    }

    @Override
    protected ThrowingSupplier<Type1> getThrowing()
    {
        return mock(ThrowingSupplier.class);
    }

    @Override
    protected SupplierChainer<Type1> getChain(
        final ThrowingSupplier<Type1> throwing)
    {
        return Throwing.supplier(throwing);
    }

    @Override
    protected Callable<Type1> toCallable(final Supplier<Type1> chain)
    {
        return chain::get;
    }

    @Override
    protected void configureFull(final ThrowingSupplier<Type1> throwing)
        throws Throwable
    {
        when(throwing.doGet())
            .thenReturn(ret1)
            .thenThrow(checked)
            .thenThrow(unchecked)
            .thenThrow(error);
    }

    @Override
    protected void configureAlternate(final ThrowingSupplier<Type1> throwing)
        throws Throwable
    {
        when(throwing.doGet())
            .thenReturn(ret2);
    }

    @Override
    protected void configureFallback(final Supplier<Type1> fallback)
    {
        when(fallback.get())
            .thenReturn(ret2);
    }

    @Test
    public void orReturnTest()
        throws Throwable
    {
        final ThrowingSupplier<Type1> throwing = getThrowing();
        configureFull(throwing);

        final Supplier<Type1> chain = getChain(throwing).orReturn(ret2);

        final Callable<Type1> callable = toCallable(chain);

        assertThat(callable.call()).isEqualTo(ret1);

        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}
