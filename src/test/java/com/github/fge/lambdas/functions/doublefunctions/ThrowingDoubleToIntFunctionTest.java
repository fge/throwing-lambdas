package com.github.fge.lambdas.functions.doublefunctions;

import com.github.fge.lambdas.ThrowingInterfaceBaseTest;
import com.github.fge.lambdas.ThrownByLambdaException;
import com.github.fge.lambdas.helpers.MyException;

import java.util.concurrent.Callable;
import java.util.function.DoubleToIntFunction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({ "ProhibitedExceptionDeclared", "AutoBoxing",
    "OverlyBroadThrowsClause" })
public final class ThrowingDoubleToIntFunctionTest
    extends ThrowingInterfaceBaseTest<ThrowingDoubleToIntFunction, DoubleToIntFunction, Integer>
{
    private final double arg = 2.0;
    private final int ret1 = 42;
    private final int ret2 = 3;

    @Override
    protected ThrowingDoubleToIntFunction getBaseInstance()
    {
        return SpiedThrowingDoubleToIntFunction.newSpy();
    }

    @Override
    protected ThrowingDoubleToIntFunction getPreparedInstance()
        throws Throwable
    {
        final ThrowingDoubleToIntFunction spy = getBaseInstance();

        when(spy.doApplyAsInt(arg)).thenReturn(ret1).thenThrow(checked)
            .thenThrow(unchecked).thenThrow(error);

        return spy;
    }

    @Override
    protected DoubleToIntFunction getNonThrowingInstance()
    {
        return mock(DoubleToIntFunction.class);
    }

    @Override
    protected Runnable runnableFrom(final DoubleToIntFunction instance)
    {
        return () -> instance.applyAsInt(arg);
    }

    @Override
    protected Callable<Integer> callableFrom(final DoubleToIntFunction instance)
    {
        return () -> instance.applyAsInt(arg);
    }

    @Override
    public void testUnchained()
        throws Throwable
    {
        final ThrowingDoubleToIntFunction instance = getPreparedInstance();

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
        final DoubleToIntFunction instance
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
        final ThrowingDoubleToIntFunction first = getPreparedInstance();
        final ThrowingDoubleToIntFunction second = getBaseInstance();
        when(second.doApplyAsInt(arg)).thenReturn(ret2);

        final DoubleToIntFunction instance = first.orTryWith(second);

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
        final ThrowingDoubleToIntFunction first = getPreparedInstance();
        final DoubleToIntFunction second = getNonThrowingInstance();
        when(second.applyAsInt(arg)).thenReturn(ret2);

        final DoubleToIntFunction instance = first.fallbackTo(second);

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
        final DoubleToIntFunction instance
            = getPreparedInstance().orReturn(ret2);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Integer> callable = callableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }
}
