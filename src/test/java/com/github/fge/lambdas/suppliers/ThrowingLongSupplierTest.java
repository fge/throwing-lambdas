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
    protected ThrowingLongSupplier getPreparedInstance()
        throws Throwable
    {
        final ThrowingLongSupplier spy
            = SpiedThrowingLongSupplier.newSpy();

        when(spy.doGetAsLong()).thenReturn(ret1).thenThrow(checked)
            .thenThrow(unchecked).thenThrow(error);

        return spy;
    }

    @Override
    protected LongSupplier getFallbackInstance()
    {
        final LongSupplier mock = mock(LongSupplier.class);

        when(mock.getAsLong()).thenReturn(ret2);

        return mock;
    }

    @Override
    protected Runnable runnableFrom(final LongSupplier instance)
    {
        return instance::getAsLong;
    }

    @Override
    protected Callable<Long> callableFrom(final LongSupplier instance)
    {
        return instance::getAsLong;
    }

    @Override
    public void testUnchained()
        throws Throwable
    {
        final ThrowingLongSupplier instance = getPreparedInstance();

        final Runnable runnable = runnableFrom(instance);
        final Callable<Long> callable = callableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);

        verifyCheckedRethrow(runnable, ThrownByLambdaException.class);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    @Override
    public void testChainedWithOrThrow()
        throws Throwable
    {
        final LongSupplier instance
            = getPreparedInstance().orThrow(MyException.class);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Long> callable = callableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);

        verifyCheckedRethrow(runnable, MyException.class);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    @Override
    public void testChainedWithOrTryWith()
        throws Throwable
    {
        final ThrowingLongSupplier first = getPreparedInstance();
        final ThrowingLongSupplier second = getAlternate();

        final LongSupplier instance = first.orTryWith(second);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Long> callable = callableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    @Override
    public void testChainedWithFallbackTo()
        throws Throwable
    {
        final ThrowingLongSupplier first = getPreparedInstance();
        final LongSupplier second = getFallbackInstance();

        final LongSupplier instance = first.fallbackTo(second);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Long> callable = callableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    public void testChainedWithOrReturn()
        throws Throwable
    {
        final ThrowingLongSupplier first = getPreparedInstance();

        final LongSupplier instance = first.orReturn(ret2);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Long> callable = callableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }
}
