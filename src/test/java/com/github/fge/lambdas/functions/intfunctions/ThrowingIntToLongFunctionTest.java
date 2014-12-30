package com.github.fge.lambdas.functions.intfunctions;

import com.github.fge.lambdas.ThrowingInterfaceBaseTest;
import com.github.fge.lambdas.ThrownByLambdaException;
import com.github.fge.lambdas.helpers.MyException;

import java.util.concurrent.Callable;
import java.util.function.IntToLongFunction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({ "ProhibitedExceptionDeclared", "AutoBoxing",
    "OverlyBroadThrowsClause" })
public final class ThrowingIntToLongFunctionTest
    extends ThrowingInterfaceBaseTest<ThrowingIntToLongFunction, IntToLongFunction, Long>
{
    private final int arg = 2;

    public ThrowingIntToLongFunctionTest()
    {
        super(42L, 387297L);
    }

    @Override
    protected ThrowingIntToLongFunction getAlternate()
        throws Throwable
    {
        final ThrowingIntToLongFunction spy =
            SpiedThrowingIntToLongFunction.newSpy();

        when(spy.doApplyAsLong(arg)).thenReturn(ret2);

        return spy;
    }

    @Override
    protected ThrowingIntToLongFunction getTestInstance()
        throws Throwable
    {
        final ThrowingIntToLongFunction spy
            = SpiedThrowingIntToLongFunction.newSpy();

        when(spy.doApplyAsLong(arg)).thenReturn(ret1).thenThrow(checked)
            .thenThrow(unchecked).thenThrow(error);

        return spy;
    }

    @Override
    protected IntToLongFunction getFallback()
    {
        final IntToLongFunction mock = mock(IntToLongFunction.class);

        when(mock.applyAsLong(arg)).thenReturn(ret2);

        return mock;
    }

    @Override
    protected Callable<Long> asCallable(final IntToLongFunction instance)
    {
        return () -> instance.applyAsLong(arg);
    }

    @Override
    public void testUnchained()
        throws Throwable
    {
        final ThrowingIntToLongFunction instance = getTestInstance();

        final Callable<Long> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);

        verifyCheckedRethrow(callable, ThrownByLambdaException.class);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    @Override
    public void testChainedWithOrThrow()
        throws Throwable
    {
        final IntToLongFunction instance
            = getTestInstance().orThrow(MyException.class);

        final Callable<Long> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);

        verifyCheckedRethrow(callable, MyException.class);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    @Override
    public void testChainedWithOrTryWith()
        throws Throwable
    {
        final ThrowingIntToLongFunction first = getTestInstance();
        final ThrowingIntToLongFunction second = getAlternate();

        final IntToLongFunction instance = first.orTryWith(second);

        final Callable<Long> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    @Override
    public void testChainedWithFallbackTo()
        throws Throwable
    {
        final ThrowingIntToLongFunction first = getTestInstance();
        final IntToLongFunction second = getFallback();

        final IntToLongFunction instance = first.fallbackTo(second);

        final Callable<Long> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    public void testChainedWithOrReturn()
        throws Throwable
    {
        final IntToLongFunction instance
            = getTestInstance().orReturn(ret2);

        final Callable<Long> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}
