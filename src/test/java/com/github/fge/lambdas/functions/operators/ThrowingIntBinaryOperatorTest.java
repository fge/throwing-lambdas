package com.github.fge.lambdas.functions.operators;

import com.github.fge.lambdas.ThrowingInterfaceTest;

import java.util.concurrent.Callable;
import java.util.function.IntBinaryOperator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({"ProhibitedExceptionDeclared", "AutoBoxing",
    "OverlyBroadThrowsClause"})
public final class ThrowingIntBinaryOperatorTest
    extends ThrowingInterfaceTest<ThrowingIntBinaryOperator, ThrowingIntBinaryOperator, IntBinaryOperator, Integer>
{
    private final int left = 125;
    private final int right = 2125;

    public ThrowingIntBinaryOperatorTest()
    {
        super(SpiedThrowingIntBinaryOperator::newSpy,
            () -> mock(IntBinaryOperator.class), 42, 625);
    }

    @Override
    protected void setupFull(final ThrowingIntBinaryOperator instance)
        throws Throwable
    {
        when(instance.doApplyAsInt(left, right)).thenReturn(ret1)
            .thenThrow(checked).thenThrow(unchecked).thenThrow(error);
    }

    @Override
    protected void setupAlternate(final ThrowingIntBinaryOperator instance)
        throws Throwable
    {
        when(instance.doApplyAsInt(left, right)).thenReturn(ret2);
    }

    @Override
    protected void setupFallback(final IntBinaryOperator instance)
    {
        when(instance.applyAsInt(left, right)).thenReturn(ret2);
    }

    @Override
    protected Callable<Integer> asCallable(final IntBinaryOperator instance)
    {
        return () -> instance.applyAsInt(left, right);
    }

    public void testChainedWithOrReturn()
        throws Throwable
    {
        final IntBinaryOperator instance = getFullInstance().orReturn(ret2);

        final Callable<Integer> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    public void testChainedWithOrReturnLeft()
        throws Throwable
    {
        final IntBinaryOperator instance = getFullInstance().orReturnLeft();

        final Callable<Integer> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(left);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    public void testChainedWithOrReturnRight()
        throws Throwable
    {
        final IntBinaryOperator instance = getFullInstance().orReturnRight();

        final Callable<Integer> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(right);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}
