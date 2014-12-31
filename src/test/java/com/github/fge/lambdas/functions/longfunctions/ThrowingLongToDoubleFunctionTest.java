package com.github.fge.lambdas.functions.longfunctions;

import com.github.fge.lambdas.ThrowingInterfaceTest;

import java.util.concurrent.Callable;
import java.util.function.LongToDoubleFunction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({ "ProhibitedExceptionDeclared", "AutoBoxing",
    "OverlyBroadThrowsClause" })
public final class ThrowingLongToDoubleFunctionTest
    extends ThrowingInterfaceTest<ThrowingLongToDoubleFunction, ThrowingLongToDoubleFunction, LongToDoubleFunction, Double>
{
    private final long value = 2223L;

    public ThrowingLongToDoubleFunctionTest()
    {
        super(SpiedThrowingLongToDoubleFunction::newSpy,
            () -> mock(LongToDoubleFunction.class), 4.0, 0.5);
    }

    @Override
    protected void setupFull(final ThrowingLongToDoubleFunction instance)
        throws Throwable
    {
        when(instance.doApplyAsDouble(value)).thenReturn(ret1)
            .thenThrow(checked).thenThrow(unchecked).thenThrow(error);
    }

    @Override
    protected void setupAlternate(final ThrowingLongToDoubleFunction instance)
        throws Throwable
    {
        when(instance.doApplyAsDouble(value)).thenReturn(ret2);
    }

    @Override
    protected void setupFallback(final LongToDoubleFunction instance)
    {
        when(instance.applyAsDouble(value)).thenReturn(ret2);
    }

    @Override
    protected Callable<Double> asCallable(final LongToDoubleFunction instance)
    {
        return () -> instance.applyAsDouble(value);
    }

    public void testChainedWithOrReturn()
        throws Throwable
    {
        final LongToDoubleFunction instance = getFullInstance().orReturn(ret2);

        final Callable<Double> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}
