package com.github.fge.lambdas.functions.longfunctions;

import com.github.fge.lambdas.ThrowingInterfaceBaseTest;
import com.github.fge.lambdas.ThrownByLambdaException;
import com.github.fge.lambdas.helpers.MyException;

import java.util.concurrent.Callable;
import java.util.function.LongToIntFunction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({ "ProhibitedExceptionDeclared", "AutoBoxing",
    "OverlyBroadThrowsClause" })
public final class ThrowingLongToIntFunctionTest
    extends ThrowingInterfaceBaseTest<ThrowingLongToIntFunction, LongToIntFunction, Integer>
{
    private final long arg = 2015L;

    public ThrowingLongToIntFunctionTest()
    {
        super(42, 3);
    }

    @Override
    protected ThrowingLongToIntFunction getAlternate()
        throws Throwable
    {
        final ThrowingLongToIntFunction spy =
            SpiedThrowingLongToIntFunction.newSpy();

        when(spy.doApplyAsInt(arg)).thenReturn(ret2);

        return spy;
    }

    @Override
    protected ThrowingLongToIntFunction getPreparedInstance()
        throws Throwable
    {
        final ThrowingLongToIntFunction spy
            = SpiedThrowingLongToIntFunction.newSpy();

        when(spy.doApplyAsInt(arg)).thenReturn(ret1).thenThrow(checked)
            .thenThrow(unchecked).thenThrow(error);

        return spy;
    }

    @Override
    protected LongToIntFunction getFallbackInstance()
    {
        final LongToIntFunction mock = mock(LongToIntFunction.class);

        when(mock.applyAsInt(arg)).thenReturn(ret2);

        return mock;
    }

    @Override
    protected Runnable runnableFrom(final LongToIntFunction instance)
    {
        return () -> instance.applyAsInt(arg);
    }

    @Override
    protected Callable<Integer> callableFrom(final LongToIntFunction instance)
    {
        return () -> instance.applyAsInt(arg);
    }

    @Override
    public void testUnchained()
        throws Throwable
    {
        final ThrowingLongToIntFunction instance = getPreparedInstance();

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
        final LongToIntFunction instance
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
        final ThrowingLongToIntFunction first = getPreparedInstance();
        final ThrowingLongToIntFunction second = getAlternate();

        final LongToIntFunction instance = first.orTryWith(second);

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
        final ThrowingLongToIntFunction first = getPreparedInstance();
        final LongToIntFunction second = getFallbackInstance();

        final LongToIntFunction instance = first.fallbackTo(second);

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
        final LongToIntFunction instance
            = getPreparedInstance().orReturn(ret2);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Integer> callable = callableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }
}
