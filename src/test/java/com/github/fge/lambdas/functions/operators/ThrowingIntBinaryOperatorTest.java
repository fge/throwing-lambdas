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
    private final int ret1 = 42;
    private final int ret2 = 625;

    @Override
    protected ThrowingIntBinaryOperator getBaseInstance()
    {
        return SpiedThrowingIntBinaryOperator.newSpy();
    }

    @Override
    protected ThrowingIntBinaryOperator getPreparedInstance()
        throws Throwable
    {
        final ThrowingIntBinaryOperator spy = getBaseInstance();

        when(spy.doApplyAsInt(left, right)).thenReturn(ret1)
            .thenThrow(checked).thenThrow(unchecked).thenThrow(error);

        return spy;
    }

    @Override
    protected IntBinaryOperator getNonThrowingInstance()
    {
        return mock(IntBinaryOperator.class);
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
        final ThrowingIntBinaryOperator second = getBaseInstance();
        when(second.doApplyAsInt(left, right)).thenReturn(ret2);

        final IntBinaryOperator instance = first.orTryWith(second);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Integer> callable = callableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    @Override
    public void testChainedWithOr()
        throws Throwable
    {
        final ThrowingIntBinaryOperator first = getPreparedInstance();
        final IntBinaryOperator second = getNonThrowingInstance();
        when(second.applyAsInt(left, right)).thenReturn(ret2);

        final IntBinaryOperator instance = first.or(second);

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
