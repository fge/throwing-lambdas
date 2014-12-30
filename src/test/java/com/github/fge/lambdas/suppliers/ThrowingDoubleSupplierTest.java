package com.github.fge.lambdas.suppliers;

import com.github.fge.lambdas.ThrowingInterfaceBaseTest;
import com.github.fge.lambdas.ThrownByLambdaException;
import com.github.fge.lambdas.helpers.MyException;

import java.util.concurrent.Callable;
import java.util.function.DoubleSupplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({"OverlyBroadThrowsClause", "AutoBoxing",
    "ProhibitedExceptionDeclared"})
public final class ThrowingDoubleSupplierTest
    extends ThrowingInterfaceBaseTest<ThrowingDoubleSupplier, DoubleSupplier, Double>
{
    public ThrowingDoubleSupplierTest()
    {
        super(0.5, 0.25);
    }

    @Override
    protected ThrowingDoubleSupplier getAlternate()
        throws Throwable
    {
        final ThrowingDoubleSupplier spy =
            SpiedThrowingDoubleSupplier.newSpy();

        when(spy.doGetAsDouble()).thenReturn(ret2);

        return spy;
    }

    @Override
    protected ThrowingDoubleSupplier getTestInstance()
        throws Throwable
    {
        final ThrowingDoubleSupplier spy
            = SpiedThrowingDoubleSupplier.newSpy();

        when(spy.doGetAsDouble()).thenReturn(ret1).thenThrow(checked)
            .thenThrow(unchecked).thenThrow(error);

        return spy;
    }

    @Override
    protected DoubleSupplier getFallback()
    {
        final DoubleSupplier mock = mock(DoubleSupplier.class);

        when(mock.getAsDouble()).thenReturn(ret2);

        return mock;
    }

    @Override
    protected Runnable runnableFrom(final DoubleSupplier instance)
    {
        return instance::getAsDouble;
    }

    @Override
    protected Callable<Double> asCallable(final DoubleSupplier instance)
    {
        return instance::getAsDouble;
    }

    @Override
    public void testUnchained()
        throws Throwable
    {
        final ThrowingDoubleSupplier instance = getTestInstance();

        final Runnable runnable = runnableFrom(instance);
        final Callable<Double> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);

        verifyCheckedRethrow(runnable, ThrownByLambdaException.class);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    @Override
    public void testChainedWithOrThrow()
        throws Throwable
    {
        final DoubleSupplier instance
            = getTestInstance().orThrow(MyException.class);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Double> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);

        verifyCheckedRethrow(runnable, MyException.class);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    @Override
    public void testChainedWithOrTryWith()
        throws Throwable
    {
        final ThrowingDoubleSupplier first = getTestInstance();
        final ThrowingDoubleSupplier second = getAlternate();

        final DoubleSupplier instance = first.orTryWith(second);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Double> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    @Override
    public void testChainedWithFallbackTo()
        throws Throwable
    {
        final ThrowingDoubleSupplier first = getTestInstance();
        final DoubleSupplier second = getFallback();

        final DoubleSupplier instance = first.fallbackTo(second);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Double> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    public void testChainedWithOrReturn()
        throws Throwable
    {
        final ThrowingDoubleSupplier first = getTestInstance();

        final DoubleSupplier instance = first.orReturn(ret2);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Double> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }
}
