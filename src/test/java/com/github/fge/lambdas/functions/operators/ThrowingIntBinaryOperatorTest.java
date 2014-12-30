package com.github.fge.lambdas.functions.operators;

import com.github.fge.lambdas.ThrowingInterfaceBaseTest;
import com.github.fge.lambdas.ThrownByLambdaException;
import com.github.fge.lambdas.helpers.MyException;

import java.util.concurrent.Callable;
import java.util.function.IntBinaryOperator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({"ProhibitedExceptionDeclared", "AutoBoxing",
    "OverlyBroadThrowsClause"})
public final class ThrowingIntBinaryOperatorTest
    extends ThrowingInterfaceBaseTest<ThrowingIntBinaryOperator, IntBinaryOperator, Integer>
{
    private final int left = 125;
    private final int right = 2125;

    public ThrowingIntBinaryOperatorTest()
    {
        super(42, 625);
    }

    @Override
    protected ThrowingIntBinaryOperator getAlternate()
        throws Throwable
    {
        final ThrowingIntBinaryOperator spy =
            SpiedThrowingIntBinaryOperator.newSpy();

        when(spy.doApplyAsInt(left, right)).thenReturn(ret2);

        return spy;
    }

    @Override
    protected ThrowingIntBinaryOperator getTestInstance()
        throws Throwable
    {
        final ThrowingIntBinaryOperator spy
            = SpiedThrowingIntBinaryOperator.newSpy();

        when(spy.doApplyAsInt(left, right)).thenReturn(ret1)
            .thenThrow(checked).thenThrow(unchecked).thenThrow(error);

        return spy;
    }

    @Override
    protected IntBinaryOperator getFallback()
    {
        final IntBinaryOperator mock = mock(IntBinaryOperator.class);

        when(mock.applyAsInt(left, right)).thenReturn(ret2);

        return mock;
    }

    @Override
    protected Callable<Integer> asCallable(final IntBinaryOperator instance)
    {
        return () -> instance.applyAsInt(left, right);
    }

    @Override
    public void testUnchained()
        throws Throwable
    {
        final IntBinaryOperator instance = getTestInstance();

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
        final IntBinaryOperator instance
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
        final ThrowingIntBinaryOperator first = getTestInstance();
        final ThrowingIntBinaryOperator second = getAlternate();

        final IntBinaryOperator instance = first.orTryWith(second);

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
        final ThrowingIntBinaryOperator first = getTestInstance();
        final IntBinaryOperator second = getFallback();

        final IntBinaryOperator instance = first.fallbackTo(second);

        final Callable<Integer> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    public void testChainedWithOrReturn()
        throws Throwable
    {
        final IntBinaryOperator instance
            = getTestInstance().orReturn(ret2);

        final Callable<Integer> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    public void testChainedWithOrReturnLeft()
        throws Throwable
    {
        final IntBinaryOperator instance
            = getTestInstance().orReturnLeft();

        final Callable<Integer> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(left);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    public void testChainedWithOrReturnRight()
        throws Throwable
    {
        final IntBinaryOperator instance
            = getTestInstance().orReturnRight();

        final Callable<Integer> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(right);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}
