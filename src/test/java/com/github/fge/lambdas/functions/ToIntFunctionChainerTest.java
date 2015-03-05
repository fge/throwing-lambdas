package com.github.fge.lambdas.functions;

import com.github.fge.lambdas.ChainerTest;
import com.github.fge.lambdas.Throwing;
import com.github.fge.lambdas.helpers.Type1;
import org.testng.annotations.Test;

import java.util.concurrent.Callable;
import java.util.function.ToIntFunction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class ToIntFunctionChainerTest
    extends ChainerTest<ToIntFunction<Type1>, ThrowingToIntFunction<Type1>, ToIntFunctionChainer<Type1>, Integer>
{
    private final Type1 value = Type1.mock();

    public ToIntFunctionChainerTest()
    {
        super(42, 24);
    }

    @Override
    protected ToIntFunction<Type1> getFallback()
    {
        return mock(ToIntFunction.class);
    }

    @Override
    protected ThrowingToIntFunction<Type1> getThrowing()
    {
        return mock(ThrowingToIntFunction.class);
    }

    @Override
    protected ToIntFunctionChainer<Type1> getChain(
        final ThrowingToIntFunction<Type1> throwing)
    {
        return Throwing.toIntFunction(throwing);
    }

    @Override
    protected Callable<Integer> toCallable(final ToIntFunction<Type1> chain)
    {
        return () -> chain.applyAsInt(value);
    }

    @Override
    protected void configureFull(final ThrowingToIntFunction<Type1> throwing)
        throws Throwable
    {
        when(throwing.doApplyAsInt(value))
            .thenReturn(ret1)
            .thenThrow(checked)
            .thenThrow(unchecked)
            .thenThrow(error);
    }

    @Override
    protected void configureAlternate(
        final ThrowingToIntFunction<Type1> throwing)
        throws Throwable
    {
        when(throwing.doApplyAsInt(value))
            .thenReturn(ret2);
    }

    @Override
    protected void configureFallback(final ToIntFunction<Type1> fallback)
    {
        when(fallback.applyAsInt(value))
            .thenReturn(ret2);
    }

    @Test
    public void orReturnTest()
        throws Throwable
    {
        final ThrowingToIntFunction<Type1> throwing = getThrowing();
        configureFull(throwing);

        final ToIntFunction<Type1> chain = getChain(throwing).orReturn(ret2);

        final Callable<Integer> callable = toCallable(chain);

        assertThat(callable.call()).isEqualTo(ret1);

        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}
