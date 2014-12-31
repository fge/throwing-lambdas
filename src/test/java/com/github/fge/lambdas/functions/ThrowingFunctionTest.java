package com.github.fge.lambdas.functions;

import com.github.fge.lambdas.ThrowingInterfaceTest;
import com.github.fge.lambdas.helpers.Type1;
import com.github.fge.lambdas.helpers.Type2;

import java.util.concurrent.Callable;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({ "ProhibitedExceptionDeclared", "OverlyBroadThrowsClause" })
public final class ThrowingFunctionTest
    extends ThrowingInterfaceTest<ThrowingFunction<Type1, Type2>, ThrowingFunction<Type1, Type2>, Function<Type1, Type2>, Type2>
{
    private final Type1 t = Type1.mock();

    public ThrowingFunctionTest()
    {
        super(SpiedThrowingFunction::newSpy, () -> mock(Function.class),
            Type2.mock(), Type2.mock());
    }

    @Override
    protected void setupFull(final ThrowingFunction<Type1, Type2> instance)
        throws Throwable
    {
        when(instance.doApply(t)).thenReturn(ret1).thenThrow(checked)
            .thenThrow(unchecked).thenThrow(error);
    }

    @Override
    protected void setupAlternate(final ThrowingFunction<Type1, Type2> instance)
        throws Throwable
    {
        when(instance.doApply(t)).thenReturn(ret2);
    }

    @Override
    protected void setupFallback(final Function<Type1, Type2> instance)
    {
        when(instance.apply(t)).thenReturn(ret2);
    }

    @Override
    protected Callable<Type2> asCallable(
        final Function<Type1, Type2> instance)
    {
        return () -> instance.apply(t);
    }

    public void testChainedWithOrReturn()
        throws Throwable
    {
        final Function<Type1, Type2> instance
            = getFullInstance().orReturn(ret2);

        final Callable<Type2> callable = asCallable(instance);

        assertThat(callable.call()).isSameAs(ret1);
        assertThat(callable.call()).isSameAs(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}
