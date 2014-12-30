package com.github.fge.lambdas.functions.operators;

import com.github.fge.lambdas.ThrowingInterfaceBaseTest;
import com.github.fge.lambdas.ThrownByLambdaException;
import com.github.fge.lambdas.helpers.MyException;

import java.util.concurrent.Callable;
import java.util.function.DoubleBinaryOperator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({"ProhibitedExceptionDeclared", "AutoBoxing",
    "OverlyBroadThrowsClause"})
public final class ThrowingDoubleBinaryOperatorTest
    extends ThrowingInterfaceBaseTest<ThrowingDoubleBinaryOperator, DoubleBinaryOperator, Double>
{
    private final double left = 0.125;
    private final double right = 125.0;
    private final double ret1 = 2.0;
    private final double ret2 = 0.625;

    @Override
    protected ThrowingDoubleBinaryOperator getAlternate()
    {
        return SpiedThrowingDoubleBinaryOperator.newSpy();
    }

    @Override
    protected ThrowingDoubleBinaryOperator getPreparedInstance()
        throws Throwable
    {
        final ThrowingDoubleBinaryOperator spy = getAlternate();

        when(spy.doApplyAsDouble(left, right)).thenReturn(ret1)
            .thenThrow(checked).thenThrow(unchecked).thenThrow(error);

        return spy;
    }

    @Override
    protected DoubleBinaryOperator getFallbackInstance()
    {
        return mock(DoubleBinaryOperator.class);
    }

    @Override
    protected Runnable runnableFrom(final DoubleBinaryOperator instance)
    {
        return () -> instance.applyAsDouble(left, right);
    }

    @Override
    protected Callable<Double> callableFrom(final DoubleBinaryOperator instance)
    {
        return () -> instance.applyAsDouble(left, right);
    }

    @Override
    public void testUnchained()
        throws Throwable
    {
        final DoubleBinaryOperator instance = getPreparedInstance();

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
        final DoubleBinaryOperator instance
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
        final ThrowingDoubleBinaryOperator first = getPreparedInstance();
        final ThrowingDoubleBinaryOperator second = getAlternate();
        when(second.doApplyAsDouble(left, right)).thenReturn(ret2);

        final DoubleBinaryOperator instance = first.orTryWith(second);

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
        final ThrowingDoubleBinaryOperator first = getPreparedInstance();
        final DoubleBinaryOperator second = getFallbackInstance();
        when(second.applyAsDouble(left, right)).thenReturn(ret2);

        final DoubleBinaryOperator instance = first.fallbackTo(second);

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
        final DoubleBinaryOperator instance
            = getPreparedInstance().orReturn(ret2);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Double> callable = callableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    public void testChainedWithOrReturnLeft()
        throws Throwable
    {
        final DoubleBinaryOperator instance
            = getPreparedInstance().orReturnLeft();

        final Runnable runnable = runnableFrom(instance);
        final Callable<Double> callable = callableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(left);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    public void testChainedWithOrReturnRight()
        throws Throwable
    {
        final DoubleBinaryOperator instance
            = getPreparedInstance().orReturnRight();

        final Runnable runnable = runnableFrom(instance);
        final Callable<Double> callable = callableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(right);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }
}
