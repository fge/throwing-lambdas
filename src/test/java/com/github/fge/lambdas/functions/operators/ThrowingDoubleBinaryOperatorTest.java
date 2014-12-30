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

    public ThrowingDoubleBinaryOperatorTest()
    {
        super(2.0, 0.625);
    }

    @Override
    protected ThrowingDoubleBinaryOperator getAlternate()
        throws Throwable
    {
        final ThrowingDoubleBinaryOperator spy =
            SpiedThrowingDoubleBinaryOperator.newSpy();

        when(spy.doApplyAsDouble(left, right)).thenReturn(ret2);

        return spy;
    }

    @Override
    protected ThrowingDoubleBinaryOperator getTestInstance()
        throws Throwable
    {
        final ThrowingDoubleBinaryOperator spy
            = SpiedThrowingDoubleBinaryOperator.newSpy();

        when(spy.doApplyAsDouble(left, right)).thenReturn(ret1)
            .thenThrow(checked).thenThrow(unchecked).thenThrow(error);

        return spy;
    }

    @Override
    protected DoubleBinaryOperator getFallback()
    {
        final DoubleBinaryOperator mock = mock(DoubleBinaryOperator.class);

        when(mock.applyAsDouble(left, right)).thenReturn(ret2);

        return mock;
    }

    @Override
    protected Callable<Double> asCallable(final DoubleBinaryOperator instance)
    {
        return () -> instance.applyAsDouble(left, right);
    }

    @Override
    public void testUnchained()
        throws Throwable
    {
        final DoubleBinaryOperator instance = getTestInstance();

        final Callable<Double> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);

        verifyCheckedRethrow(callable, ThrownByLambdaException.class);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    @Override
    public void testChainedWithOrThrow()
        throws Throwable
    {
        final DoubleBinaryOperator instance
            = getTestInstance().orThrow(MyException.class);

        final Callable<Double> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);

        verifyCheckedRethrow(callable, MyException.class);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    @Override
    public void testChainedWithOrTryWith()
        throws Throwable
    {
        final ThrowingDoubleBinaryOperator first = getTestInstance();
        final ThrowingDoubleBinaryOperator second = getAlternate();

        final DoubleBinaryOperator instance = first.orTryWith(second);

        final Callable<Double> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    @Override
    public void testChainedWithFallbackTo()
        throws Throwable
    {
        final ThrowingDoubleBinaryOperator first = getTestInstance();
        final DoubleBinaryOperator second = getFallback();

        final DoubleBinaryOperator instance = first.fallbackTo(second);

        final Callable<Double> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    public void testChainedWithOrReturn()
        throws Throwable
    {
        final DoubleBinaryOperator instance
            = getTestInstance().orReturn(ret2);

        final Callable<Double> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    public void testChainedWithOrReturnLeft()
        throws Throwable
    {
        final DoubleBinaryOperator instance
            = getTestInstance().orReturnLeft();

        final Callable<Double> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(left);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    public void testChainedWithOrReturnRight()
        throws Throwable
    {
        final DoubleBinaryOperator instance
            = getTestInstance().orReturnRight();

        final Callable<Double> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(right);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}
