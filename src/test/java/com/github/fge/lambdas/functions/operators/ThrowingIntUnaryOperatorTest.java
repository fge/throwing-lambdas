package com.github.fge.lambdas.functions.operators;

import com.github.fge.lambdas.ThrowingInterfaceTest;

import java.util.concurrent.Callable;
import java.util.function.IntUnaryOperator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({"ProhibitedExceptionDeclared", "AutoBoxing",
    "OverlyBroadThrowsClause"})
public final class ThrowingIntUnaryOperatorTest
    extends ThrowingInterfaceTest<ThrowingIntUnaryOperator, ThrowingIntUnaryOperator, IntUnaryOperator, Integer>
{
    private final int operand = 1;

    public ThrowingIntUnaryOperatorTest()
    {
        super(SpiedThrowingIntUnaryOperator::newSpy,
            () -> mock(IntUnaryOperator.class), 5, 20);
    }

    @Override
    protected void setupFull(final ThrowingIntUnaryOperator instance)
        throws Throwable
    {
        when(instance.doApplyAsInt(operand)).thenReturn(ret1).thenThrow(checked)
            .thenThrow(unchecked).thenThrow(error);
    }

    @Override
    protected void setupAlternate(final ThrowingIntUnaryOperator instance)
        throws Throwable
    {
        when(instance.doApplyAsInt(operand)).thenReturn(ret2);
    }

    @Override
    protected void setupFallback(final IntUnaryOperator instance)
    {
        when(instance.applyAsInt(operand)).thenReturn(ret2);
    }

    @Override
    protected Callable<Integer> asCallable(final IntUnaryOperator instance)
    {
        return () -> instance.applyAsInt(operand);
    }

    public void testChainedWithOrReturn()
        throws Throwable
    {
        final IntUnaryOperator instance = getFullInstance().orReturn(ret2);

        final Callable<Integer> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    public void testChainedWithOrReturnSelf()
        throws Throwable
    {
        final IntUnaryOperator instance = getFullInstance().orReturnSelf();

        final Callable<Integer> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(operand);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}
