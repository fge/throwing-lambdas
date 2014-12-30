package com.github.fge.lambdas.functions.intfunctions;

import com.github.fge.lambdas.ThrowingInterfaceBaseTest;
import com.github.fge.lambdas.ThrownByLambdaException;
import com.github.fge.lambdas.helpers.MyException;

import java.util.concurrent.Callable;
import java.util.function.IntToDoubleFunction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({ "ProhibitedExceptionDeclared", "AutoBoxing",
    "OverlyBroadThrowsClause" })
public final class ThrowingIntToDoubleFunctionTest
    extends ThrowingInterfaceBaseTest<ThrowingIntToDoubleFunction, IntToDoubleFunction, Double>
{
    private final int arg = 2;

    public ThrowingIntToDoubleFunctionTest()
    {
        super(4.0, 0.5);
    }

    @Override
    protected ThrowingIntToDoubleFunction getAlternate()
        throws Throwable
    {
        final ThrowingIntToDoubleFunction spy =
            SpiedThrowingIntToDoubleFunction.newSpy();

        when(spy.doApplyAsDouble(arg)).thenReturn(ret2);

        return spy;
    }

    @Override
    protected ThrowingIntToDoubleFunction getTestInstance()
        throws Throwable
    {
        final ThrowingIntToDoubleFunction spy
            = SpiedThrowingIntToDoubleFunction.newSpy();

        when(spy.doApplyAsDouble(arg)).thenReturn(ret1).thenThrow(checked)
            .thenThrow(unchecked).thenThrow(error);

        return spy;
    }

    @Override
    protected IntToDoubleFunction getFallback()
    {
        final IntToDoubleFunction mock = mock(IntToDoubleFunction.class);

        when(mock.applyAsDouble(arg)).thenReturn(ret2);

        return mock;
    }

    @Override
    protected Callable<Double> asCallable(final IntToDoubleFunction instance)
    {
        return () -> instance.applyAsDouble(arg);
    }

    @Override
    public void testUnchained()
        throws Throwable
    {
        final ThrowingIntToDoubleFunction instance = getTestInstance();

        final Callable<Double> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);

        verifyCheckedRethrow(callable, ThrownByLambdaException.class);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    @Override
    public void testChainedWithOrThrow()
        throws Throwable
    {
        final IntToDoubleFunction instance
            = getTestInstance().orThrow(MyException.class);

        final Callable<Double> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);

        verifyCheckedRethrow(callable, MyException.class);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    @Override
    public void testChainedWithOrTryWith()
        throws Throwable
    {
        final ThrowingIntToDoubleFunction first = getTestInstance();
        final ThrowingIntToDoubleFunction second = getAlternate();

        final IntToDoubleFunction instance = first.orTryWith(second);

        final Callable<Double> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    @Override
    public void testChainedWithFallbackTo()
        throws Throwable
    {
        final ThrowingIntToDoubleFunction first = getTestInstance();
        final IntToDoubleFunction second = getFallback();

        final IntToDoubleFunction instance = first.fallbackTo(second);

        final Callable<Double> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    public void testChainedWithOrReturn()
        throws Throwable
    {
        final IntToDoubleFunction instance
            = getTestInstance().orReturn(ret2);

        final Callable<Double> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}
