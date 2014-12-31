package com.github.fge.lambdas.functions.longfunctions;

import com.github.fge.lambdas.ThrowingInterfaceTest;
import com.github.fge.lambdas.helpers.Type1;

import java.util.concurrent.Callable;
import java.util.function.LongFunction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({ "ProhibitedExceptionDeclared", "OverlyBroadThrowsClause" })
public final class ThrowingLongFunctionTest
    extends ThrowingInterfaceTest<ThrowingLongFunction<Type1>, ThrowingLongFunction<Type1>, LongFunction<Type1>, Type1>
{
    private final long value = 2898L;

    public ThrowingLongFunctionTest()
    {
        super(SpiedThrowingLongFunction::newSpy, () -> mock(LongFunction.class),
            Type1.mock(), Type1.mock());
    }

    @Override
    protected void setupFull(final ThrowingLongFunction<Type1> instance)
        throws Throwable
    {
        when(instance.doApply(value)).thenReturn(ret1).thenThrow(checked)
            .thenThrow(unchecked).thenThrow(error);
    }

    @Override
    protected void setupAlternate(final ThrowingLongFunction<Type1> instance)
        throws Throwable
    {
        when(instance.doApply(value)).thenReturn(ret2);
    }

    @Override
    protected void setupFallback(final LongFunction<Type1> instance)
    {
        when(instance.apply(value)).thenReturn(ret2);
    }

    @Override
    protected Callable<Type1> asCallable(final LongFunction<Type1> instance)
    {
        return () -> instance.apply(value);
    }

    public void testChainedWithOrReturn()
        throws Throwable
    {
        final LongFunction<Type1> instance = getFullInstance().orReturn(ret2);

        final Callable<Type1> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}
