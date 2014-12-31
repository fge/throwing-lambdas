package com.github.fge.lambdas.functions.intfunctions;

import com.github.fge.lambdas.ThrowingInterfaceTest;

import java.util.concurrent.Callable;
import java.util.function.IntToLongFunction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({ "ProhibitedExceptionDeclared", "AutoBoxing",
    "OverlyBroadThrowsClause" })
public final class ThrowingIntToLongFunctionTest
    extends ThrowingInterfaceTest<ThrowingIntToLongFunction, ThrowingIntToLongFunction, IntToLongFunction, Long>
{
    private final int value = 2;

    public ThrowingIntToLongFunctionTest()
    {
        super(SpiedThrowingIntToLongFunction::newSpy,
            () -> mock(IntToLongFunction.class), 42L, 387297L);
    }

    @Override
    protected void setupFull(final ThrowingIntToLongFunction instance)
        throws Throwable
    {
        when(instance.doApplyAsLong(value)).thenReturn(ret1).thenThrow(checked)
            .thenThrow(unchecked).thenThrow(error);
    }

    @Override
    protected void setupAlternate(final ThrowingIntToLongFunction instance)
        throws Throwable
    {
        when(instance.doApplyAsLong(value)).thenReturn(ret2);
    }

    @Override
    protected void setupFallback(final IntToLongFunction instance)
    {
        when(instance.applyAsLong(value)).thenReturn(ret2);
    }

    @Override
    protected Callable<Long> asCallable(final IntToLongFunction instance)
    {
        return () -> instance.applyAsLong(value);
    }

    public void testChainedWithOrReturn()
        throws Throwable
    {
        final IntToLongFunction instance = getFullInstance().orReturn(ret2);

        final Callable<Long> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}
