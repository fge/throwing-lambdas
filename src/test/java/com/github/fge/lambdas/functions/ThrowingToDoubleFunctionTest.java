package com.github.fge.lambdas.functions;

import com.github.fge.lambdas.ThrowingInterfaceTest;
import com.github.fge.lambdas.helpers.Type1;

import java.util.concurrent.Callable;
import java.util.function.ToDoubleFunction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({ "ProhibitedExceptionDeclared", "AutoBoxing",
    "OverlyBroadThrowsClause" })
public final class ThrowingToDoubleFunctionTest
    extends ThrowingInterfaceTest<ThrowingToDoubleFunction<Type1>, ThrowingToDoubleFunction<Type1>, ToDoubleFunction<Type1>, Double>
{
    private final Type1 value = Type1.mock();

    public ThrowingToDoubleFunctionTest()
    {
        super(SpiedThrowingToDoubleFunction::newSpy,
            () -> mock(ToDoubleFunction.class), 0.5, 0.25);
    }

    @Override
    protected void setupFull(final ThrowingToDoubleFunction<Type1> instance)
        throws Throwable
    {
        when(instance.doApplyAsDouble(value)).thenReturn(ret1)
            .thenThrow(checked).thenThrow(unchecked).thenThrow(error);
    }

    @Override
    protected void setupAlternate(
        final ThrowingToDoubleFunction<Type1> instance)
        throws Throwable
    {
        when(instance.doApplyAsDouble(value)).thenReturn(ret2);
    }

    @Override
    protected void setupFallback(final ToDoubleFunction<Type1> instance)
    {
        when(instance.applyAsDouble(value)).thenReturn(ret2);
    }

    @Override
    protected Callable<Double> asCallable(
        final ToDoubleFunction<Type1> instance)
    {
        return () -> instance.applyAsDouble(value);
    }

    public void testChainedWithOrReturn()
        throws Throwable
    {
        final ToDoubleFunction<Type1> instance
            = getFullInstance().orReturn(ret2);

        final Callable<Double> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}
