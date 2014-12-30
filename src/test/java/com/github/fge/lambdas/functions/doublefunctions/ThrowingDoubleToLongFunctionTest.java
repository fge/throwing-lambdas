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

    public ThrowingDoubleToLongFunctionTest()
    {
        super(42L, 387297L);
    }


    @Override
    protected ThrowingDoubleToLongFunction getAlternate()
        throws Throwable
    {
        final ThrowingDoubleToLongFunction spy =
            SpiedThrowingDoubleToLongFunction.newSpy();

        when(spy.doApplyAsLong(arg)).thenReturn(ret2);
        return spy;
    }

    @Override
    protected ThrowingDoubleToLongFunction getTestInstance()
        throws Throwable
    {
        final ThrowingDoubleToLongFunction spy
            = SpiedThrowingDoubleToLongFunction.newSpy();

        when(spy.doApplyAsLong(arg)).thenReturn(ret1).thenThrow(checked)
            .thenThrow(unchecked).thenThrow(error);

        return spy;
    }

    @Override
    protected DoubleToLongFunction getFallback()
    {
        final DoubleToLongFunction mock = mock(DoubleToLongFunction.class);

        when(mock.applyAsLong(arg)).thenReturn(ret2);

        return mock;
    }

    @Override
    protected Runnable runnableFrom(final DoubleToLongFunction instance)
    {
        return () -> instance.applyAsLong(arg);
    }

    @Override
    protected Callable<Long> asCallable(final DoubleToLongFunction instance)
    {
        return () -> instance.applyAsLong(arg);
    }

    @Override
    public void testUnchained()
        throws Throwable
    {
        final ThrowingDoubleToLongFunction instance = getTestInstance();

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
        final DoubleToLongFunction instance
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
        final ThrowingDoubleToLongFunction first = getTestInstance();
        final ThrowingDoubleToLongFunction second = getAlternate();

        final DoubleToLongFunction instance = first.orTryWith(second);

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
        final ThrowingDoubleToLongFunction first = getTestInstance();
        final DoubleToLongFunction second = getFallback();

        final DoubleToLongFunction instance = first.fallbackTo(second);

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
        final DoubleToLongFunction instance
            = getTestInstance().orReturn(ret2);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Long> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }
}
