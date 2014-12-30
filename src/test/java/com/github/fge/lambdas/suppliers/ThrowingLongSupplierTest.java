package com.github.fge.lambdas.suppliers;

import com.github.fge.lambdas.ThrowingInterfaceBaseTest;
import com.github.fge.lambdas.ThrownByLambdaException;
import com.github.fge.lambdas.helpers.MyException;

import java.util.concurrent.Callable;
import java.util.function.LongSupplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({"ProhibitedExceptionDeclared", "AutoBoxing",
    "OverlyBroadThrowsClause"})
public final class ThrowingLongSupplierTest
    extends ThrowingInterfaceBaseTest<ThrowingLongSupplier, LongSupplier, Long>
{
    public ThrowingLongSupplierTest()
    {
        super(42L, 24L);
    }

    @Override
    protected ThrowingLongSupplier getAlternate()
        throws Throwable
    {
        final ThrowingLongSupplier spy = SpiedThrowingLongSupplier.newSpy();

        when(spy.doGetAsLong()).thenReturn(ret2);

        return spy;
    }

    @Override
    protected ThrowingLongSupplier getTestInstance()
        throws Throwable
    {
        final ThrowingLongSupplier spy
            = SpiedThrowingLongSupplier.newSpy();

        when(spy.doGetAsLong()).thenReturn(ret1).thenThrow(checked)
            .thenThrow(unchecked).thenThrow(error);

        return spy;
    }

    @Override
    protected LongSupplier getFallback()
    {
        final LongSupplier mock = mock(LongSupplier.class);

        when(mock.getAsLong()).thenReturn(ret2);

        return mock;
    }

    @Override
    protected Callable<Long> asCallable(final LongSupplier instance)
    {
        return instance::getAsLong;
    }

    @Override
    public void testUnchained()
        throws Throwable
    {
        final ThrowingLongSupplier instance = getTestInstance();

        final Callable<Long> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);

        verifyCheckedRethrow(callable, ThrownByLambdaException.class);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    @Override
    public void testChainedWithOrThrow()
        throws Throwable
    {
        final LongSupplier instance
            = getTestInstance().orThrow(MyException.class);

        final Callable<Long> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);

        verifyCheckedRethrow(callable, MyException.class);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    @Override
    public void testChainedWithOrTryWith()
        throws Throwable
    {
        final ThrowingLongSupplier first = getTestInstance();
        final ThrowingLongSupplier second = getAlternate();

        final LongSupplier instance = first.orTryWith(second);

        final Callable<Long> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    @Override
    public void testChainedWithFallbackTo()
        throws Throwable
    {
        final ThrowingLongSupplier first = getTestInstance();
        final LongSupplier second = getFallback();

        final LongSupplier instance = first.fallbackTo(second);

        final Callable<Long> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    public void testChainedWithOrReturn()
        throws Throwable
    {
        final ThrowingLongSupplier first = getTestInstance();

        final LongSupplier instance = first.orReturn(ret2);

        final Callable<Long> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}
