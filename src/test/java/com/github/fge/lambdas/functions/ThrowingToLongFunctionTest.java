package com.github.fge.lambdas.functions;

import com.github.fge.lambdas.ThrowingInterfaceTest;
import com.github.fge.lambdas.helpers.Type1;

import java.util.concurrent.Callable;
import java.util.function.ToLongFunction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({ "ProhibitedExceptionDeclared", "AutoBoxing",
    "OverlyBroadThrowsClause" })
public final class ThrowingToLongFunctionTest
    extends ThrowingInterfaceTest<ThrowingToLongFunction<Type1>, ThrowingToLongFunction<Type1>, ToLongFunction<Type1>, Long>
{
    private final Type1 value = Type1.mock();

    public ThrowingToLongFunctionTest()
    {
        super(SpiedThrowingToLongFunction::newSpy,
            () -> mock(ToLongFunction.class), 42L, 25L);
    }

    @Override
    protected void setupFull(final ThrowingToLongFunction<Type1> instance)
        throws Throwable
    {
        when(instance.doApplyAsLong(value)).thenReturn(ret1).thenThrow(checked)
            .thenThrow(unchecked).thenThrow(error);
    }

    @Override
    protected void setupAlternate(final ThrowingToLongFunction<Type1> instance)
        throws Throwable
    {
        when(instance.doApplyAsLong(value)).thenReturn(ret2);
    }

    @Override
    protected void setupFallback(final ToLongFunction<Type1> instance)
    {
        when(instance.applyAsLong(value)).thenReturn(ret2);
    }

    @Override
    protected Callable<Long> asCallable(
        final ToLongFunction<Type1> instance)
    {
        return () -> instance.applyAsLong(value);
    }

    public void testChainedWithOrReturn()
        throws Throwable
    {
        final ToLongFunction<Type1> instance = getFullInstance().orReturn(ret2);

        final Callable<Long> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}
