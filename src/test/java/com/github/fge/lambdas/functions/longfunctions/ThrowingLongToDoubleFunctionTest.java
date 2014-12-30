package com.github.fge.lambdas.functions.longfunctions;

import com.github.fge.lambdas.ThrowingInterfaceBaseTest;
import com.github.fge.lambdas.ThrownByLambdaException;
import com.github.fge.lambdas.helpers.MyException;

import java.util.concurrent.Callable;
import java.util.function.LongToDoubleFunction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({ "ProhibitedExceptionDeclared", "AutoBoxing",
    "OverlyBroadThrowsClause" })
public final class ThrowingLongToDoubleFunctionTest
    extends ThrowingInterfaceBaseTest<ThrowingLongToDoubleFunction, LongToDoubleFunction, Double>
{
    private final long arg = 2223L;

    public ThrowingLongToDoubleFunctionTest()
    {
        super(4.0, 0.5);
    }

    @Override
    protected ThrowingLongToDoubleFunction getAlternate()
        throws Throwable
    {
        final ThrowingLongToDoubleFunction spy =
            SpiedThrowingLongToDoubleFunction.newSpy();

        when(spy.doApplyAsDouble(arg)).thenReturn(ret2);

        return spy;
    }

    @Override
    protected ThrowingLongToDoubleFunction getPreparedInstance()
        throws Throwable
    {
        final ThrowingLongToDoubleFunction spy
            = SpiedThrowingLongToDoubleFunction.newSpy();

        when(spy.doApplyAsDouble(arg)).thenReturn(ret1).thenThrow(checked)
            .thenThrow(unchecked).thenThrow(error);

        return spy;
    }

    @Override
    protected LongToDoubleFunction getFallbackInstance()
    {
        final LongToDoubleFunction mock = mock(LongToDoubleFunction.class);

        when(mock.applyAsDouble(arg)).thenReturn(ret2);

        return mock;
    }

    @Override
    protected Runnable runnableFrom(final LongToDoubleFunction instance)
    {
        return () -> instance.applyAsDouble(arg);
    }

    @Override
    protected Callable<Double> callableFrom(final LongToDoubleFunction instance)
    {
        return () -> instance.applyAsDouble(arg);
    }

    @Override
    public void testUnchained()
        throws Throwable
    {
        final ThrowingLongToDoubleFunction instance = getPreparedInstance();

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
        final LongToDoubleFunction instance
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
        final ThrowingLongToDoubleFunction first = getPreparedInstance();
        final ThrowingLongToDoubleFunction second = getAlternate();

        final LongToDoubleFunction instance = first.orTryWith(second);

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
        final ThrowingLongToDoubleFunction first = getPreparedInstance();
        final LongToDoubleFunction second = getFallbackInstance();

        final LongToDoubleFunction instance = first.fallbackTo(second);

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
        final LongToDoubleFunction instance
            = getPreparedInstance().orReturn(ret2);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Double> callable = callableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }
}
