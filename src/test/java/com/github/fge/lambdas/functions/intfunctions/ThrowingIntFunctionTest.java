package com.github.fge.lambdas.functions.intfunctions;

import com.github.fge.lambdas.ThrowingInterfaceTest;
import com.github.fge.lambdas.helpers.Type1;

import java.util.concurrent.Callable;
import java.util.function.IntFunction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({ "ProhibitedExceptionDeclared", "OverlyBroadThrowsClause" })
public final class ThrowingIntFunctionTest
    extends ThrowingInterfaceTest<ThrowingIntFunction<Type1>, ThrowingIntFunction<Type1>, IntFunction<Type1>, Type1>
{
    private final int value = 25;

    public ThrowingIntFunctionTest()
    {
        super(SpiedThrowingIntFunction::newSpy, () -> mock(IntFunction.class),
            Type1.mock(), Type1.mock());
    }

    @Override
    protected void setupFull(final ThrowingIntFunction<Type1> instance)
        throws Throwable
    {
        when(instance.doApply(value)).thenReturn(ret1).thenThrow(checked)
            .thenThrow(unchecked).thenThrow(error);
    }

    @Override
    protected void setupAlternate(final ThrowingIntFunction<Type1> instance)
        throws Throwable
    {
        when(instance.doApply(value)).thenReturn(ret2);
    }

    @Override
    protected void setupFallback(final IntFunction<Type1> instance)
    {
        when(instance.apply(value)).thenReturn(ret2);
    }

    @Override
    protected Callable<Type1> asCallable(final IntFunction<Type1> instance)
    {
        return () -> instance.apply(value);
    }

    public void testChainedWithOrReturn()
        throws Throwable
    {
        final IntFunction<Type1> instance = getFullInstance().orReturn(ret2);

        final Callable<Type1> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}
