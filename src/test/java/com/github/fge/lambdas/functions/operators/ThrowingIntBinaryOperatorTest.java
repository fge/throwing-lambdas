package com.github.fge.lambdas.functions.operators;

import com.github.fge.lambdas.ThrowingInterfaceBaseTest;
import com.github.fge.lambdas.ThrownByLambdaException;
import com.github.fge.lambdas.helpers.MyException;

import java.util.concurrent.Callable;
import java.util.function.IntBinaryOperator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({"ProhibitedExceptionDeclared", "AutoBoxing",
    "OverlyBroadThrowsClause"})
public final class ThrowingIntBinaryOperatorTest
    extends ThrowingInterfaceBaseTest<ThrowingIntBinaryOperator, IntBinaryOperator, Integer>
{
    private final int left = 125;
    private final int right = 2125;

    public ThrowingIntBinaryOperatorTest()
    {
        super(42, 625);
    }

    @Override
    protected ThrowingIntBinaryOperator getAlternate()
        throws Throwable
    {
        final ThrowingIntBinaryOperator spy =
            SpiedThrowingIntBinaryOperator.newSpy();

        when(spy.doApplyAsInt(left, right)).thenReturn(ret2);

        return spy;
    }

    @Override
    protected ThrowingIntBinaryOperator getPreparedInstance()
        throws Throwable
    {
        final ThrowingIntBinaryOperator spy
            = SpiedThrowingIntBinaryOperator.newSpy();

        when(spy.doApplyAsInt(left, right)).thenReturn(ret1)
            .thenThrow(checked).thenThrow(unchecked).thenThrow(error);

        return spy;
    }

    @Override
    protected IntBinaryOperator getFallbackInstance()
    {
        final IntBinaryOperator mock = mock(IntBinaryOperator.class);

        when(mock.applyAsInt(left, right)).thenReturn(ret2);

        return mock;
    }

    @Override
    protected Runnable runnableFrom(final IntBinaryOperator instance)
    {
        return () -> instance.applyAsInt(left, right);
    }

    @Override
    protected Callable<Integer> callableFrom(final IntBinaryOperator instance)
    {
        return () -> instance.applyAsInt(left, right);
    }

    @Override
    public void testUnchained()
        throws Throwable
    {
        final IntBinaryOperator instance = getPreparedInstance();

        final Runnable runnable = runnableFrom(instance);
        final Callable<Integer> callable = callableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);

        verifyCheckedRethrow(runnable, ThrownByLambdaException.class);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    @Override
    public void testChainedWithOrThrow()
        throws Throwable
    {
        final IntBinaryOperator instance
            = getPreparedInstance().orThrow(MyException.class);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Integer> callable = callableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);

        verifyCheckedRethrow(runnable, MyException.class);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    @Override
    public void testChainedWithOrTryWith()
        throws Throwable
    {
        final ThrowingIntBinaryOperator first = getPreparedInstance();
        final ThrowingIntBinaryOperator second = getAlternate();

        final IntBinaryOperator instance = first.orTryWith(second);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Integer> callable = callableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    @Override
    public void testChainedWithFallbackTo()
        throws Throwable
    {
        final ThrowingIntBinaryOperator first = getPreparedInstance();
        final IntBinaryOperator second = getFallbackInstance();

        final IntBinaryOperator instance = first.fallbackTo(second);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Integer> callable = callableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    public void testChainedWithOrReturn()
        throws Throwable
    {
        final IntBinaryOperator instance
            = getPreparedInstance().orReturn(ret2);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Integer> callable = callableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    public void testChainedWithOrReturnLeft()
        throws Throwable
    {
        final IntBinaryOperator instance
            = getPreparedInstance().orReturnLeft();

        final Runnable runnable = runnableFrom(instance);
        final Callable<Integer> callable = callableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(left);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    public void testChainedWithOrReturnRight()
        throws Throwable
    {
        final IntBinaryOperator instance
            = getPreparedInstance().orReturnRight();

        final Runnable runnable = runnableFrom(instance);
        final Callable<Integer> callable = callableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(right);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }
}
