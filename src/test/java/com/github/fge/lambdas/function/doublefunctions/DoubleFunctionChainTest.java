package com.github.fge.lambdas.function.doublefunctions;

import com.github.fge.lambdas.ChainTest;
import com.github.fge.lambdas.Throwing;
import com.github.fge.lambdas.helpers.Type1;
import org.testng.annotations.Test;

import java.util.concurrent.Callable;
import java.util.function.DoubleFunction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class DoubleFunctionChainTest
    extends ChainTest<DoubleFunction<Type1>, ThrowingDoubleFunction<Type1>, DoubleFunctionChain<Type1>, Type1>
{
    private final double value = 42.0;

    public DoubleFunctionChainTest()
    {
        super(Type1.mock(), Type1.mock());
    }

    @Override
    protected DoubleFunction<Type1> getFallback()
    {
        return mock(DoubleFunction.class);
    }

    @Override
    protected ThrowingDoubleFunction<Type1> getThrowing()
    {
        return mock(ThrowingDoubleFunction.class);
    }

    @Override
    protected DoubleFunctionChain<Type1> getChain(
        final ThrowingDoubleFunction<Type1> throwing)
    {
        return Throwing.doubleFunction(throwing);
    }

    @Override
    protected Callable<Type1> toCallable(final DoubleFunction<Type1> chain)
    {
        return () -> chain.apply(value);
    }

    @Override
    protected void configureFull(final ThrowingDoubleFunction<Type1> throwing)
        throws Throwable
    {
        when(throwing.doApply(value))
            .thenReturn(ret1)
            .thenThrow(checked)
            .thenThrow(unchecked)
            .thenThrow(error);
    }

    @Override
    protected void configureAlternate(
        final ThrowingDoubleFunction<Type1> throwing)
        throws Throwable
    {
        when(throwing.doApply(value))
            .thenReturn(ret2);
    }

    @Override
    protected void configureFallback(final DoubleFunction<Type1> fallback)
    {
        when(fallback.apply(value))
            .thenReturn(ret2);
    }

    @Test
    public void orReturnTest()
        throws Throwable
    {
        final ThrowingDoubleFunction<Type1> throwing
            = mock(ThrowingDoubleFunction.class);
        configureFull(throwing);

        final DoubleFunction<Type1> chain = getChain(throwing).orReturn(ret2);

        final Callable<Type1> callable = toCallable(chain);


        assertThat(callable.call()).isEqualTo(ret1);

        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}
