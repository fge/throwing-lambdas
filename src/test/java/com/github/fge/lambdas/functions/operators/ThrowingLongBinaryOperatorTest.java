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
    private final long ret1 = 2L;
    private final long ret2 = 625L;

    @Override
    protected ThrowingLongBinaryOperator getBaseInstance()
    {
        return SpiedThrowingLongBinaryOperator.newSpy();
    }

    @Override
    protected ThrowingLongBinaryOperator getPreparedInstance()
        throws Throwable
    {
        final ThrowingLongBinaryOperator spy = getBaseInstance();

        when(spy.doApplyAsLong(left, right)).thenReturn(ret1)
            .thenThrow(checked).thenThrow(unchecked).thenThrow(error);

        return spy;
    }

    @Override
    protected LongBinaryOperator getNonThrowingInstance()
    {
        return mock(LongBinaryOperator.class);
    }

    @Override
    protected Runnable runnableFrom(final LongBinaryOperator instance)
    {
        return () -> instance.applyAsLong(left, right);
    }

    @Override
    protected Callable<Long> callableFrom(final LongBinaryOperator instance)
    {
        return () -> instance.applyAsLong(left, right);
    }

    @Override
    public void testUnchained()
        throws Throwable
    {
        final LongBinaryOperator instance = getPreparedInstance();

        final Runnable runnable = runnableFrom(instance);
        final Callable<Long> callable = callableFrom(instance);

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
            = getPreparedInstance().orThrow(MyException.class);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Long> callable = callableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);

        verifyCheckedRethrow(runnable, MyException.class);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    @Override
    public void testChainedWithOrTryWith()
        throws Throwable
    {
        final ThrowingLongBinaryOperator first = getPreparedInstance();
        final ThrowingLongBinaryOperator second = getBaseInstance();
        when(second.doApplyAsLong(left, right)).thenReturn(ret2);

        final LongBinaryOperator instance = first.orTryWith(second);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Long> callable = callableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    @Override
    public void testChainedWithFallbackTo()
        throws Throwable
    {
        final ThrowingLongBinaryOperator first = getPreparedInstance();
        final LongBinaryOperator second = getNonThrowingInstance();
        when(second.applyAsLong(left, right)).thenReturn(ret2);

        final LongBinaryOperator instance = first.fallbackTo(second);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Long> callable = callableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    public void testChainedWithOrReturn()
        throws Throwable
    {
        final LongBinaryOperator instance
            = getPreparedInstance().orReturn(ret2);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Long> callable = callableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    public void testChainedWithOrReturnLeft()
        throws Throwable
    {
        final LongBinaryOperator instance
            = getPreparedInstance().orReturnLeft();

        final Runnable runnable = runnableFrom(instance);
        final Callable<Long> callable = callableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(left);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    public void testChainedWithOrReturnRight()
        throws Throwable
    {
        final LongBinaryOperator instance
            = getPreparedInstance().orReturnRight();

        final Runnable runnable = runnableFrom(instance);
        final Callable<Long> callable = callableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(right);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }
}
