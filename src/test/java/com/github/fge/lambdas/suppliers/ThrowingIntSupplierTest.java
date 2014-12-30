package com.github.fge.lambdas.suppliers;

import com.github.fge.lambdas.ThrowingInterfaceBaseTest;
import com.github.fge.lambdas.ThrownByLambdaException;
import com.github.fge.lambdas.helpers.MyException;

import java.util.concurrent.Callable;
import java.util.function.IntSupplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({"AutoBoxing", "OverlyBroadThrowsClause",
    "ProhibitedExceptionDeclared"})
public final class ThrowingIntSupplierTest
        extends ThrowingInterfaceBaseTest<ThrowingIntSupplier, IntSupplier, Integer>
{
    public ThrowingIntSupplierTest()
    {
        super(42, 24);
    }

    @Override
    protected ThrowingIntSupplier getAlternate()
        throws Throwable
    {
        final ThrowingIntSupplier spy = SpiedThrowingIntSupplier.newSpy();

        when(spy.doGetAsInt()).thenReturn(ret2);
        return spy;
    }

    @Override
    protected ThrowingIntSupplier getTestInstance()
        throws Throwable
    {
        final ThrowingIntSupplier spy
            = SpiedThrowingIntSupplier.newSpy();

        when(spy.doGetAsInt()).thenReturn(ret1).thenThrow(checked)
            .thenThrow(unchecked).thenThrow(error);

        return spy;
    }

    @Override
    protected IntSupplier getFallback()
    {
        final IntSupplier mock = mock(IntSupplier.class);

        when(mock.getAsInt()).thenReturn(ret2);

        return mock;
    }

    @Override
    protected Runnable runnableFrom(final IntSupplier instance)
    {
        return instance::getAsInt;
    }

    @Override
    protected Callable<Integer> asCallable(final IntSupplier instance)
    {
        return instance::getAsInt;
    }

    @Override
    public void testUnchained()
            throws Throwable
    {
        final ThrowingIntSupplier instance = getTestInstance();

        final Runnable runnable = runnableFrom(instance);
        final Callable<Integer> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);

        verifyCheckedRethrow(runnable, ThrownByLambdaException.class);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    @Override
    public void testChainedWithOrThrow()
            throws Throwable
    {
        final IntSupplier instance
                = getTestInstance().orThrow(MyException.class);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Integer> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);

        verifyCheckedRethrow(runnable, MyException.class);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    @Override
    public void testChainedWithOrTryWith()
            throws Throwable
    {
        final ThrowingIntSupplier first = getTestInstance();
        final ThrowingIntSupplier second = getAlternate();

        final IntSupplier instance = first.orTryWith(second);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Integer> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);

        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    @Override
    public void testChainedWithFallbackTo()
            throws Throwable
    {
        final ThrowingIntSupplier first = getTestInstance();
        final IntSupplier second = getFallback();

        final IntSupplier instance = first.fallbackTo(second);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Integer> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    public void testChainedWithOrReturn()
            throws Throwable
    {
        final ThrowingIntSupplier first = getTestInstance();

        final IntSupplier instance = first.orReturn(ret2);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Integer> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }
}
