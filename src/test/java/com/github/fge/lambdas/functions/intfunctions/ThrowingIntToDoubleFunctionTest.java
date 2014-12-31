package com.github.fge.lambdas.functions.intfunctions;

import com.github.fge.lambdas.ThrowingInterfaceTest;

import java.util.concurrent.Callable;
import java.util.function.IntToDoubleFunction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({ "ProhibitedExceptionDeclared", "AutoBoxing",
    "OverlyBroadThrowsClause" })
public final class ThrowingIntToDoubleFunctionTest
    extends ThrowingInterfaceTest<ThrowingIntToDoubleFunction, ThrowingIntToDoubleFunction, IntToDoubleFunction, Double>
{
    private final int value = 2;

    public ThrowingIntToDoubleFunctionTest()
    {
        super(SpiedThrowingIntToDoubleFunction::newSpy,
            () -> mock(IntToDoubleFunction.class), 4.0, 0.5);
    }

    @Override
    protected void setupFull(final ThrowingIntToDoubleFunction instance)
        throws Throwable
    {
        when(instance.doApplyAsDouble(value)).thenReturn(ret1)
            .thenThrow(checked).thenThrow(unchecked).thenThrow(error);
    }

    @Override
    protected void setupAlternate(final ThrowingIntToDoubleFunction instance)
        throws Throwable
    {
        when(instance.doApplyAsDouble(value)).thenReturn(ret2);
    }

    @Override
    protected void setupFallback(final IntToDoubleFunction instance)
    {
        when(instance.applyAsDouble(value)).thenReturn(ret2);
    }

    @Override
    protected Callable<Double> asCallable(final IntToDoubleFunction instance)
    {
        return () -> instance.applyAsDouble(value);
    }

    public void testChainedWithOrReturn()
        throws Throwable
    {
        final IntToDoubleFunction instance = getFullInstance().orReturn(ret2);

        final Callable<Double> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}
