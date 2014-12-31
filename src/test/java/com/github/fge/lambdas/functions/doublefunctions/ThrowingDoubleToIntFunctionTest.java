package com.github.fge.lambdas.functions.doublefunctions;

import com.github.fge.lambdas.ThrowingInterfaceTest;

import java.util.concurrent.Callable;
import java.util.function.DoubleToIntFunction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({ "ProhibitedExceptionDeclared", "AutoBoxing",
    "OverlyBroadThrowsClause" })
public final class ThrowingDoubleToIntFunctionTest
    extends ThrowingInterfaceTest<ThrowingDoubleToIntFunction, ThrowingDoubleToIntFunction, DoubleToIntFunction, Integer>
{
    private final double value = 2.0;

    public ThrowingDoubleToIntFunctionTest()
    {
        super(SpiedThrowingDoubleToIntFunction::newSpy,
            () -> mock(DoubleToIntFunction.class), 42, 3);
    }

    @Override
    protected void setupFull(final ThrowingDoubleToIntFunction instance)
        throws Throwable
    {
        when(instance.doApplyAsInt(value)).thenReturn(ret1).thenThrow(checked)
            .thenThrow(unchecked).thenThrow(error);
    }

    @Override
    protected void setupAlternate(final ThrowingDoubleToIntFunction instance)
        throws Throwable
    {
        when(instance.doApplyAsInt(value)).thenReturn(ret2);
    }

    @Override
    protected void setupFallback(final DoubleToIntFunction instance)
    {
        when(instance.applyAsInt(value)).thenReturn(ret2);
    }

    @Override
    protected Callable<Integer> asCallable(final DoubleToIntFunction instance)
    {
        return () -> instance.applyAsInt(value);
    }

    public void testChainedWithOrReturn()
        throws Throwable
    {
        final DoubleToIntFunction instance = getFullInstance().orReturn(ret2);

        final Callable<Integer> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}
