package com.github.fge.lambdas.functions.operators;

import com.github.fge.lambdas.ThrowingInterfaceTest;

import java.util.concurrent.Callable;
import java.util.function.LongBinaryOperator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({"ProhibitedExceptionDeclared", "AutoBoxing",
    "OverlyBroadThrowsClause"})
public final class ThrowingLongBinaryOperatorTest
    extends ThrowingInterfaceTest<ThrowingLongBinaryOperator, ThrowingLongBinaryOperator, LongBinaryOperator, Long>
{
    private final long left = 398098L;
    private final long right = 125L;

    public ThrowingLongBinaryOperatorTest()
    {
        super(SpiedThrowingLongBinaryOperator::newSpy,
            () -> mock(LongBinaryOperator.class), 2L, 625L);
    }

    @Override
    protected void setupFull(final ThrowingLongBinaryOperator instance)
        throws Throwable
    {
        when(instance.doApplyAsLong(left, right)).thenReturn(ret1)
            .thenThrow(checked).thenThrow(unchecked).thenThrow(error);
    }

    @Override
    protected void setupAlternate(final ThrowingLongBinaryOperator instance)
        throws Throwable
    {
        when(instance.doApplyAsLong(left, right)).thenReturn(ret2);
    }

    @Override
    protected void setupFallback(final LongBinaryOperator instance)
    {
        when(instance.applyAsLong(left, right)).thenReturn(ret2);
    }

    @Override
    protected Callable<Long> asCallable(final LongBinaryOperator instance)
    {
        return () -> instance.applyAsLong(left, right);
    }

    public void testChainedWithOrReturn()
        throws Throwable
    {
        final LongBinaryOperator instance = getFullInstance().orReturn(ret2);

        final Callable<Long> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    public void testChainedWithOrReturnLeft()
        throws Throwable
    {
        final LongBinaryOperator instance = getFullInstance().orReturnLeft();

        final Callable<Long> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(left);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    public void testChainedWithOrReturnRight()
        throws Throwable
    {
        final LongBinaryOperator instance = getFullInstance().orReturnRight();

        final Callable<Long> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(right);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}
