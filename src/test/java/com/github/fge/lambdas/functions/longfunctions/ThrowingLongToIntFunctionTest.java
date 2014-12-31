package com.github.fge.lambdas.functions.longfunctions;

import com.github.fge.lambdas.ThrowingInterfaceTest;

import java.util.concurrent.Callable;
import java.util.function.LongToIntFunction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({ "ProhibitedExceptionDeclared", "AutoBoxing",
    "OverlyBroadThrowsClause" })
public final class ThrowingLongToIntFunctionTest
    extends ThrowingInterfaceTest<ThrowingLongToIntFunction, ThrowingLongToIntFunction, LongToIntFunction, Integer>
{
    private final long value = 2015L;

    public ThrowingLongToIntFunctionTest()
    {
        super(SpiedThrowingLongToIntFunction::newSpy,
            () -> mock(LongToIntFunction.class), 42, 3);
    }

    @Override
    protected void setupFull(final ThrowingLongToIntFunction instance)
        throws Throwable
    {
        when(instance.doApplyAsInt(value)).thenReturn(ret1).thenThrow(checked)
            .thenThrow(unchecked).thenThrow(error);
    }

    @Override
    protected void setupAlternate(final ThrowingLongToIntFunction instance)
        throws Throwable
    {
        when(instance.doApplyAsInt(value)).thenReturn(ret2);
    }

    @Override
    protected void setupFallback(final LongToIntFunction instance)
    {
        when(instance.applyAsInt(value)).thenReturn(ret2);
    }

    @Override
    protected Callable<Integer> asCallable(final LongToIntFunction instance)
    {
        return () -> instance.applyAsInt(value);
    }

    public void testChainedWithOrReturn()
        throws Throwable
    {
        final LongToIntFunction instance = getFullInstance().orReturn(ret2);

        final Callable<Integer> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}
