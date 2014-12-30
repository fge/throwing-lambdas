package com.github.fge.lambdas.functions.operators;

import com.github.fge.lambdas.ThrowingInterfaceBaseTest;
import com.github.fge.lambdas.ThrownByLambdaException;
import com.github.fge.lambdas.helpers.MyException;

import java.util.concurrent.Callable;
import java.util.function.IntUnaryOperator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({"ProhibitedExceptionDeclared", "AutoBoxing",
    "OverlyBroadThrowsClause"})
public final class ThrowingIntUnaryOperatorTest
    extends ThrowingInterfaceBaseTest<ThrowingIntUnaryOperator, IntUnaryOperator, Integer>
{
    private final int arg = 1;

    public ThrowingIntUnaryOperatorTest()
    {
        super(5, 20);
    }

    @Override
    protected ThrowingIntUnaryOperator getAlternate()
        throws Throwable
    {
        final ThrowingIntUnaryOperator spy =
            SpiedThrowingIntUnaryOperator.newSpy();

        when(spy.doApplyAsInt(arg)).thenReturn(ret2);

        return spy;
    }

    @Override
    protected ThrowingIntUnaryOperator getTestInstance()
        throws Throwable
    {
        final ThrowingIntUnaryOperator spy
            = SpiedThrowingIntUnaryOperator.newSpy();

        when(spy.doApplyAsInt(arg)).thenReturn(ret1).thenThrow(checked)
            .thenThrow(unchecked).thenThrow(error);

        return spy;
    }

    @Override
    protected IntUnaryOperator getFallback()
    {
        final IntUnaryOperator mock = mock(IntUnaryOperator.class);

        when(mock.applyAsInt(arg)).thenReturn(ret2);

        return mock;
    }

    @Override
    protected Runnable runnableFrom(final IntUnaryOperator instance)
    {
        return () -> instance.applyAsInt(arg);
    }

    @Override
    protected Callable<Integer> asCallable(final IntUnaryOperator instance)
    {
        return () -> instance.applyAsInt(arg);
    }

    @Override
    public void testUnchained()
        throws Throwable
    {
        final IntUnaryOperator instance = getTestInstance();

        final Runnable runnable = runnableFrom(instance);
        final Callable<Integer> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);

        verifyCheckedRethrow(runnable, ThrownByLambdaException.class);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    @Override
    public void testChainedWithOrThrow()
        throws Throwable
    {
        final IntUnaryOperator instance
            = getTestInstance().orThrow(MyException.class);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Integer> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);

        verifyCheckedRethrow(runnable, MyException.class);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    @Override
    public void testChainedWithOrTryWith()
        throws Throwable
    {
        final ThrowingIntUnaryOperator first = getTestInstance();
        final ThrowingIntUnaryOperator second = getAlternate();

        final IntUnaryOperator instance = first.orTryWith(second);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Integer> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    @Override
    public void testChainedWithFallbackTo()
        throws Throwable
    {
        final ThrowingIntUnaryOperator first = getTestInstance();
        final IntUnaryOperator second = getFallback();

        final IntUnaryOperator instance = first.fallbackTo(second);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Integer> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    public void testChainedWithOrReturn()
        throws Throwable
    {
        final IntUnaryOperator instance
            = getTestInstance().orReturn(ret2);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Integer> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    public void testChainedWithOrReturnSelf()
        throws Throwable
    {
        final IntUnaryOperator instance
            = getTestInstance().orReturnSelf();

        final Runnable runnable = runnableFrom(instance);
        final Callable<Integer> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(arg);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }
}
