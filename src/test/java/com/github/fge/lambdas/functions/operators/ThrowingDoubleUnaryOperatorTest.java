package com.github.fge.lambdas.functions.operators;

import com.github.fge.lambdas.ThrowingInterfaceBaseTest;
import com.github.fge.lambdas.ThrownByLambdaException;
import com.github.fge.lambdas.helpers.MyException;

import java.util.concurrent.Callable;
import java.util.function.DoubleUnaryOperator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({"ProhibitedExceptionDeclared", "AutoBoxing",
    "OverlyBroadThrowsClause"})
public final class ThrowingDoubleUnaryOperatorTest
    extends ThrowingInterfaceBaseTest<ThrowingDoubleUnaryOperator, DoubleUnaryOperator, Double>
{
    private final double arg = 1.0;
    private final double ret1 = 0.5;
    private final double ret2 = 2.0;

    @Override
    protected ThrowingDoubleUnaryOperator getBaseInstance()
    {
        return SpiedThrowingDoubleUnaryOperator.newSpy();
    }

    @Override
    protected ThrowingDoubleUnaryOperator getPreparedInstance()
        throws Throwable
    {
        final ThrowingDoubleUnaryOperator spy = getBaseInstance();

        when(spy.doApplyAsDouble(arg)).thenReturn(ret1).thenThrow(checked)
            .thenThrow(unchecked).thenThrow(error);

        return spy;
    }

    @Override
    protected DoubleUnaryOperator getNonThrowingInstance()
    {
        return mock(ThrowingDoubleUnaryOperator.class);
    }

    @Override
    protected Runnable runnableFrom(final DoubleUnaryOperator instance)
    {
        return () -> instance.applyAsDouble(arg);
    }

    @Override
    protected Callable<Double> callableFrom(final DoubleUnaryOperator instance)
    {
        return () -> instance.applyAsDouble(arg);
    }

    @Override
    public void testUnchained()
        throws Throwable
    {
        final DoubleUnaryOperator instance = getPreparedInstance();

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
        final DoubleUnaryOperator instance
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
        final ThrowingDoubleUnaryOperator first = getPreparedInstance();
        final ThrowingDoubleUnaryOperator second = getBaseInstance();
        when(second.doApplyAsDouble(arg)).thenReturn(ret2);

        final DoubleUnaryOperator instance = first.orTryWith(second);

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
        final ThrowingDoubleUnaryOperator first = getPreparedInstance();
        final DoubleUnaryOperator second = getNonThrowingInstance();
        when(second.applyAsDouble(arg)).thenReturn(ret2);

        final DoubleUnaryOperator instance = first.fallbackTo(second);

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
        final DoubleUnaryOperator instance
            = getPreparedInstance().orReturn(ret2);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Double> callable = callableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    public void testChainedWithOrReturnSelf()
        throws Throwable
    {
        final DoubleUnaryOperator instance
            = getPreparedInstance().orReturnSelf();

        final Runnable runnable = runnableFrom(instance);
        final Callable<Double> callable = callableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(arg);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }
}
