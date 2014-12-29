package com.github.fge.lambdas.suppliers;

import com.github.fge.lambdas.ThrowingInterfaceBaseTest;
import com.github.fge.lambdas.ThrownByLambdaException;
import com.github.fge.lambdas.helpers.MyException;

import java.util.concurrent.Callable;
import java.util.function.LongSupplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class ThrowingLongSupplierTest
        extends ThrowingInterfaceBaseTest<ThrowingLongSupplier,
        LongSupplier, Long>
{

    private final Long ret1 = 42L; // Arbitrarily random, also The Answer.
    private final Long ret2 = 24L; // Opposite of The Answer.

    @Override
    protected ThrowingLongSupplier getBaseInstance()
    {
        return SpiedThrowingLongSupplier.newSpy();
    }

    @Override
    protected ThrowingLongSupplier getPreparedInstance() throws Throwable
    {
        final ThrowingLongSupplier spy = getBaseInstance();

        when(spy.doGetAsLong()).thenReturn(ret1).thenThrow(checked)
                .thenThrow(unchecked).thenThrow(error);

        return spy;
    }

    @Override
    protected LongSupplier getNonThrowingInstance()
    {
        return mock(LongSupplier.class);
    }

    @Override
    protected Runnable runnableFrom(LongSupplier instance)
    {
        return () -> instance.getAsLong();
    }

    @Override
    protected Callable<Long> callableFrom(LongSupplier instance)
    {
        return () -> instance.getAsLong();
    }

    @Override
    public void testUnchained()
            throws Throwable
    {
        final ThrowingLongSupplier instance = getPreparedInstance();

        final Runnable runnable = runnableFrom(instance);
        final Callable<Long> callable = callableFrom(instance);

        assertThat(callable.call()).isSameAs(ret1);

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

        assertThat(callable.call()).isSameAs(ret1);

        verifyCheckedRethrow(runnable, MyException.class);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    @Override
    public void testChainedWithOrTryWith()
            throws Throwable
    {
        final ThrowingLongSupplier first = getPreparedInstance();
        final ThrowingLongSupplier second = getBaseInstance();
        when(second.doGetAsLong()).thenReturn(ret2);

        final LongSupplier instance = first.orTryWith(second);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Long> callable = callableFrom(instance);

        assertThat(callable.call()).isSameAs(ret1);

        assertThat(callable.call()).isSameAs(ret2);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    @Override
    public void testChainedWithOr()
            throws Throwable
    {
        final ThrowingLongSupplier first = getPreparedInstance();
        final LongSupplier second = getNonThrowingInstance();
        when(second.getAsLong()).thenReturn(ret2);

        final LongSupplier instance = first.or(second);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Long> callable = callableFrom(instance);

        assertThat(callable.call()).isSameAs(ret1);
        assertThat(callable.call()).isSameAs(ret2);

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

        assertThat(callable.call()).isSameAs(ret1);
        assertThat(callable.call()).isSameAs(ret2);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }
}
