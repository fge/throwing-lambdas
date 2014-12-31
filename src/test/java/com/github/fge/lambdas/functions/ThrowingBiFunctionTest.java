package com.github.fge.lambdas.functions;

import com.github.fge.lambdas.ThrowingInterfaceTest;
import com.github.fge.lambdas.helpers.Type1;
import com.github.fge.lambdas.helpers.Type2;
import com.github.fge.lambdas.helpers.Type3;

import java.util.concurrent.Callable;
import java.util.function.BiFunction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({ "ProhibitedExceptionDeclared", "OverlyBroadThrowsClause" })
public final class ThrowingBiFunctionTest
    extends ThrowingInterfaceTest<ThrowingBiFunction<Type1, Type2, Type3>, ThrowingBiFunction<Type1, Type2, Type3>, BiFunction<Type1, Type2, Type3>, Type3>
{
    private final Type1 t = Type1.mock();
    private final Type2 u = Type2.mock();

    public ThrowingBiFunctionTest()
    {
        super(SpiedThrowingBiFunction::newSpy, () -> mock(BiFunction.class),
            Type3.mock(), Type3.mock());
    }

    @Override
    protected void setupFull(
        final ThrowingBiFunction<Type1, Type2, Type3> instance)
        throws Throwable
    {
        when(instance.doApply(t, u)).thenReturn(ret1).thenThrow(checked)
            .thenThrow(unchecked).thenThrow(error);
    }

    @Override
    protected void setupAlternate(
        final ThrowingBiFunction<Type1, Type2, Type3> instance)
        throws Throwable
    {
        when(instance.doApply(t, u)).thenReturn(ret2);
    }

    @Override
    protected void setupFallback(final BiFunction<Type1, Type2, Type3> instance)
    {
        when(instance.apply(t, u)).thenReturn(ret2);
    }

    @Override
    protected Callable<Type3> asCallable(
        final BiFunction<Type1, Type2, Type3> instance)
    {
        return () -> instance.apply(t, u);
    }

    public void testChainedWithOrReturn()
        throws Throwable
    {
        final BiFunction<Type1, Type2, Type3> instance
            = getFullInstance().orReturn(ret2);

        final Callable<Type3> callable = asCallable(instance);

        assertThat(callable.call()).isSameAs(ret1);
        assertThat(callable.call()).isSameAs(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}
