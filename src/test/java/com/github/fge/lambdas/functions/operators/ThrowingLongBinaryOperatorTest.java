package com.github.fge.lambdas.functions.operators;

import com.github.fge.lambdas.ThrowingInterfaceBaseTest;
import com.github.fge.lambdas.ThrownByLambdaException;
import com.github.fge.lambdas.helpers.MyException;

import java.util.concurrent.Callable;
import java.util.function.LongBinaryOperator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({"ProhibitedExceptionDeclared", "AutoBoxing",
    "OverlyBroadThrowsClause"})
public final class ThrowingLongBinaryOperatorTest
    extends ThrowingInterfaceBaseTest<ThrowingLongBinaryOperator, LongBinaryOperator, Long>
{
    private final long left = 398098L;
    private final long right = 125L;

    public ThrowingLongBinaryOperatorTest()
    {
        super(2L, 625L);
    }

    @Override
    protected ThrowingLongBinaryOperator getAlternate()
        throws Throwable
    {
        final ThrowingLongBinaryOperator spy =
            SpiedThrowingLongBinaryOperator.newSpy();

        when(spy.doApplyAsLong(left, right)).thenReturn(ret2);

        return spy;
    }

    @Override
    protected ThrowingLongBinaryOperator getTestInstance()
        throws Throwable
    {
        final ThrowingLongBinaryOperator spy
            = SpiedThrowingLongBinaryOperator.newSpy();

        when(spy.doApplyAsLong(left, right)).thenReturn(ret1)
            .thenThrow(checked).thenThrow(unchecked).thenThrow(error);

        return spy;
    }

    @Override
    protected LongBinaryOperator getFallback()
    {
        final LongBinaryOperator mock = mock(LongBinaryOperator.class);

        when(mock.applyAsLong(left, right)).thenReturn(ret2);

        return mock;
    }

    @Override
    protected Runnable runnableFrom(final LongBinaryOperator instance)
    {
        return () -> instance.applyAsLong(left, right);
    }

    @Override
    protected Callable<Long> asCallable(final LongBinaryOperator instance)
    {
        return () -> instance.applyAsLong(left, right);
    }

    @Override
    public void testUnchained()
        throws Throwable
    {
        final LongBinaryOperator instance = getTestInstance();

        final Runnable runnable = runnableFrom(instance);
        final Callable<Long> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);

        verifyCheckedRethrow(runnable, ThrownByLambdaException.class);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    @Override
    public void testChainedWithOrThrow()
        throws Throwable
    {
        final LongBinaryOperator instance
            = getTestInstance().orThrow(MyException.class);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Long> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);

        verifyCheckedRethrow(runnable, MyException.class);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    @Override
    public void testChainedWithOrTryWith()
        throws Throwable
    {
        final ThrowingLongBinaryOperator first = getTestInstance();
        final ThrowingLongBinaryOperator second = getAlternate();

        final LongBinaryOperator instance = first.orTryWith(second);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Long> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    @Override
    public void testChainedWithFallbackTo()
        throws Throwable
    {
        final ThrowingLongBinaryOperator first = getTestInstance();
        final LongBinaryOperator second = getFallback();

        final LongBinaryOperator instance = first.fallbackTo(second);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Long> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    public void testChainedWithOrReturn()
        throws Throwable
    {
        final LongBinaryOperator instance
            = getTestInstance().orReturn(ret2);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Long> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    public void testChainedWithOrReturnLeft()
        throws Throwable
    {
        final LongBinaryOperator instance
            = getTestInstance().orReturnLeft();

        final Runnable runnable = runnableFrom(instance);
        final Callable<Long> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(left);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    public void testChainedWithOrReturnRight()
        throws Throwable
    {
        final LongBinaryOperator instance
            = getTestInstance().orReturnRight();

        final Runnable runnable = runnableFrom(instance);
        final Callable<Long> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(right);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }
}
