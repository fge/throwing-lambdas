package com.github.fge.lambdas.functions.doublefunctions;

import com.github.fge.lambdas.ThrowingInterfaceTest;

import java.util.concurrent.Callable;
import java.util.function.DoubleToLongFunction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({ "ProhibitedExceptionDeclared", "AutoBoxing",
    "OverlyBroadThrowsClause" })
public final class ThrowingDoubleToLongFunctionTest
    extends ThrowingInterfaceTest<ThrowingDoubleToLongFunction, ThrowingDoubleToLongFunction, DoubleToLongFunction, Long>
{
    private final double value = 2.0;

    public ThrowingDoubleToLongFunctionTest()
    {
        super(SpiedThrowingDoubleToLongFunction::newSpy,
            () -> mock(DoubleToLongFunction.class), 42L, 387297L);
    }

    @Override
    protected void setupFull(final ThrowingDoubleToLongFunction instance)
        throws Throwable
    {
        when(instance.doApplyAsLong(value)).thenReturn(ret1).thenThrow(checked)
            .thenThrow(unchecked).thenThrow(error);
    }

    @Override
    protected void setupAlternate(final ThrowingDoubleToLongFunction instance)
        throws Throwable
    {
        when(instance.doApplyAsLong(value)).thenReturn(ret2);
    }

    @Override
    protected void setupFallback(final DoubleToLongFunction instance)
    {
        when(instance.applyAsLong(value)).thenReturn(ret2);
    }

    @Override
    protected Callable<Long> asCallable(final DoubleToLongFunction instance)
    {
        return () -> instance.applyAsLong(value);
    }

    public void testChainedWithOrReturn()
        throws Throwable
    {
        final DoubleToLongFunction instance = getFullInstance().orReturn(ret2);

        final Callable<Long> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}
