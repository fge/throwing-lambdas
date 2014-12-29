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
    private final long ret1 = 2980982197L;
    private final long ret2 = 22L;

    @Override
    protected ThrowingLongUnaryOperator getBaseInstance()
    {
        return SpiedThrowingLongUnaryOperator.newSpy();
    }

    @Override
    protected ThrowingLongUnaryOperator getPreparedInstance()
        throws Throwable
    {
        final ThrowingLongUnaryOperator spy = getBaseInstance();

        when(spy.doApplyAsLong(arg)).thenReturn(ret1).thenThrow(checked)
            .thenThrow(unchecked).thenThrow(error);

        return spy;
    }

    @Override
    protected LongUnaryOperator getNonThrowingInstance()
    {
        return mock(ThrowingLongUnaryOperator.class);
    }

    @Override
    protected Runnable runnableFrom(final LongUnaryOperator instance)
    {
        return () -> instance.applyAsLong(arg);
    }

    @Override
    protected Callable<Long> callableFrom(final LongUnaryOperator instance)
    {
        return () -> instance.applyAsLong(arg);
    }

    @Override
    public void testUnchained()
        throws Throwable
    {
        final LongUnaryOperator instance = getPreparedInstance();

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
        final LongUnaryOperator instance
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
        final ThrowingLongUnaryOperator first = getPreparedInstance();
        final ThrowingLongUnaryOperator second = getBaseInstance();
        when(second.doApplyAsLong(arg)).thenReturn(ret2);

        final LongUnaryOperator instance = first.orTryWith(second);

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
        final ThrowingLongUnaryOperator first = getPreparedInstance();
        final LongUnaryOperator second = getNonThrowingInstance();
        when(second.applyAsLong(arg)).thenReturn(ret2);

        final LongUnaryOperator instance = first.fallbackTo(second);

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
        final LongUnaryOperator instance
            = getPreparedInstance().orReturn(ret2);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Long> callable = callableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    public void testChainedWithOrReturnSelf()
        throws Throwable
    {
        final LongUnaryOperator instance
            = getPreparedInstance().orReturnSelf();

        final Runnable runnable = runnableFrom(instance);
        final Callable<Long> callable = callableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(arg);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }
}
