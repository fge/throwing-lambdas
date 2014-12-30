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
    private final double ret1 = 0.5;
    private final double ret2 = 0.25;

    @Override
    protected ThrowingDoubleSupplier getAlternate()
    {
        return SpiedThrowingDoubleSupplier.newSpy();
    }

    @Override
    protected ThrowingDoubleSupplier getPreparedInstance()
        throws Throwable
    {
        final ThrowingDoubleSupplier spy = getAlternate();

        when(spy.doGetAsDouble()).thenReturn(ret1).thenThrow(checked)
            .thenThrow(unchecked).thenThrow(error);

        return spy;
    }

    @Override
    protected DoubleSupplier getFallbackInstance()
    {
        return mock(DoubleSupplier.class);
    }

    @Override
    protected Runnable runnableFrom(final DoubleSupplier instance)
    {
        return instance::getAsDouble;
    }

    @Override
    protected Callable<Double> callableFrom(final DoubleSupplier instance)
    {
        return instance::getAsDouble;
    }

    @Override
    public void testUnchained()
        throws Throwable
    {
        final ThrowingDoubleSupplier instance = getPreparedInstance();

        final Runnable runnable = runnableFrom(instance);
        final Callable<Double> callable = callableFrom(instance);

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
            = getPreparedInstance().orThrow(MyException.class);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Double> callable = callableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);

        verifyCheckedRethrow(runnable, MyException.class);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    @Override
    public void testChainedWithOrTryWith()
        throws Throwable
    {
        final ThrowingDoubleSupplier first = getPreparedInstance();
        final ThrowingDoubleSupplier second = getAlternate();
        when(second.doGetAsDouble()).thenReturn(ret2);

        final DoubleSupplier instance = first.orTryWith(second);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Double> callable = callableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    @Override
    public void testChainedWithFallbackTo()
        throws Throwable
    {
        final ThrowingDoubleSupplier first = getPreparedInstance();
        final DoubleSupplier second = getFallbackInstance();
        when(second.getAsDouble()).thenReturn(ret2);

        final DoubleSupplier instance = first.fallbackTo(second);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Double> callable = callableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    public void testChainedWithOrReturn()
        throws Throwable
    {
        final ThrowingDoubleSupplier first = getPreparedInstance();

        final DoubleSupplier instance = first.orReturn(ret2);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Double> callable = callableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }
}
