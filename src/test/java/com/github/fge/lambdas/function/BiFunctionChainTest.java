package com.github.fge.lambdas.function;

import com.github.fge.lambdas.ChainTest;
import com.github.fge.lambdas.Throwing;
import com.github.fge.lambdas.helpers.Type1;
import com.github.fge.lambdas.helpers.Type2;
import com.github.fge.lambdas.helpers.Type3;
import org.testng.annotations.Test;

import java.util.concurrent.Callable;
import java.util.function.BiFunction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class BiFunctionChainTest
    extends ChainTest<BiFunction<Type1, Type2, Type3>, ThrowingBiFunction<Type1, Type2, Type3>, BiFunctionChain<Type1, Type2, Type3>, Type3>
{
    private final Type1 t = Type1.mock();
    private final Type2 u = Type2.mock();

    public BiFunctionChainTest()
    {
        super(Type3.mock(), Type3.mock());
    }

    @Override
    protected BiFunction<Type1, Type2, Type3> getFallback()
    {
        return mock(BiFunction.class);
    }

    @Override
    protected ThrowingBiFunction<Type1, Type2, Type3> getThrowing()
    {
        return mock(ThrowingBiFunction.class);
    }

    @Override
    protected BiFunctionChain<Type1, Type2, Type3> getChain(
        final ThrowingBiFunction<Type1, Type2, Type3> throwing)
    {
        return Throwing.biFunction(throwing);
    }

    @Override
    protected Callable<Type3> toCallable(
        final BiFunction<Type1, Type2, Type3> chain)
    {
        return () -> chain.apply(t, u);
    }

    @Override
    protected void configureFull(
        final ThrowingBiFunction<Type1, Type2, Type3> throwing)
        throws Throwable
    {
        when(throwing.doApply(t, u))
            .thenReturn(ret1)
            .thenThrow(checked)
            .thenThrow(unchecked)
            .thenThrow(error);
    }

    @Override
    protected void configureAlternate(
        final ThrowingBiFunction<Type1, Type2, Type3> throwing)
        throws Throwable
    {
        when(throwing.doApply(t, u))
            .thenReturn(ret2);
    }

    @Override
    protected void configureFallback(
        final BiFunction<Type1, Type2, Type3> fallback)
    {
        when(fallback.apply(t, u))
            .thenReturn(ret2);
    }

    @Test
    public void orReturnTest()
        throws Throwable
    {
        final ThrowingBiFunction<Type1, Type2, Type3> throwing = getThrowing();
        configureFull(throwing);

        final BiFunction<Type1, Type2, Type3> chain
            = getChain(throwing).orReturn(ret2);

        final Callable<Type3> callable = toCallable(chain);

        assertThat(callable.call()).isSameAs(ret1);

        assertThat(callable.call()).isSameAs(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}
