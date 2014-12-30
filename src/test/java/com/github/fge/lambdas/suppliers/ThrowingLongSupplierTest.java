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
    private final long ret1 = 42L; // Arbitrarily random, also The Answer.
    private final long ret2 = 24L; // Opposite of The Answer.

    @Override
    protected ThrowingLongSupplier getAlternate()
    {
        return SpiedThrowingLongSupplier.newSpy();
    }

    @Override
    protected ThrowingLongSupplier getPreparedInstance()
        throws Throwable
    {
        final ThrowingLongSupplier spy = getAlternate();

        when(spy.doGetAsLong()).thenReturn(ret1).thenThrow(checked)
            .thenThrow(unchecked).thenThrow(error);

        return spy;
    }

    @Override
    protected LongSupplier getFallbackInstance()
    {
        return mock(LongSupplier.class);
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
        when(second.doGetAsLong()).thenReturn(ret2);

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
        when(second.getAsLong()).thenReturn(ret2);

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
