package com.github.fge.lambdas.functions.operators;

import com.github.fge.lambdas.ThrowingInterfaceTest;

import java.util.concurrent.Callable;
import java.util.function.DoubleBinaryOperator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({"ProhibitedExceptionDeclared", "AutoBoxing",
    "OverlyBroadThrowsClause"})
public final class ThrowingDoubleBinaryOperatorTest
    extends ThrowingInterfaceTest<ThrowingDoubleBinaryOperator, ThrowingDoubleBinaryOperator, DoubleBinaryOperator, Double>
{
    private final double left = 0.125;
    private final double right = 125.0;

    public ThrowingDoubleBinaryOperatorTest()
    {
        super(SpiedThrowingDoubleBinaryOperator::newSpy,
            () -> mock(DoubleBinaryOperator.class), 2.0, 0.625);
    }

    @Override
    protected void setupFull(final ThrowingDoubleBinaryOperator instance)
        throws Throwable
    {
        when(instance.doApplyAsDouble(left, right)).thenReturn(ret1)
            .thenThrow(checked).thenThrow(unchecked).thenThrow(error);
    }

    @Override
    protected void setupAlternate(final ThrowingDoubleBinaryOperator instance)
        throws Throwable
    {
        when(instance.doApplyAsDouble(left, right)).thenReturn(ret2);
    }

    @Override
    protected void setupFallback(final DoubleBinaryOperator instance)
    {
        when(instance.applyAsDouble(left, right)).thenReturn(ret2);
    }

    @Override
    protected Callable<Double> asCallable(final DoubleBinaryOperator instance)
    {
        return () -> instance.applyAsDouble(left, right);
    }

    public void testChainedWithOrReturn()
        throws Throwable
    {
        final DoubleBinaryOperator instance = getFullInstance().orReturn(ret2);

        final Callable<Double> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    public void testChainedWithOrReturnLeft()
        throws Throwable
    {
        final DoubleBinaryOperator instance = getFullInstance().orReturnLeft();

        final Callable<Double> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(left);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    public void testChainedWithOrReturnRight()
        throws Throwable
    {
        final DoubleBinaryOperator instance = getFullInstance().orReturnRight();

        final Callable<Double> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(right);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}
