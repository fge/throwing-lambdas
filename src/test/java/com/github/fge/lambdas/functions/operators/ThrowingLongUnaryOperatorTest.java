package com.github.fge.lambdas.functions.operators;

import com.github.fge.lambdas.ThrowingInterfaceTest;

import java.util.concurrent.Callable;
import java.util.function.LongUnaryOperator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({"ProhibitedExceptionDeclared", "AutoBoxing",
    "OverlyBroadThrowsClause"})
public final class ThrowingLongUnaryOperatorTest
    extends ThrowingInterfaceTest<ThrowingLongUnaryOperator, ThrowingLongUnaryOperator, LongUnaryOperator, Long>
{
    private final long operand = 2837987219387L;

    public ThrowingLongUnaryOperatorTest()
    {
        super(SpiedThrowingLongUnaryOperator::newSpy,
            () -> mock(LongUnaryOperator.class), 2980982197L, 22L);
    }

    @Override
    protected void setupFull(final ThrowingLongUnaryOperator instance)
        throws Throwable
    {
        when(instance.doApplyAsLong(operand)).thenReturn(ret1)
            .thenThrow(checked).thenThrow(unchecked).thenThrow(error);
    }

    @Override
    protected void setupAlternate(final ThrowingLongUnaryOperator instance)
        throws Throwable
    {
        when(instance.doApplyAsLong(operand)).thenReturn(ret2);
    }

    @Override
    protected void setupFallback(final LongUnaryOperator instance)
    {
        when(instance.applyAsLong(operand)).thenReturn(ret2);
    }

    @Override
    protected Callable<Long> asCallable(final LongUnaryOperator instance)
    {
        return () -> instance.applyAsLong(operand);
    }

    public void testChainedWithOrReturn()
        throws Throwable
    {
        final LongUnaryOperator instance = getFullInstance().orReturn(ret2);

        final Callable<Long> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    public void testChainedWithOrReturnSelf()
        throws Throwable
    {
        final LongUnaryOperator instance = getFullInstance().orReturnSelf();

        final Callable<Long> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(operand);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}
