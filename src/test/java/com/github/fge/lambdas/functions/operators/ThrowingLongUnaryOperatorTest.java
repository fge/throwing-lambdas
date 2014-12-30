package com.github.fge.lambdas.functions.operators;

import com.github.fge.lambdas.ThrowingInterfaceBaseTest;
import com.github.fge.lambdas.ThrownByLambdaException;
import com.github.fge.lambdas.helpers.MyException;

import java.util.concurrent.Callable;
import java.util.function.LongUnaryOperator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({"ProhibitedExceptionDeclared", "AutoBoxing",
    "OverlyBroadThrowsClause"})
public final class ThrowingLongUnaryOperatorTest
    extends ThrowingInterfaceBaseTest<ThrowingLongUnaryOperator, LongUnaryOperator, Long>
{
    private final long arg = 2837987219387L;

    public ThrowingLongUnaryOperatorTest()
    {
        super(2980982197L, 22L);
    }

    @Override
    protected ThrowingLongUnaryOperator getAlternate()
        throws Throwable
    {
        final ThrowingLongUnaryOperator spy =
            SpiedThrowingLongUnaryOperator.newSpy();

        when(spy.doApplyAsLong(arg)).thenReturn(ret2);

        return spy;
    }

    @Override
    protected ThrowingLongUnaryOperator getTestInstance()
        throws Throwable
    {
        final ThrowingLongUnaryOperator spy
            = SpiedThrowingLongUnaryOperator.newSpy();

        when(spy.doApplyAsLong(arg)).thenReturn(ret1).thenThrow(checked)
            .thenThrow(unchecked).thenThrow(error);

        return spy;
    }

    @Override
    protected LongUnaryOperator getFallback()
    {
        final LongUnaryOperator mock = mock(LongUnaryOperator.class);

        when(mock.applyAsLong(arg)).thenReturn(ret2);

        return mock;
    }

    @Override
    protected Runnable runnableFrom(final LongUnaryOperator instance)
    {
        return () -> instance.applyAsLong(arg);
    }

    @Override
    protected Callable<Long> asCallable(final LongUnaryOperator instance)
    {
        return () -> instance.applyAsLong(arg);
    }

    @Override
    public void testUnchained()
        throws Throwable
    {
        final LongUnaryOperator instance = getTestInstance();

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
        final LongUnaryOperator instance
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
        final ThrowingLongUnaryOperator first = getTestInstance();
        final ThrowingLongUnaryOperator second = getAlternate();

        final LongUnaryOperator instance = first.orTryWith(second);

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
        final ThrowingLongUnaryOperator first = getTestInstance();
        final LongUnaryOperator second = getFallback();

        final LongUnaryOperator instance = first.fallbackTo(second);

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
        final LongUnaryOperator instance
            = getTestInstance().orReturn(ret2);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Long> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    public void testChainedWithOrReturnSelf()
        throws Throwable
    {
        final LongUnaryOperator instance
            = getTestInstance().orReturnSelf();

        final Runnable runnable = runnableFrom(instance);
        final Callable<Long> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(arg);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }
}
