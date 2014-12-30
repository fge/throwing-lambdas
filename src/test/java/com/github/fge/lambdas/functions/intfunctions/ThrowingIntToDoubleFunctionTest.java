package com.github.fge.lambdas.functions.intfunctions;

import com.github.fge.lambdas.ThrowingInterfaceBaseTest;
import com.github.fge.lambdas.ThrownByLambdaException;
import com.github.fge.lambdas.helpers.MyException;

import java.util.concurrent.Callable;
import java.util.function.IntToDoubleFunction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({ "ProhibitedExceptionDeclared", "AutoBoxing",
    "OverlyBroadThrowsClause" })
public final class ThrowingIntToDoubleFunctionTest
    extends ThrowingInterfaceBaseTest<ThrowingIntToDoubleFunction, IntToDoubleFunction, Double>
{
    private final int arg = 2;

    public ThrowingIntToDoubleFunctionTest()
    {
        super(4.0, 0.5);
    }

    @Override
    protected ThrowingIntToDoubleFunction getAlternate()
        throws Throwable
    {
        final ThrowingIntToDoubleFunction spy =
            SpiedThrowingIntToDoubleFunction.newSpy();

        when(spy.doApplyAsDouble(arg)).thenReturn(ret2);

        return spy;
    }

    @Override
    protected ThrowingIntToDoubleFunction getPreparedInstance()
        throws Throwable
    {
        final ThrowingIntToDoubleFunction spy
            = SpiedThrowingIntToDoubleFunction.newSpy();

        when(spy.doApplyAsDouble(arg)).thenReturn(ret1).thenThrow(checked)
            .thenThrow(unchecked).thenThrow(error);

        return spy;
    }

    @Override
    protected IntToDoubleFunction getFallbackInstance()
    {
        final IntToDoubleFunction mock = mock(IntToDoubleFunction.class);

        when(mock.applyAsDouble(arg)).thenReturn(ret2);

        return mock;
    }

    @Override
    protected Runnable runnableFrom(final IntToDoubleFunction instance)
    {
        return () -> instance.applyAsDouble(arg);
    }

    @Override
    protected Callable<Double> callableFrom(final IntToDoubleFunction instance)
    {
        return () -> instance.applyAsDouble(arg);
    }

    @Override
    public void testUnchained()
        throws Throwable
    {
        final ThrowingIntToDoubleFunction instance = getPreparedInstance();

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
        final IntToDoubleFunction instance
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
        final ThrowingIntToDoubleFunction first = getPreparedInstance();
        final ThrowingIntToDoubleFunction second = getAlternate();

        final IntToDoubleFunction instance = first.orTryWith(second);

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
        final ThrowingIntToDoubleFunction first = getPreparedInstance();
        final IntToDoubleFunction second = getFallbackInstance();

        final IntToDoubleFunction instance = first.fallbackTo(second);

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
        final IntToDoubleFunction instance
            = getPreparedInstance().orReturn(ret2);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Double> callable = callableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }
}
