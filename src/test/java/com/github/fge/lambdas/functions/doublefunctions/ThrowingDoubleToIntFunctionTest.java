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

    public ThrowingDoubleToIntFunctionTest()
    {
        super(42, 3);
    }

    @Override
    protected ThrowingDoubleToIntFunction getAlternate()
        throws Throwable
    {
        final ThrowingDoubleToIntFunction spy =
            SpiedThrowingDoubleToIntFunction.newSpy();

        when(spy.doApplyAsInt(arg)).thenReturn(ret2);

        return spy;
    }

    @Override
    protected ThrowingDoubleToIntFunction getTestInstance()
        throws Throwable
    {
        final ThrowingDoubleToIntFunction spy
            = SpiedThrowingDoubleToIntFunction.newSpy();

        when(spy.doApplyAsInt(arg)).thenReturn(ret1).thenThrow(checked)
            .thenThrow(unchecked).thenThrow(error);

        return spy;
    }

    @Override
    protected DoubleToIntFunction getFallback()
    {
        final DoubleToIntFunction mock = mock(DoubleToIntFunction.class);

        when(mock.applyAsInt(arg)).thenReturn(ret2);

        return mock;
    }

    @Override
    protected Callable<Integer> asCallable(final DoubleToIntFunction instance)
    {
        return () -> instance.applyAsInt(arg);
    }

    @Override
    public void testUnchained()
        throws Throwable
    {
        final ThrowingDoubleToIntFunction instance = getTestInstance();

        final Callable<Integer> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);

        verifyCheckedRethrow(callable, ThrownByLambdaException.class);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    @Override
    public void testChainedWithOrThrow()
        throws Throwable
    {
        final DoubleToIntFunction instance
            = getTestInstance().orThrow(MyException.class);

        final Callable<Integer> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);

        verifyCheckedRethrow(callable, MyException.class);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    @Override
    public void testChainedWithOrTryWith()
        throws Throwable
    {
        final ThrowingDoubleToIntFunction first = getTestInstance();
        final ThrowingDoubleToIntFunction second = getAlternate();

        final DoubleToIntFunction instance = first.orTryWith(second);

        final Callable<Integer> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    @Override
    public void testChainedWithFallbackTo()
        throws Throwable
    {
        final ThrowingDoubleToIntFunction first = getTestInstance();
        final DoubleToIntFunction second = getFallback();

        final DoubleToIntFunction instance = first.fallbackTo(second);

        final Callable<Integer> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    public void testChainedWithOrReturn()
        throws Throwable
    {
        final DoubleToIntFunction instance
            = getTestInstance().orReturn(ret2);

        final Callable<Integer> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}
