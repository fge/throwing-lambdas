package com.github.fge.lambdas.functions;

import com.github.fge.lambdas.ThrowingInterfaceTest;
import com.github.fge.lambdas.helpers.Type1;

import java.util.concurrent.Callable;
import java.util.function.ToIntFunction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({ "ProhibitedExceptionDeclared", "AutoBoxing",
    "OverlyBroadThrowsClause" })
public final class ThrowingToIntFunctionTest
    extends ThrowingInterfaceTest<ThrowingToIntFunction<Type1>, ThrowingToIntFunction<Type1>, ToIntFunction<Type1>, Integer>
{
    private final Type1 value = Type1.mock();

    public ThrowingToIntFunctionTest()
    {
        super(SpiedThrowingToIntFunction::newSpy,
            () -> mock(ToIntFunction.class), 42, 25);
    }

    @Override
    protected void setupFull(final ThrowingToIntFunction<Type1> instance)
        throws Throwable
    {
        when(instance.doApplyAsInt(value)).thenReturn(ret1).thenThrow(checked)
            .thenThrow(unchecked).thenThrow(error);
    }

    @Override
    protected void setupAlternate(final ThrowingToIntFunction<Type1> instance)
        throws Throwable
    {
        when(instance.doApplyAsInt(value)).thenReturn(ret2);
    }

    @Override
    protected void setupFallback(final ToIntFunction<Type1> instance)
    {
        when(instance.applyAsInt(value)).thenReturn(ret2);
    }

    @Override
    protected Callable<Integer> asCallable(final ToIntFunction<Type1> instance)
    {
        return () -> instance.applyAsInt(value);
    }

    public void testChainedWithOrReturn()
        throws Throwable
    {
        final ToIntFunction<Type1> instance = getFullInstance().orReturn(ret2);

        final Callable<Integer> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}
