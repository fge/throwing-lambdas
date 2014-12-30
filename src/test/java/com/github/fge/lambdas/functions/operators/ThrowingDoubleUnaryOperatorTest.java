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

    public ThrowingDoubleUnaryOperatorTest()
    {
        super(0.5, 2.0);
    }

    @Override
    protected ThrowingDoubleUnaryOperator getAlternate()
        throws Throwable
    {
        final ThrowingDoubleUnaryOperator spy =
            SpiedThrowingDoubleUnaryOperator.newSpy();

        when(spy.doApplyAsDouble(arg)).thenReturn(ret2);

        return spy;
    }

    @Override
    protected ThrowingDoubleUnaryOperator getTestInstance()
        throws Throwable
    {
        final ThrowingDoubleUnaryOperator spy
            = SpiedThrowingDoubleUnaryOperator.newSpy();

        when(spy.doApplyAsDouble(arg)).thenReturn(ret1).thenThrow(checked)
            .thenThrow(unchecked).thenThrow(error);

        return spy;
    }

    @Override
    protected DoubleUnaryOperator getFallback()
    {
        final DoubleUnaryOperator mock = mock(DoubleUnaryOperator.class);

        when(mock.applyAsDouble(arg)).thenReturn(ret2);

        return mock;
    }

    @Override
    protected Callable<Double> asCallable(final DoubleUnaryOperator instance)
    {
        return () -> instance.applyAsDouble(arg);
    }

    @Override
    public void testUnchained()
        throws Throwable
    {
        final DoubleUnaryOperator instance = getTestInstance();

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
        final DoubleUnaryOperator instance
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
        final ThrowingDoubleUnaryOperator first = getTestInstance();
        final ThrowingDoubleUnaryOperator second = getAlternate();

        final DoubleUnaryOperator instance = first.orTryWith(second);

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
        final ThrowingDoubleUnaryOperator first = getTestInstance();
        final DoubleUnaryOperator second = getFallback();

        final DoubleUnaryOperator instance = first.fallbackTo(second);

        final Callable<Double> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    public void testChainedWithOrReturn()
        throws Throwable
    {
        final DoubleUnaryOperator instance
            = getTestInstance().orReturn(ret2);

        final Callable<Double> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    public void testChainedWithOrReturnSelf()
        throws Throwable
    {
        final DoubleUnaryOperator instance
            = getTestInstance().orReturnSelf();

        final Callable<Double> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(arg);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}
