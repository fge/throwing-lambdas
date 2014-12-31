package com.github.fge.lambdas.functions.operators;

import com.github.fge.lambdas.ThrowingInterfaceTest;

import java.util.concurrent.Callable;
import java.util.function.DoubleUnaryOperator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({"ProhibitedExceptionDeclared", "AutoBoxing",
    "OverlyBroadThrowsClause"})
public final class ThrowingDoubleUnaryOperatorTest
    extends ThrowingInterfaceTest<ThrowingDoubleUnaryOperator, ThrowingDoubleUnaryOperator, DoubleUnaryOperator, Double>
{
    private final double operand = 1.0;

    public ThrowingDoubleUnaryOperatorTest()
    {
        super(SpiedThrowingDoubleUnaryOperator::newSpy,
            () -> mock(DoubleUnaryOperator.class), 0.5, 2.0);
    }

    @Override
    protected void setupFull(final ThrowingDoubleUnaryOperator instance)
        throws Throwable
    {
        when(instance.doApplyAsDouble(operand)).thenReturn(ret1)
            .thenThrow(checked).thenThrow(unchecked).thenThrow(error);
    }

    @Override
    protected void setupAlternate(final ThrowingDoubleUnaryOperator instance)
        throws Throwable
    {
        when(instance.doApplyAsDouble(operand)).thenReturn(ret2);
    }

    @Override
    protected void setupFallback(final DoubleUnaryOperator instance)
    {
        when(instance.applyAsDouble(operand)).thenReturn(ret2);
    }

    @Override
    protected Callable<Double> asCallable(final DoubleUnaryOperator instance)
    {
        return () -> instance.applyAsDouble(operand);
    }

    public void testChainedWithOrReturn()
        throws Throwable
    {
        final DoubleUnaryOperator instance = getFullInstance().orReturn(ret2);

        final Callable<Double> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    public void testChainedWithOrReturnSelf()
        throws Throwable
    {
        final DoubleUnaryOperator instance = getFullInstance().orReturnSelf();

        final Callable<Double> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(operand);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}
