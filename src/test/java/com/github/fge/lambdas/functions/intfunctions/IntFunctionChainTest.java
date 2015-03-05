package com.github.fge.lambdas.functions.intfunctions;

import com.github.fge.lambdas.ChainTest;
import com.github.fge.lambdas.Throwing;
import com.github.fge.lambdas.helpers.Type1;
import org.testng.annotations.Test;

import java.util.concurrent.Callable;
import java.util.function.IntFunction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class IntFunctionChainTest
    extends ChainTest<IntFunction<Type1>, ThrowingIntFunction<Type1>, IntFunctionChain<Type1>, Type1>
{
    private final int value = 42;

    public IntFunctionChainTest()
    {
        super(Type1.mock(), Type1.mock());
    }

    @Override
    protected IntFunction<Type1> getFallback()
    {
        return mock(IntFunction.class);
    }

    @Override
    protected ThrowingIntFunction<Type1> getThrowing()
    {
        return mock(ThrowingIntFunction.class);
    }

    @Override
    protected IntFunctionChain<Type1> getChain(
        final ThrowingIntFunction<Type1> throwing)
    {
        return Throwing.intFunction(throwing);
    }

    @Override
    protected Callable<Type1> toCallable(final IntFunction<Type1> chain)
    {
        return () -> chain.apply(value);
    }

    @Override
    protected void configureFull(final ThrowingIntFunction<Type1> throwing)
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
        final ThrowingIntFunction<Type1> throwing)
        throws Throwable
    {
        when(throwing.doApply(value))
            .thenReturn(ret2);
    }

    @Override
    protected void configureFallback(final IntFunction<Type1> fallback)
    {
        when(fallback.apply(value))
            .thenReturn(ret2);
    }

    @Test
    public void orReturnTest()
        throws Throwable
    {
        final ThrowingIntFunction<Type1> throwing
            = mock(ThrowingIntFunction.class);
        configureFull(throwing);

        final IntFunction<Type1> chain = getChain(throwing).orReturn(ret2);

        final Callable<Type1> callable = toCallable(chain);


        assertThat(callable.call()).isEqualTo(ret1);

        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}
