package com.github.fge.lambdas.functions.doublefunctions;

import com.github.fge.lambdas.ThrowingInterfaceBaseTest;
import com.github.fge.lambdas.ThrownByLambdaException;
import com.github.fge.lambdas.helpers.MyException;

import java.util.concurrent.Callable;
import java.util.function.DoubleToLongFunction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({ "ProhibitedExceptionDeclared", "AutoBoxing",
    "OverlyBroadThrowsClause" })
public final class ThrowingDoubleToLongFunctionTest
    extends ThrowingInterfaceBaseTest<ThrowingDoubleToLongFunction, DoubleToLongFunction, Long>
{
    private final double arg = 2.0;
    private final long ret1 = 42L;
    private final long ret2 = 387297L;

    @Override
    protected ThrowingDoubleToLongFunction getBaseInstance()
    {
        return SpiedThrowingDoubleToLongFunction.newSpy();
    }

    @Override
    protected ThrowingDoubleToLongFunction getPreparedInstance()
        throws Throwable
    {
        final ThrowingDoubleToLongFunction spy = getBaseInstance();

        when(spy.doApplyAsLong(arg)).thenReturn(ret1).thenThrow(checked)
            .thenThrow(unchecked).thenThrow(error);

        return spy;
    }

    @Override
    protected DoubleToLongFunction getNonThrowingInstance()
    {
        return mock(DoubleToLongFunction.class);
    }

    @Override
    protected Runnable runnableFrom(final DoubleToLongFunction instance)
    {
        return () -> instance.applyAsLong(arg);
    }

    @Override
    protected Callable<Long> callableFrom(final DoubleToLongFunction instance)
    {
        return () -> instance.applyAsLong(arg);
    }

    @Override
    public void testUnchained()
        throws Throwable
    {
        final ThrowingDoubleToLongFunction instance = getPreparedInstance();

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
        final DoubleToLongFunction instance
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
        final ThrowingDoubleToLongFunction first = getPreparedInstance();
        final ThrowingDoubleToLongFunction second = getBaseInstance();
        when(second.doApplyAsLong(arg)).thenReturn(ret2);

        final DoubleToLongFunction instance = first.orTryWith(second);

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
        final ThrowingDoubleToLongFunction first = getPreparedInstance();
        final DoubleToLongFunction second = getNonThrowingInstance();
        when(second.applyAsLong(arg)).thenReturn(ret2);

        final DoubleToLongFunction instance = first.fallbackTo(second);

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
        final DoubleToLongFunction instance
            = getPreparedInstance().orReturn(ret2);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Long> callable = callableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }
}
