package com.github.fge.lambdas.functions;

import com.github.fge.lambdas.ChainTest;
import com.github.fge.lambdas.Throwing;
import com.github.fge.lambdas.helpers.Type1;
import org.testng.annotations.Test;

import java.util.concurrent.Callable;
import java.util.function.ToLongFunction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class ToLongFunctionChainTest
    extends ChainTest<ToLongFunction<Type1>, ThrowingToLongFunction<Type1>, ToLongFunctionChain<Type1>, Long>
{
    private final Type1 value = Type1.mock();

    public ToLongFunctionChainTest()
    {
        super(42L, 24L);
    }

    @Override
    protected ToLongFunction<Type1> getFallback()
    {
        return mock(ToLongFunction.class);
    }

    @Override
    protected ThrowingToLongFunction<Type1> getThrowing()
    {
        return mock(ThrowingToLongFunction.class);
    }

    @Override
    protected ToLongFunctionChain<Type1> getChain(
        final ThrowingToLongFunction<Type1> throwing)
    {
        return Throwing.toLongFunction(throwing);
    }

    @Override
    protected Callable<Long> toCallable(final ToLongFunction<Type1> chain)
    {
        return () -> chain.applyAsLong(value);
    }

    @Override
    protected void configureFull(final ThrowingToLongFunction<Type1> throwing)
        throws Throwable
    {
        when(throwing.doApplyAsLong(value))
            .thenReturn(ret1)
            .thenThrow(checked)
            .thenThrow(unchecked)
            .thenThrow(error);
    }

    @Override
    protected void configureAlternate(
        final ThrowingToLongFunction<Type1> throwing)
        throws Throwable
    {
        when(throwing.doApplyAsLong(value))
            .thenReturn(ret2);
    }

    @Override
    protected void configureFallback(final ToLongFunction<Type1> fallback)
    {
        when(fallback.applyAsLong(value))
            .thenReturn(ret2);
    }

    @Test
    public void orReturnTest()
        throws Throwable
    {
        final ThrowingToLongFunction<Type1> throwing = getThrowing();
        configureFull(throwing);

        final ToLongFunction<Type1> chain = getChain(throwing).orReturn(ret2);

        final Callable<Long> callable = toCallable(chain);

        assertThat(callable.call()).isEqualTo(ret1);

        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}
