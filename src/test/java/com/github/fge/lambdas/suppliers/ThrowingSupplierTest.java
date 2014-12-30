package com.github.fge.lambdas.suppliers;

import com.github.fge.lambdas.ThrowingInterfaceBaseTest;
import com.github.fge.lambdas.ThrownByLambdaException;
import com.github.fge.lambdas.helpers.MyException;
import com.github.fge.lambdas.helpers.Type1;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({"ProhibitedExceptionDeclared", "OverlyBroadThrowsClause"})
public final class ThrowingSupplierTest
    extends ThrowingInterfaceBaseTest<ThrowingSupplier<Type1>, Supplier<Type1>, Type1>
{
    public ThrowingSupplierTest()
    {
        super(Type1.mock(), Type1.mock());
    }

    @Override
    protected ThrowingSupplier<Type1> getAlternate()
        throws Throwable
    {
        final ThrowingSupplier<Type1> spy = SpiedThrowingSupplier.newSpy();

        when(spy.doGet()).thenReturn(ret2);

        return spy;
    }

    @Override
    protected ThrowingSupplier<Type1> getTestInstance()
        throws Throwable
    {
        final ThrowingSupplier<Type1> spy = SpiedThrowingSupplier.newSpy();

        when(spy.doGet()).thenReturn(ret1).thenThrow(checked)
            .thenThrow(unchecked).thenThrow(error);

        return spy;
    }

    @Override
    protected Supplier<Type1> getFallback()
    {
        @SuppressWarnings("unchecked")
        final Supplier<Type1> mock = mock(Supplier.class);

        when(mock.get()).thenReturn(ret2);

        return mock;
    }

    @Override
    protected Callable<Type1> asCallable(final Supplier<Type1> instance)
    {
        return instance::get;
    }

    @Override
    public void testUnchained()
        throws Throwable
    {
        final ThrowingSupplier<Type1> instance = getTestInstance();

        final Callable<Type1> callable = asCallable(instance);

        assertThat(callable.call()).isSameAs(ret1);

        verifyCheckedRethrow(callable, ThrownByLambdaException.class);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    @Override
    public void testChainedWithOrThrow()
        throws Throwable
    {
        final Supplier<Type1> instance
            = getTestInstance().orThrow(MyException.class);

        final Callable<Type1> callable = asCallable(instance);

        assertThat(callable.call()).isSameAs(ret1);

        verifyCheckedRethrow(callable, MyException.class);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    @Override
    public void testChainedWithOrTryWith()
        throws Throwable
    {
        final ThrowingSupplier<Type1> first = getTestInstance();
        final ThrowingSupplier<Type1> second = getAlternate();

        final Supplier<Type1> instance = first.orTryWith(second);

        final Callable<Type1> callable = asCallable(instance);

        assertThat(callable.call()).isSameAs(ret1);

        assertThat(callable.call()).isSameAs(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    @Override
    public void testChainedWithFallbackTo()
        throws Throwable
    {
        final ThrowingSupplier<Type1> first = getTestInstance();
        final Supplier<Type1> second = getFallback();

        final Supplier<Type1> instance = first.fallbackTo(second);

        final Callable<Type1> callable = asCallable(instance);

        assertThat(callable.call()).isSameAs(ret1);
        assertThat(callable.call()).isSameAs(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    public void testChainedWithOrReturn()
        throws Throwable
    {
        final ThrowingSupplier<Type1> first = getTestInstance();

        final Supplier<Type1> instance = first.orReturn(ret2);

        final Callable<Type1> callable = asCallable(instance);

        assertThat(callable.call()).isSameAs(ret1);
        assertThat(callable.call()).isSameAs(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}
